package com.locationmatching.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.ImageType;
import com.locationmatching.enums.PhotoPlanType;
import com.locationmatching.enums.UserPlanType;
import com.locationmatching.service.LocationProviderService;
import com.locationmatching.util.GlobalVars;

/**
 * Controller for uploading of pictures to be stored on the server.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */

@Controller
@SessionAttributes({"location", "locationProvider", "uploadForm"})
public class FileUploadController implements ServletContextAware{
	private ServletContext context;

	@Autowired
	private LocationProviderService providerService;
	
	public void setProviderService(LocationProviderService providerService) {
		this.providerService = providerService;
	}
	
	@RequestMapping(value="uploadFile.request", method=RequestMethod.POST)
	protected String uploadImage(@ModelAttribute("locationProvider")LocationProvider locationProvider,
			@ModelAttribute("location")Location location, 
			@RequestParam("multipartFile")MultipartFile multipartFile, 
			@RequestParam(value = "mainPhotoRadio", defaultValue="0") long coverPhotoId,
			@RequestParam("nextPage") String nextPage,
			HttpServletRequest request,
			Model model,
			BindingResult result) {
		String message;
		Integer numberOfFreePhotos, numberOfPaidPhotos;

		if(result.hasErrors() == false) {
			// Only proceed if the user has included a file for uploading.
			if(ServletFileUpload.isMultipartContent(request) == true 
					&& multipartFile != null && multipartFile.getSize() > 0) {
				FileOutputStream fileOutput = null;
				String absoluteFilePath, relativeUrlPath, filePath, fileName;
				Long providerId, locationId;
				
				providerId = locationProvider.getId();
				locationId = location.getId();
				filePath = context.getInitParameter("UploadDirectoryPath") + "images" + File.separator + providerId + File.separator + locationId;
				fileName = multipartFile.getOriginalFilename();
				absoluteFilePath = filePath + File.separator + fileName;
				relativeUrlPath = context.getInitParameter("UploadImageUrlDirectory") + providerId + "/" + locationId + "/" + fileName;
				
				try {
					// Create the directory if it does not exist.
					File directory = new File(filePath);
					
					if(directory.exists() == false) {
						directory.mkdirs();
					}
					File imageFile = new File(absoluteFilePath);
					
					imageFile.createNewFile();
					
					fileOutput = new FileOutputStream(imageFile);
					IOUtils.copy(multipartFile.getInputStream(), fileOutput);
					
					// Create a new image and add it to the Location object
					Image image = new Image();
					image.setUploadDate(new Date(System.currentTimeMillis()));
					image.setAbsoluteFilePath(absoluteFilePath);
					image.setRelativeUrlPath(relativeUrlPath);
					image.setApproved(false);
					image.setFileName(fileName);
					
					// Set the image type based on the content type from the
					// file item.
					String contentType;
					ImageType imageType = ImageType.NOT_SUPPORTED;
					contentType = multipartFile.getContentType();
					
					switch(contentType) {
						case("image/jpeg"):
							imageType = ImageType.JPG;
							break;
						case("image/gif"):
							imageType = ImageType.GIF;
							break;
						case("image/png"):
							imageType = ImageType.PNG;
							break;
						default:
							// Need to add error handling
					}
					image.setImageType(imageType);

					numberOfFreePhotos = location.getNumberOfFreePhotos();
					numberOfPaidPhotos = location.getNumberOfPaidPhotos();
					// We need to see if the user must pay for the photo. First 
					// we need to see what the Location Provider's plan type is.
					// If it is basic, the max free photos to upload is BASIC_FREE_PHOTO_AMOUNT
					// If the plan is premium, the max free photos is PREMIUM_FREE_PHOTO_AMOUNT
					// If the photo exceed
					UserPlanType userPlanType;
					boolean freePhoto = false;
					
					userPlanType = locationProvider.getUserPlanType();
					if(userPlanType == UserPlanType.PREMIUM) {
						if(numberOfFreePhotos < GlobalVars.PREMIUM_FREE_PHOTO_AMOUNT) {
							freePhoto = true;
						}
					}
					else {
						if(numberOfFreePhotos < GlobalVars.BASIC_FREE_PHOTO_AMOUNT) {
							freePhoto = true;
						}
					}
					
					PhotoPlanType photoPlanType;
					if(freePhoto == true) {
						photoPlanType = PhotoPlanType.FREE_PHOTO;
					}
					else {
						photoPlanType = PhotoPlanType.PAID_PHOTO;
					}
					image.setPhotoPlanType(photoPlanType);
					
					
					
					// We need to set the coverPhoto flag. If it is 
					// the first image being added to the collection then
					// set the coverPhoto flag to true.
					if(numberOfFreePhotos == 0 && numberOfPaidPhotos == 0) {
						image.setCoverPhoto(true);
						// Set the url for the cover photo in the Location object.
						location.setCoverPhotoUrl(image.getRelativeUrlPath());
					}
					else {
						location.setCoverPhotoUrl(coverPhotoId);
					}
					location.addImage(image);
					
					providerService.addImage(image);

				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
				finally {
					if(fileOutput != null) {
						try {
							fileOutput.close();
						}
						catch(IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				message = "File was uploaded successfully.";
				model.addAttribute("fileuploadSuccessMessage", message);
			}
			else {
				// Maybe the user did not add a photo to upload
				// There was an error so let the user know.
				message = "There was an error uploading the image. Please make sure to select an image before trying to add it.";
				model.addAttribute("errorMessage", message);
			}
		}
		else {
			// There was an error so let the user know.
			message = "There was an error uploading this image. Please try again at a later time.";
			model.addAttribute("errorMessage", message);
		}
		
		return nextPage;
	}


	@Override
	/**
	 * ServletContext is injected because of the ServletContextAware 
	 * interface.
	 */
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
