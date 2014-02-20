package com.locationmatching.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

import com.locationmatching.component.CreditCard;
import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.component.PayPalPaymentService;
import com.locationmatching.component.PaymentService;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.ImageType;
import com.locationmatching.enums.PhotoPlanType;
import com.locationmatching.enums.PhotoStatus;
import com.locationmatching.enums.UserPlanType;
import com.locationmatching.service.LocationProviderServiceImpl;
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
	private LocationProviderServiceImpl providerService;
	
	public void setProviderService(LocationProviderServiceImpl providerService) {
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
		Integer numberOfFreePhotos;

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
					numberOfFreePhotos = location.getNumberOfFreePhotos();
					// We need to see if the user must pay for the photo. First 
					// we need to see what the Location Provider's plan type is.
					// If it is basic, the max free photos to upload is BASIC_FREE_PHOTO_AMOUNT
					// If the plan is premium, the max free photos is PREMIUM_FREE_PHOTO_AMOUNT
					// If the number of free photos == to the max free photos then the user
					// needs to start paying for photos.
					UserPlanType userPlanType;
					Boolean freePhoto = false;
					int planTotalFreePhotos = GlobalVars.BASIC_FREE_PHOTO_AMOUNT, numberOfRemainingFreePhotos = 0;
					
					userPlanType = locationProvider.getUserPlanType();
					if(userPlanType == UserPlanType.PREMIUM) {
						planTotalFreePhotos = GlobalVars.PREMIUM_FREE_PHOTO_AMOUNT;
					}
					if(numberOfFreePhotos < planTotalFreePhotos) {
						freePhoto = true;
					}
					
					// Check to see if user needs to start paying for photos. If so, we need to see if they
					// have a credit card linked to their account. If not redirect them to the manageCreditCard page.
					// If they do have one or more credit cards linked to the account, get the primary credit card and
					// make sure the card has not expired. If not submit payment to credit card service to process
					// the transaction. If the transaction goes through continue processing the photo. If not
					// display a message to the user that their transaction was not accepted.
					if(freePhoto != true) {
						// Need to pay for photo. Check for valid credit card.
						CreditCard creditCard = locationProvider.getPrimaryCreditCard();
						
						if(creditCard == null) {
							// If no credit card linked to this account 
							// send them to the Manage Credit Card page to 
							// add a credit card, update the primary credit card information,
							// or select a new primary credit card.
							
							// Display error message to the user
							String errorMessage;
							
							errorMessage = "Please add a credit card to this account to pay for additional photos.";
							model.addAttribute("errorMessage", errorMessage);
							
							// Set the name of the template to use for the view.
							model.addAttribute("templateName", "manageProviderCreditCardsPage");
							
							return "forward:/setupManagePayments.request";
						}
						if(creditCard.hasCardExpired(new Date(System.currentTimeMillis())) == true) {
							// If the primary credit card has expired, send them to the Manage Credit Card page to 
							// add a credit card, update the primary credit card information,
							// or select a new primary credit card.
							
							// Display error message to the user
							String errorMessage;
							
							errorMessage = "The expiration date for the primary credit card on this account has expired."
									+ " Please add a new credit card, edit the information of the primary card, or select a new primary credit card.";
							model.addAttribute("errorMessage", errorMessage);

							// Set the name of the template to use for the view.
							model.addAttribute("templateName", "manageProviderCreditCardsPage");
							
							return "forward:/setupManagePayments.request";
						}
						
						// Process credit card payment
						PaymentService paymentService = new PayPalPaymentService();
						
						try {
							paymentService.processPayment(creditCard);
						}
						catch(Exception exception) {
							// Display error message to the user
							String errorMessage;
							
							errorMessage = exception.getMessage();
							model.addAttribute("errorMessage", errorMessage);

							// Set the name of the template to use for the view.
							model.addAttribute("templateName", "manageProviderCreditCardsPage");
							
							return "forward:/setupManagePayments.request";
						}
					}
					
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
					image.setStatus(PhotoStatus.NOT_REVIEWED);
					image.setFileName(fileName);
					image.setHidden(false);
					
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
					if(numberOfFreePhotos == 0) {
						image.setCoverPhoto(true);
						// Set the url for the cover photo in the Location object.
						location.setCoverPhotoUrl(image.getRelativeUrlPath());
					}
					else {
						location.setCoverPhotoUrl(coverPhotoId);
					}
					location.addImage(image);
					
					providerService.addImage(image);

					// Set the number of free photos left. It will be displayed on the jsp page.
					if(freePhoto == true) {
						// We need to bump up the number of free photos by one to include the one we are adding.
						numberOfFreePhotos++;
					}
					numberOfRemainingFreePhotos = planTotalFreePhotos - numberOfFreePhotos;
					model.addAttribute("numberOfRemainingFreePhotos", numberOfRemainingFreePhotos);
					// Add message if number of remaining free photos is 0. The message will tell the user
					// that they have to pay for additional images.
					if(numberOfRemainingFreePhotos == 0) {
						String payForPhotosMessage = new String();
						
						payForPhotosMessage = String.format(GlobalVars.PAY_FOR_PHOTO_MESSAGE, GlobalVars.PRICE_PER_LOCATION_PHOTO);
						model.addAttribute("payForPhotosMessage", payForPhotosMessage);
					}
					
					// Update the Location with any updates such as number of photo counts.
					providerService.modifyLocation(location);
					
					// Set the processPhotos flag on the addPhoto page to true so if the user tries to navigate away
					// from the page without saving the added photos data, we can let them know that they need to save
					// the photo data or they will lose it.
					model.addAttribute("processPhotos", "true");

					// Add the free photo flag to the model to be used on the addPhoto page.
					model.addAttribute("freePhoto", freePhoto);
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
		
		// Set the name of the template to use for the view.
		model.addAttribute("templateName", nextPage);
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
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
