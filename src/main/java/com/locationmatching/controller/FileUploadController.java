package com.locationmatching.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.locationmatching.helper.UploadForm;
import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.ImageType;
import com.locationmatching.service.LocationProviderService;

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
	
	@RequestMapping(value="uploadFile.request", method=RequestMethod.POST)
	protected String uploadImage(@ModelAttribute("locationProvider")LocationProvider locationProvider,
			@ModelAttribute("location")Location location, 
			@RequestParam("multipartFile")MultipartFile multipartFile, 
			HttpServletRequest request,
			Model model,
			BindingResult result) {
		String message;

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
					location.addImage(image);
					
					// We need to set the coverPhoto flag. If it is 
					// the first image being added to the collection then
					// set the coverPhoto flag to true. If not, set to false.
					if(location.getNumberOfImages() == 1) {
						image.setCoverPhoto(true);
					}
					else {
						image.setCoverPhoto(false);
					}
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
			}
			message = "File was uploaded successfully.";
			model.addAttribute("fileuploadSuccessMessage", message);
		}
		else {
			// There was an error so let the user know.
			
			message = "There was an error uploading this image. Please try again at a later time.";
			model.addAttribute("errorMessage", message);
		}
		
		return "addPhoto";
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
