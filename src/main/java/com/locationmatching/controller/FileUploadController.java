package com.locationmatching.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.locationmatching.component.UploadForm;
import com.locationmatching.domain.Image;
import com.locationmatching.domain.Location;
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
	
	/**
	 * Service to handle persisting new Image object to the database
	 */
	@Autowired
	LocationProviderService service;
	
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 5;  // 5MB
    
    // Directory Names
    private static final String TEMP_DIRECTORY	= "temp";
    private static final String UPLOAD_DIRECTORY = "upload";
    private static final String UPLOAD_IMAGE_DIRECTORY ="images";
    
	@RequestMapping(value="setupImageFileUpload", method=RequestMethod.GET)
	protected String setupUpload(@ModelAttribute("location")Location location, 
			@ModelAttribute("locationProvider")LocationProvider locationProvider,
			Model model) {
    	UploadForm uploadForm = new UploadForm();
    	model.addAttribute("uploadForm", uploadForm);
    	
    	return "addPhoto";
	}

	@RequestMapping(value="setupFileUploadPhoto.request", method=RequestMethod.GET)
	protected String setupUploadPhoto(@ModelAttribute("location")Location location, 
			@ModelAttribute("locationProvider")LocationProvider locationProvider,
			Model model) {
    	UploadForm uploadForm = new UploadForm();
    	model.addAttribute("uploadForm", uploadForm);
    	
    	return "addPhoto";
	}

	@RequestMapping(value="uploadFile.request", method=RequestMethod.POST)
	protected String uploadImage(@ModelAttribute("locationProvider")LocationProvider locationProvider,
			@ModelAttribute("location")Location location, 
			@ModelAttribute("uploadForm")UploadForm uploadForm, 
			@RequestParam("source")long id, 
			HttpServletRequest request,
			BindingResult result) {
		if(result.hasErrors() == false) {
			// Only proceed if the user has included a file for uploading.
			if(ServletFileUpload.isMultipartContent(request) == true) {
				CommonsMultipartFile multipartFile = uploadForm.getMultipartFile();
				DefaultMultipartHttpServletRequest multipartRequest;
				FileItem fileItem;
				List<FileItem> fileItems;
				Iterator<FileItem>itemIterator;
				String absoluteFilePath, relativeFilePath, baseFilePath, fileName, tempDirectoryPath;
				
				fileItem = multipartFile.getFileItem();
				baseFilePath = context.getInitParameter("UploadDirectoryPath");
				fileName = multipartFile.getOriginalFilename();
				
				tempDirectoryPath = baseFilePath + TEMP_DIRECTORY;
				absoluteFilePath = baseFilePath + UPLOAD_DIRECTORY + File.separator + UPLOAD_IMAGE_DIRECTORY + File.separator + id;
				relativeFilePath = "./" + UPLOAD_DIRECTORY + "/" + UPLOAD_IMAGE_DIRECTORY + "/" + id + "/" + fileName;
				
				try {
					// Create the directory where the image will be stored if it does not
					// already exists.
					File directoryFile = new File(absoluteFilePath);
					if(directoryFile.exists() == false) {
						directoryFile.mkdirs();
					}
					// Create the temp directory where the file will be downloaded to
					// if it does not already exist..
					File repository = new File(tempDirectoryPath);
					if(repository.exists() == false) {
						repository.mkdirs();
					}
					// The FileUpload library tracks uploaded files and deletes them
					// when they are no longer referenced. After the file has been
					// uploaded to the temp directory, we will copy the file to its
					// final destination.
					// Write the image file to the temp directory first.
					File imageFile = new File(tempDirectoryPath + File.separator + fileName);
	/*				
					// the files that we upload will not be tracked.
					DiskFileItemFactory factory = new DiskFileItemFactory();

					factory.setRepository(repository);
					factory.setSizeThreshold(MEMORY_THRESHOLD);

					// Create a new file upload handler
					ServletFileUpload uploadHandler = new ServletFileUpload(factory);
					// Parse the request
					fileItems = uploadHandler.parseRequest(request);
					itemIterator = fileItems.iterator();
					// Go through each of the file items until we find the file.
					while(itemIterator.hasNext() == true) {
						fileItem = itemIterator.next();
						
						if(fileItem.isFormField() == false) {
							fileName = fileItem.getName();
							fileItem.write(new File(absoluteFilePath + File.separator + fileName));
							
							// Create a new image and add it to the Location object
							Image image = new Image();
							image.setUploadDate(new Date(System.currentTimeMillis()));
							image.setAbsoluteFilePath(absoluteFilePath);
							image.setRelativeFilePath(relativeFilePath);
							image.setApproved(false);
							image.setFileName(fileName);
							image.setImageType(ImageType.JPG);
							location.addImage(image);
							URI uri = imageFile.toURI();
							break;
						}
					}
					//fileItem = multipartFile.getFileItem();
	
	
					//fileOutput = new FileOutputStream(imageFile);
					//fileOutput.write(fileItem.get());
					//fileOutput.close();
		*/			
					fileItem = multipartFile.getFileItem();
					fileItem.write(imageFile);
					// Copy the file to its final destination.
					FileUtils.moveFileToDirectory(imageFile, directoryFile, false);
					if(fileItem.isInMemory()) {
						System.out.println("Is in memory");
					}

					// Create a new image and add it to the Location object
					Image image = new Image();
					image.setUploadDate(new Date(System.currentTimeMillis()));
					image.setAbsoluteFilePath(absoluteFilePath);
					image.setRelativeFilePath(relativeFilePath);
					image.setApproved(false);
					image.setFileName(fileName);
					
					// Set the image type based on the content type from the
					// file item.
					String contentType;
					ImageType imageType = ImageType.NOT_SUPPORTED;
					contentType = fileItem.getContentType();
					
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
					
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return "addPhoto";
	}

	@RequestMapping(value="uploadFilePhoto.request", method=RequestMethod.POST)
	protected String uploadImageFile(@ModelAttribute("locationProvider")LocationProvider locationProvider,
			@ModelAttribute("location")Location location, 
			@ModelAttribute("uploadForm")UploadForm uploadForm, 
			@RequestParam("source")long id,
			BindingResult result) {
		if(result.hasErrors() == false) {
			FileOutputStream fileOutput;
			CommonsMultipartFile multipartFile = uploadForm.getMultipartFile();
			FileItem fileItem;
			String absoluteFilePath, relativeFilePath, filePath, fileName;
			
			filePath = context.getInitParameter("imageUploadPath") + "upload\\" + id;
			fileName = multipartFile.getOriginalFilename();
			absoluteFilePath = filePath + "\\" + fileName;
			relativeFilePath = ".upload/" + id + "/" + fileName;
			
			try {
				File imageFile = new File(filePath);
				if(imageFile.exists() == false) {
					imageFile.mkdir();
				}
				fileItem = multipartFile.getFileItem();

				fileOutput = new FileOutputStream(new File(absoluteFilePath));
				fileOutput.write(fileItem.get());
				fileOutput.close();
				
				// Create a new image and add it to the Location object
				Image image = new Image();
				image.setUploadDate(new Date(System.currentTimeMillis()));
				image.setAbsoluteFilePath(absoluteFilePath);
				image.setRelativeFilePath(relativeFilePath);
				image.setApproved(false);
				image.setFileName(fileName);
				image.setImageType(ImageType.JPG);
				location.addImage(image);
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return "forward:/setupFileUploadPhoto.request";
	}

	@Override
	/**
	 * ServletContext is injected because of the ServletContextAware 
	 * interface.
	 */
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	public void setService(LocationProviderService service) {
		this.service = service;
	}
}
