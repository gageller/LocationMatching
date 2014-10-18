package com.locationmatching.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;

import com.locationmatching.util.GlobalVars;
import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.AdminUser;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.PhotoStatus;
import com.locationmatching.service.AdminServiceImpl;
import com.locationmatching.service.EmailServiceImpl;
import com.locationmatching.service.LocationProviderServiceImpl;
import com.locationmatching.service.LocationScoutServiceImpl;
import com.sun.xml.internal.fastinfoset.util.StringArray;

/**
 * Controller for all Administrator functionality.
 * 
 * @author Greg Geller
 * @since 0.1.1
 * @version 0.0.1
 *
 */
@Controller
@SessionAttributes({"adminUser"})
public class AdminController implements ServletContextAware {
	/**
	 * Servlet Context is inject by the framework
	 */
	private ServletContext context;
	
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	/**
	 * Persistence Service that is injected into this controller
	 */
	@Autowired
	AdminServiceImpl service;
	
	public void setService(AdminServiceImpl service) {
		this.service = service;
	}
	
	/**
	 * Location Provider database service
	 */
	@Autowired
	private LocationProviderServiceImpl providerService;
	
	public void setProviderService(LocationProviderServiceImpl providerService) {
		this.providerService = providerService;
	}
	
	/**
	 * Email service.
	 */
	@Autowired
	EmailServiceImpl emailService;
	
	public void setEmailService(EmailServiceImpl emailService) {
		this.emailService = emailService;
	}
	
	/**
	 * Setup the Admin User session
	 * @param Admin User
	 * @param model - Set the AdminUser model attribute and the navigation attributes.
	 * 
	 * @return - Next Page
	 */
	@RequestMapping(value="setupAdminSession.request", method=RequestMethod.POST)
	protected String setupAdminSession(@ModelAttribute("adminUser") AdminUser adminUser, Model model) {
		//
		model.addAttribute("adminUser", adminUser);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminHomePage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;	
	}

	/**
	 * Return to the main page.
	 * 
	 * @param model - Put the template name that we are navigating back to display
	 * @return
	 */
	@RequestMapping(value="returnAdminMainPage.request", method=RequestMethod.GET)
	protected String returnToMainPage(Model model)
	{
		model.addAttribute("templateName", "adminHomePage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;

	}

	/**
	 * Setup the navigation template attributes
	 * 
	 * @param model - Store the navigation template attributes. Add unapproved photos collection.
	 * @return - Next Page
	 */
	@RequestMapping(value="setupApproveDeclinePhotos.request", method=RequestMethod.GET)
	protected String setupApproveDeclinePhotos(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminApproveDeclinePhotosPage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Return a map of the unapproved photos based on the search criteria provided by
	 * the user
	 * 
	 * @param unapprovedPhotos - Map of unapproved photos
	 * @param searchType - What to search on either all unapproved photos or by user name.
	 * @param userName - The user name to search by if that option was selected in searchType.
	 * @param model - Set navigation template attribute
	 * @return - Next page
	 */
	@RequestMapping(value="processProviderSearchUnapprovedPhotos.request", method=RequestMethod.POST)
	protected String processUnapprovedPhotoSearch(@RequestParam("searchByMethod") String searchByMethod, 
			@RequestParam("userName") String userName, 
			Model model) {
		List<LocationProvider>unapprovedPhotosByUser = new ArrayList<LocationProvider>();
		
		// Check to see if the radio button value is byUserName. If it is we
		// are search for images for a particular user. If not we are getting all
		// of the unapproved images.
		if(searchByMethod.equals("byUserName") ==  true) {
			LocationProvider user;
			
			user = service.searchProviderUnapprovedPhotosByUserName(userName);
			
			if(user != null) {
				// Add the user to the ArrayList which will be passed to the model
				unapprovedPhotosByUser.add(user);
			}
			else {
				// User was not found so display error message.
				model.addAttribute("errorMessage", "Could not find the User Name that was entered.");
			}
		}
		else {
			unapprovedPhotosByUser = service.searchAllProviderUnapprovedPhotos();
		}
		
		// Add the unapproved photos collection to the model to be used on the jsp page.
		model.addAttribute("unapprovedPhotosByUser", unapprovedPhotosByUser);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminApproveDeclinePhotosPage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Check the value of the each photo's radio buttons and write the selected values 
	 * to the database.
	 * 
	 * @param model - Set the navigation templagte values in the model
	 * @param request - HttpServletRequest so we can iterate over all of the request parameters.
	 * 
	 * @return - Next Page
	 */
	@RequestMapping(value="approveDeclineSkipPhotos.request", method=RequestMethod.POST)
	protected String approveDeclineSkipPhotos(Model model, HttpServletRequest request) {
		Map<String, String[]> requestParameters;
		Set<String> parameters;
		Iterator<String> iterator;
		
		requestParameters = request.getParameterMap();
		parameters = requestParameters.keySet();
		iterator = parameters.iterator();
		
		while(iterator.hasNext() == true) {
			String key;
			String [] values;
			int index;
			
			// Get the next request parameter
			key = iterator.next();
			// See if key has the word radio in it.
			index = key.indexOf("Radio");
			if(index > -1) {
				// Found it so extract the id number out of the key
				// Look for the 'o' in 'radio'
				String id, radioBttnValue, adminNotes, emailSubject, toEmailAddress;
				StringArray toEmailAddresses = new StringArray();
				StringBuilder emailBodyText = new StringBuilder();
				// If we are deleting an image, we need to do it after the email has been sent.
				boolean deleteImage = false;
				emailSubject = "";
				
				// Image object associated with this id.
				Image image;
				
				index = key.lastIndexOf('o');
				// Move the index past the 'o'.
				index++;
				
				id = key.substring(index);
				
				// Get the selected value of the radio button group
				values = requestParameters.get(key);
				radioBttnValue = values[0];

				// Get the Image object associated with this id.
				image = service.getImage(Long.valueOf(id));
				image.setAdminReviewedDate(new Date(System.currentTimeMillis()));
				// Get the admin notes for this image
				adminNotes = request.getParameter("adminNotes" + id);
				image.setAdminNotes(adminNotes);

				// Get the email address of the grandparent object
				toEmailAddress = image.getParentLocation().getLocationOwner().getEmailAddress();
				toEmailAddresses.add(toEmailAddress);
				
				if(radioBttnValue.equals("APPROVED") == true) {
					image.setStatus(PhotoStatus.APPROVED);
					
					// Setup email subject and body.
					emailSubject = "Photo has been approved.";
					emailBodyText.append("<html><body>");
					emailBodyText.append("<h3>Photo Approved.</h3>");
					emailBodyText.append("<p>The photo ");
					emailBodyText.append(image.getFileName());
					emailBodyText.append(" <img src=\"cid:inlineImage\" width=\"330\" height=\"250\"/>");
					emailBodyText.append("<br/>has been approved. The photo will now be displayed to Location Requestors that you have submitted to.</p><br/>");
					emailBodyText.append("</body></html>");
				}
				if(radioBttnValue.equals("DECLINED") == true) {
					boolean deleteDirectory = false;
					Location location;

					deleteImage = true;
					image.setStatus(PhotoStatus.DECLINED);
					// We are going to delete the file off of the server so set the deletion date
					// in the Image object. If this is the last image for this location delete the
					// the directory on the server.
					image.setDeletionDate(new Date(System.currentTimeMillis()));
					
					// Get the Location object that owns this image.
					location = image.getParentLocation();
					location.removeImage(image);
					
					// Setup email subject and body.
					emailSubject = "Photo has been declined.";
					emailBodyText.append("<html><body>");
					emailBodyText.append("<h3>Photo Declined.</h3>");
					emailBodyText.append("<p>The photo ");
					emailBodyText.append(image.getFileName());
					emailBodyText.append(" <img src=\"cid:inlineImage\" width=\"330\" height=\"250\"/>");
					emailBodyText.append("<br/>has been declined for the following reason.<br/><textareal rows=\"5\" cols=\"50\">");
					emailBodyText.append(adminNotes);
					emailBodyText.append("</textarea></p><br/>");
					emailBodyText.append("</body></html>");
				}
				if(radioBttnValue.equals("SKIPPED_ON_REVIEW") == true) {
					image.setStatus(PhotoStatus.SKIPPED_ON_REVIEW);
					// As of right now we are going to send email for skipped photos.
					// Setup email subject and body.
/*					
					emailSubject = "Photo still under review.";
					emailBodyText.append("<html><body>");
					emailBodyText.append("<h3>Photo Still Under Review.</h3>");
					emailBodyText.append("<p>The photo ");
					emailBodyText.append(image.getFileName());
					emailBodyText.append(" <img src=\"cid:inlineImage\" width=\"330\" height=\"250\"/>");
					emailBodyText.append("<br/>is still under review. You will receive an email once the review process has finished.</p><br/>");
					emailBodyText.append("</body></html>");
*/					
				}
				
				service.modifyImage(image);
				
				// Send an email to the photo provider.
				// Do not send emails for skipped photos
				if(image.getStatus() != PhotoStatus.SKIPPED_ON_REVIEW) {
					emailService.sendEmailWithInlinePicture(toEmailAddresses, image.getAbsoluteFilePath(), emailSubject, emailBodyText.toString(),GlobalVars.UPLOAD_APPROVAL_EMAIL_NAME);
				}
				
				if(deleteImage == true) {
					// We can now delete the image since the email has been sent.
					boolean deleteDirectory = false;
					
					// Check to see if this is the last image for this location.
					// If so we are going to delete the directory on the server that held
					// the images for this location
					if(image.getParentLocation().getLocationImages().isEmpty() == true) {
						deleteDirectory = true;
					}
					service.deleteImage(image, deleteDirectory);
				}
			}
		}
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminApproveDeclinePhotosPage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * 
	 * @param model - Set the template navigation attribute
	 * @return
	 */
	@RequestMapping(value="setupReviewPhotoHistory.request", method=RequestMethod.GET)
	protected String setupViewPhotoHistory(Model model) {
		// Add in an Image object to be used for search criteria
		Image searchImage = new Image();
		Location searchLocation = new Location();
		LocationProvider searchUser = new LocationProvider();
		
		// Set up the tree structure for the Image object
		searchLocation.setLocationOwner(searchUser);
		searchImage.setParentLocation(searchLocation);
		
		model.addAttribute("searchImage", searchImage);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminViewPhotoHistoryPage");
		
		return GlobalVars.ADMIN_TEMPLATE_HOME_PAGE_URL;	
	}
}
