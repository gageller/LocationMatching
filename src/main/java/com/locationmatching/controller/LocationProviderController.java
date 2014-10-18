package com.locationmatching.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;

import com.locationmatching.component.CreditCardImpl;
import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ProviderSubmission;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.enums.CreditCardType;
import com.locationmatching.enums.LocationType;
import com.locationmatching.enums.SubmissionResponse;
import com.locationmatching.enums.UserPlanType;
import com.locationmatching.exception.DuplicateLocationException;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.service.EmailService;
import com.locationmatching.service.LocationProviderServiceImpl;
import com.locationmatching.service.LocationScoutServiceImpl;
import com.locationmatching.util.GlobalVars;
import com.sun.xml.internal.fastinfoset.util.StringArray;

/**
 * Controller to handle request made by the Location Provider. This 
 * controller implements the ServletContextAware interface so the 
 * ServletContext can be wired into the class
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Controller
@SessionAttributes({"locationProvider", "location", "editLocation", "searchLocationRequest", "requestSearchResults", "newCreditCard", "editCreditCard"})
public class LocationProviderController implements ServletContextAware{
	/**
	 * We get the ServletContext so we have access to any init
	 * parameters defined in the web.xml file.
	 */
	private ServletContext context;
	
	/**
	 * String base url of this application pulled from the
	 * context init param from the web.xml file.
	 */
	private String appBaseUriString;
	
	/**
	 * Base URI of this app.
	 */
	private URI appBaseUri;
	
	/**
	 * Hibernate based service for persisting LocationProvider data
	 */
	@Autowired
	LocationProviderServiceImpl providerService;
	
	/**
	 * Hibernate based service for persisting LocationScout data.
	 */
	@Autowired()
	LocationScoutServiceImpl scoutService;
	
	public void setProviderService(LocationProviderServiceImpl providerService) {
		this.providerService = providerService;
	}
	
	public void setScoutService(LocationScoutServiceImpl scoutService) {
		this.scoutService = scoutService;
	}
	
	// Inject the email service
	@Autowired
	EmailService emailService;
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	/**
	 * Method to inject the ServletContext into our class.
	 */
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
		
		// Set the base url for this application. It is 
		// defined in the web.xml file
		appBaseUriString = context.getInitParameter("appBaseUrl");
		appBaseUri = UriBuilder.fromUri(appBaseUriString).build();
	}

	/**
	 * Used for the State select controls on the jsp pages.
	 * 
	 * @return map containing state names and abbreviations.
	 */
	@ModelAttribute("stateSelectList")
	protected Map<String, String> stateSelectList() {
		return GlobalVars.stateMap;
	}
	
	/**
	 * Used for the request to set the type of property
	 * 
	 * @return Map of Location Types
	 */
	@ModelAttribute("locationTypeList")
	protected EnumMap<LocationType, String> locationTypeList() {
		return GlobalVars.locationTypes;
	}

	/**
	 *  Used for credit card type selection on Manage Credit Cards page
	 *  
	 * @return Map of credit cards that may be used.
	 */
	@ModelAttribute("creditCardTypesMap")
	protected EnumMap<CreditCardType, String> creditCardTypesMap() {
		return GlobalVars.creditCardTypes;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	// Location Provider Actions
	////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="setupLocationProviderSession.request", method=RequestMethod.POST)
	protected String setupLocationProviderSession(@ModelAttribute("locationProvider") LocationProvider locationProvider, Model model) {
		//
		model.addAttribute("locationProvider", locationProvider);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationProviderHomePage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}

	/**
	 * Instantiate new Location Provider object and add to the Model.
	 * Then continue onto the newLocationProvider.jsp page.
	 */
	@RequestMapping(value="createNewProvider.request", method=RequestMethod.GET)
	protected String setupNewProvider(Model model) {
		LocationProvider provider = new LocationProvider();
		
		model.addAttribute("locationProvider", provider);
		
		return "/" + GlobalVars.PROVIDER_JSP_FOLDER + "/newLocationProvider";
	}
	
	/**
	 * We got here from the request to create a new Provider account after all
	 * of the provider data has been filled out.
	 * 
	 * @return String
	 */
	@RequestMapping(value="createNewProvider.request", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String createLocationProvider(LocationProvider locationProvider, HttpServletResponse response, Model model) {
		Date date = new Date(System.currentTimeMillis());
		String nextPage;
		
		// Set the current time and last accessed time for this new provider.
		locationProvider.setCreationDate(date);
		locationProvider.setLastAccessDate(date);
		// This provider to active
		locationProvider.setActive(true);
		// Set the user level to 2
		locationProvider.setUserLevel(Short.valueOf("2"));
		// Set the new user's plan type to the Pay Per Photo plan type
		locationProvider.setUserPlanType(UserPlanType.BASIC);
		
		// Go to the provider.jsp page to add locations
		nextPage = GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
		
		try {
			Long id;
			
			providerService.createUser(locationProvider);
			
			id = locationProvider.getId();
			response.setHeader("location", "/providers/" + id);
		}
		catch(UserAlreadyExistsException ex) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			// System.out.println(ex.getMessage());
			// User name already in use. Send back to the register page.
			nextPage = "forward:./index.jsp";
			// Reset the user name and password to blank before sending
			// user back to register page
			locationProvider.setUserName("");
			locationProvider.setPassword("");
			model.addAttribute("userProviderLoginErrorMessage", "This User Name already exists. Please select another User Name.");
		}
		model.addAttribute("templateName", "locationProviderHomePage");	
		
		return nextPage;
	}
	
	/**
	 * Going to the Edit Location Provider page.
	 * 
	 * @param model - Added template name that we are going to display
	 * @return - Page to return to.
	 */
	@RequestMapping(value="setupEditLocationProvider.request", method=RequestMethod.GET)
	protected String setProviderEdit(Model model) {
		model.addAttribute("templateName", "editLocationProviderPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Update the database with the updated LocationProvider info.
	 * 
	 * @param locationProvider - LocationProvider whose info has been edited.
	 * @param model - Put the template name that we are navigating back to display
	 * @return
	 */
	@RequestMapping(value="editLocationProvider.request", method=RequestMethod.POST)
	protected String editProviderInfo(@ModelAttribute("locationProvider") LocationProvider locationProvider,
			Model model) {
		// Persist updated LocationProvider data.
		providerService.modifyUser(locationProvider);
		
		model.addAttribute("templateName", "locationProviderHomePage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Return to the main page.
	 * 
	 * @param model - Put the template name that we are navigating back to display
	 * @return
	 */
	@RequestMapping(value="returnProviderMainPage.request", method=RequestMethod.GET)
	protected String returnToMainPage(Model model)
	{
		model.addAttribute("templateName", "locationProviderHomePage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;

	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Location Actions
	//////////////////////////////////////////////////////////////////////////////////
	/**
	 * Setup a new location object that will be 
	 * populated once going to the addLocation.jsp page
	 */
	@RequestMapping(value="addLocation.request", method=RequestMethod.GET)
	protected String setupNewLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, Model model) {
		Location location = new Location();
		Date currentDate = new Date(System.currentTimeMillis());
		
		// Set the number of free and paid for photos to 0 for new location
		location.setNumberOfFreePhotos(0);
		location.setNumberOfPaidPhotos(0);
		
		// Set the number of free and paid for videos to 0
		location.setNumberOfFreeVideos(0);
		location.setNumberOfPaidVideos(0);
		
		// Set this as active. May not need active flag anymore.
		location.setActive(true);
	
		// Set the creation and modified date.
		location.setCreationDate(currentDate);
		location.setModifiedDate(currentDate);
		
		model.addAttribute("location", location);
		model.addAttribute("templateName", "addLocationPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Persist new Location object to the database.
	 * 
	 * @param locationProvider - Add new location object to this LocationProvider instance.
	 * @param location - New Location to add.
	 * @param model - Add variable to model for page navigation.
	 * @param session - HttpSession object so we can add the name of the view template to
	 * use after the user has finished adding photos.
	 * @return
	 */
	@RequestMapping(value="addLocation.request", method=RequestMethod.POST)
	protected String addNewLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, 
			@ModelAttribute("location") Location location,
			Model model, HttpSession session) {
		String nextPage = "addLocationPage";

		try {
			
			// Set the name of the template to use for the view after the
			// user has finished adding photos.
			session.setAttribute("nextTemplateName", "locationProviderHomePage");
			locationProvider.addLocation(location);
			
			// Make sure the address for this location does not already exist in the database. 
			// If it does, a DuplicateLocationException will be thrown by the providerService.
			providerService.validateLocation(location);
			
			// Location does not already exist so add it.
			providerService.addLocation(location);
			
			// Add the number of free photos remaining to the model so it is displayed
			// on the add photo page. Since this is a new Location it will be the maximum 
			// for this plan type. So get the provider's plan type and get the maximum
			// photo count.
			UserPlanType userPlanType = locationProvider.getUserPlanType();
			int numberOfFreePhotosRemaining;
			
			numberOfFreePhotosRemaining = GlobalVars.BASIC_FREE_PHOTO_AMOUNT;
			
			if(userPlanType == UserPlanType.PREMIUM) {
				numberOfFreePhotosRemaining = GlobalVars.PREMIUM_FREE_PHOTO_AMOUNT;
			}

			model.addAttribute("numberOfRemainingFreePhotos", numberOfFreePhotosRemaining);
			nextPage = "addPhotoPage";
		}
		catch(DuplicateLocationException ex) {
			String errorMessage;
			
//			ex.printStackTrace();
			
			errorMessage = ex.getExceptionMessage();
			model.addAttribute("errorMessage", errorMessage);
		}
		catch(LocationProcessingException ex) {
			ex.printStackTrace();
			model.addAttribute("errorMessage", ex.getExceptionMessage());
		}
		
		model.addAttribute("templateName", nextPage);
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Update the LocationProvider's new Location with the added photos. Persist data to
	 * database. We can get to this method from the Add Photo page when adding a new location
	 * or from the Manage Photos page if a location is being edited.
	 * 
	 * @param location - The location object that was created or is being modified.
	 * @param coverPhotoImageId - id of the image to set as the main/cover image.
	 * @param model - To set the name of the next template to use for the view. This name is 
	 * picked up on the jsp through expression language.
	 * @param session - HttpSession to retrieve the name of the next template to 
	 * use for the view.
	 * 
	 * @return Next Page.
	 */
	@RequestMapping(value="returnFromFileUpload.request", method=RequestMethod.POST)
	protected String returnFromFileUpload(@ModelAttribute("location")Location location, 
			@RequestParam(value="mainPhotoRadio", defaultValue="0") long coverPhotoImageId,
			Model model,
			HttpSession session) {
		String nextTemplateName, emailSubject;
		StringArray toEmailAddresses;
		StringBuilder emailBodyText;
		LocationProvider locationProvider;
		
		location.setCoverPhotoUrl(coverPhotoImageId);
		location.setCoverPhotoId(coverPhotoImageId);
		
		providerService.setCoverPicture(location);
		
		// Name of the template to use for the next view.
		nextTemplateName = (String) session.getAttribute("nextTemplateName");
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", nextTemplateName);
		
		// Send an email to the Admin telling them that there are photos
		// that need to be approved.
		// Get the owner of this location.
		toEmailAddresses = new StringArray();
		emailBodyText = new StringBuilder();
		
		// Email Subject
		emailSubject = "Photos needing review";
		
		// Email Body Text
		// Get the owner of the location so we can get their user name
		// for the email body text.
		locationProvider = location.getLocationOwner();
		
		// Set the modified date for this location.
		location.setModifiedDate(new Date(System.currentTimeMillis()));
		providerService.modifyLocation(location);
		
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
		format.applyPattern("MM/dd/yyyy");

		emailBodyText.append(locationProvider.getUserName());
		emailBodyText.append(" has submitted new photos on ");
		emailBodyText.append(format.format(new Date(System.currentTimeMillis())));
		emailBodyText.append(" that need to be reviewed");
		
		toEmailAddresses.add(GlobalVars.UPLOAD_APPROVAL_EMAIL_NAME);
		
		// Send the email to support to alert them that there are photos to review.
		emailService.sendEmail(toEmailAddresses, emailSubject, emailBodyText.toString(), GlobalVars.UPLOAD_APPROVAL_EMAIL_NAME);
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Setup the template to be used for the listings of locations
	 * to edit.
	 * 
	 * @param model - Add the template name to the model
	 * @return
	 */
	@RequestMapping(value="setupEditLocationListings.request", method=RequestMethod.GET)
	protected String setupEditLocationListings(Model model) {
		model.addAttribute("templateName", "editLocationListingsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * 
	 * @param editId - Id of the Location to edit
	 * @param locationProvider - List of Locations for this Location Provider
	 * @param model - To set the name of the template to use for the next view.
	 * @param session - set the name of the template to use after the user has
	 * finished adding their photos.
	 * @return
	 */
	@RequestMapping(value="editLocationSetup.request", method=RequestMethod.POST)
	protected String editLocationSetup(@RequestParam("editId") long editId, 
			@ModelAttribute("locationProvider")LocationProvider locationProvider, 
			Model model,
			HttpSession session) {
		Set<Location> providerLocations;
		Location editLocation = null;
		Iterator<Location>itr;
		
		providerLocations = locationProvider.getProviderLocations();
		itr = providerLocations.iterator();


		while(itr.hasNext() == true) {
			Long id;
			
			editLocation = itr.next();
			id = editLocation.getId();
			
			if(id.equals(editId) == true) {
				break;
			}
		}
		model.addAttribute("location", editLocation);
		model.addAttribute("templateName", "editLocationPage");
		
		// Set the name of the template to use after the user has
		// finished adding the photos.
		session.setAttribute("nextTemplateName", "editLocationListingsPage");

		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Update the edited location to the database
	 */
	@RequestMapping(value="editLocation.request", method=RequestMethod.POST)
	protected String editLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, 
			@ModelAttribute("location") Location editLocation,
			Model model,
			HttpSession session) {
		// Set the modified date for this location
		editLocation.setModifiedDate(new Date(System.currentTimeMillis()));
		// Persist the modifications to the database.
		providerService.modifyLocation(editLocation);
		
		model.addAttribute("templateName", "editLocationListingsPage");

		// Set the name of the template to use after the user has
		// finished add the photos.
		session.setAttribute("nextTemplateName", "editLocationListingsPage");

		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Setup the ModelAttribute LocationRequest to be used to fill
	 * in the parameters for the search.
	 * 
	 * @param Model - Add new LocationRequest object to hold search filter options and set navigation template variables.
	 * @return String
	 */
	@RequestMapping(value="setupSearchLocationRequest.request", method=RequestMethod.GET)
	protected String setupSearchLocationRequests(Model model) {
		LocationRequest searchLocationRequest;
		
		searchLocationRequest = new LocationRequest();
		
		model.addAttribute("searchLocationRequest", searchLocationRequest);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "searchLocationRequestsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Take the filter parameters passed in the LocationRequest object and return a result set
	 * of LocationRequests that match.
	 * 
	 * @param searchLocationRequest - LocationRequest object that holds the filter values for retrieving the result set
	 * @param model - Add the returned results to the model. Add navigation template parameters.
	 * @return
	 */
	@RequestMapping(value="processSearchLocationRequest.request", method=RequestMethod.POST)
	protected String searchLocationRequests(@ModelAttribute("searchLocationRequest")LocationRequest searchLocationRequest, Model model) {
		Map<Long, LocationRequest> locationRequests;

		locationRequests = scoutService.getLocationRequests(searchLocationRequest);
		
		if(locationRequests != null) {
			model.addAttribute("requestSearchResults", locationRequests);
		}
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "searchLocationRequestsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	@RequestMapping(value="setupAddPhoto.request", method=RequestMethod.GET)
	protected String setupAddPhoto(@ModelAttribute("location")Location location, Model model) {
		int planTotalFreePhotos, numberOfFreePhotosRemaining, numberOfFreePhotosUsed;
		
		// Get the Location Provider of this Location User Plan Type
		UserPlanType userPlanType = location.getLocationOwner().getUserPlanType();
		
		// Get the number of total free pictures depending on the plan type
		if(userPlanType == UserPlanType.PREMIUM) {
			planTotalFreePhotos = GlobalVars.PREMIUM_FREE_PHOTO_AMOUNT;
		}
		else {
			planTotalFreePhotos = GlobalVars.BASIC_FREE_PHOTO_AMOUNT;
		}
		
		numberOfFreePhotosUsed = location.getNumberOfFreePhotos();
		numberOfFreePhotosRemaining = planTotalFreePhotos - numberOfFreePhotosUsed;
		
		// If the number of free photos remaining is 0, display a message to the user to
		// let them know that they need to pay for additional photos
		if(numberOfFreePhotosRemaining == 0) {
			String payForPhotosMessage = new String();
			
			payForPhotosMessage = String.format(GlobalVars.PAY_FOR_PHOTO_MESSAGE, GlobalVars.PRICE_PER_LOCATION_PHOTO);
			model.addAttribute("payForPhotosMessage", payForPhotosMessage);
		}
		
		// Display the number of free photos left.
		model.addAttribute("numberOfRemainingFreePhotos", Long.valueOf(numberOfFreePhotosRemaining));
		
		// Set whether we need to process photos yet to false. This is used in case the user tries
		// to navigate away from the addPhoto page with out saving uploaded photo data.
		model.addAttribute("processPhotos", "false");
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "addPhotoPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	@RequestMapping(value="addPhotoPageReturn.request", method=RequestMethod.POST)
	protected String addPhotoPageReturn(Model model) {
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "addPhotoPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Setup the Manage Photos jsp page.
	 * 
	 * @param location - The Location object that contains these pictures
	 * @param model - Add navigation template variable. Add the number of 
	 * remaining free photos to the model to display on the jsp page.
	 * @return
	 */
	@RequestMapping(value="setupManageExistingPhotos.request", method=RequestMethod.GET)
	protected String setupManageExistingPhotos(@ModelAttribute("location") Location location, Model model) {
		int numberOfFreePhotos, planTotalFreePhotos, numberOfRemainingFreePhotos = 0;
		
		numberOfFreePhotos = location.getNumberOfFreePhotos();
		
		// Get the Location Provider of this Location User Plan Type
		UserPlanType userPlanType = location.getLocationOwner().getUserPlanType();
		
		if(userPlanType == UserPlanType.PREMIUM) {
			planTotalFreePhotos = GlobalVars.PREMIUM_FREE_PHOTO_AMOUNT;
		}
		else {
			planTotalFreePhotos = GlobalVars.BASIC_FREE_PHOTO_AMOUNT;
		}
		
		// Check to see if the location has any free photos remaining.
		if(numberOfFreePhotos < planTotalFreePhotos) {
			// The user still has free photos for this location.
			numberOfRemainingFreePhotos = planTotalFreePhotos - numberOfFreePhotos;
		}
		
		model.addAttribute("numberOfRemainingFreePhotos", Long.valueOf(numberOfRemainingFreePhotos));
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "managePhotosPage");

		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	
	@RequestMapping(value="manageExistingPhotos.request", method=RequestMethod.POST)
	protected String manageExistingPhotos(@ModelAttribute("location") Location location, Model model, HttpServletRequest request) {
		Map<String, String[]> requestParameters;
		Set<String> parameters;
		Iterator<String> iterator;
		String coverPhotoId;
		Long newCoverPhotoId, currentCoverPhotoId;
		
		// Get the id of the photo that was selected to be the Main/Cover photo
		// and see if it has been changed. If so, we need to update the Image objects
		coverPhotoId = request.getParameter("mainPhotoRadio");
		newCoverPhotoId = Long.valueOf(coverPhotoId);
		currentCoverPhotoId = location.getCoverPhotoId();
		
		if(currentCoverPhotoId.equals(newCoverPhotoId) == false) {
			// Change of id so we need to set the new values
			// Set the location's cover photo URL and id
			location.setCoverPhotoUrl(Long.valueOf(coverPhotoId));
			location.setCoverPhotoId(newCoverPhotoId);
			
			// Save the change to the database.
			providerService.modifyLocation(location);
		}
		
		requestParameters = request.getParameterMap();
		parameters = requestParameters.keySet();
		iterator = parameters.iterator();
		
		// Iterate through the request variables and update the Image objects
		// hidden variable and main photo settings.
		while(iterator.hasNext() == true) {
			String key;
			String [] values;
			int index;
			
			// Get the next request parameter
			key = iterator.next();
			// See if key has the word imageHideUnhideRadio in it.
			index = key.indexOf("imageHideUnhideRadio");
			if(index > -1) {
				// Found one of the hide/unhide radio buttons
				String imageId;
				
				// Pull the image id off the end of the "imageHideUnhideRadio" name
				index = key.lastIndexOf('o');
				
				if(index > -1) {
					// Found the id. Now retrieve and process the image.
					Image image;
					String hideUnhideValue;
					
					// Move the index past the 'o'.
					index++;
					
					imageId = key.substring(index);
					image = location.getImage(Long.valueOf(imageId));
					
					values = requestParameters.get(key);
					
					hideUnhideValue = values[0];
					image.setHidden(new Boolean(hideUnhideValue));
					
					// Update the changes to the database
					providerService.modifyImage(image);
				}
			}
		}


		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editLocationListingsPage");

		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;
	}
	
	/**
	 * Setup the navigation to the correct tile template.
	 * 
	 * @param model - Add the template parameters for the delete photos page
	 * @return
	 */
	@RequestMapping(value="setupDeletePhotos.request", method=RequestMethod.GET)
	protected String setupDeletePhotos(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "deletePhotosPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	@RequestMapping(value="deletePhotos.request", method=RequestMethod.POST)
	protected String deletePhotos(@ModelAttribute("location") Location location, @RequestParam("deleteCheck") String[] photosToDelete, Model model) {
		// Delete images from server and mark the Images in database as DELETED.
		// Set the location's modified date
		location.setModifiedDate(new Date(System.currentTimeMillis()));
		providerService.modifyLocation(location);
		
		providerService.deleteImages(location, photosToDelete);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationProviderHomePage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Save location request submission, add to the Location, and send an email 
	 * to the Location Scout alerting them of the submission.
	 * 
	 * @param locationRequests - Map of Location Requests.
	 * @param locationProvider - The Location Provider that made the submission.
	 * @param locationRequestId - Id of the Location Request that needs to be
	 * pulled out of the map.
	 * @param locationId - Id of the Location that was submitted.
	 * @param model - Add message for successful or failed submissions. Add navigation to the correct template.
	 * @return - Next page
	 */
	@RequestMapping(value="processLocationRequestSubmission.request", method=RequestMethod.POST)
	protected String processLocationRequestSubmission(@ModelAttribute("requestSearchResults")TreeMap<Long, LocationRequest>locationRequests, 
			@ModelAttribute("locationProvider") LocationProvider locationProvider,
			@RequestParam("locationRequestId")long locationRequestId,
			@RequestParam("locationId") long locationId,
			Model model) {
		LocationRequest locationRequest;
		LocationScout scout;
		Location location;
		ProviderSubmission newSubmission = new ProviderSubmission();
		Image coverImage;
		String message;
		
		location = locationProvider.getLocation(locationId);
		locationRequest = locationRequests.get(locationRequestId);
		
		// Check to see if this has already been submitted. If so, show error message
		if(locationProvider.hasLocationBeenSubmittedWithThisRequest(locationId, locationRequestId) == false) {
			scout = locationRequest.getRequestOwner();
		
			newSubmission.setLocationRequestId(locationRequestId);
			newSubmission.setLocationId(locationId);
			newSubmission.setSubmissionDate(new Date(System.currentTimeMillis()));
			newSubmission.setSubmissionResponse(SubmissionResponse.NOT_RESPONDED);
			
			// Add to the LocationProvider requestSubmissions collection and also
			// set the parent pointer of the ProviderSubmission object.
			locationProvider.addRequestSubmission(newSubmission);
			
			// Persist the new submission
			providerService.addProviderSubmission(newSubmission);
			
			// Add an alert to the Location Scout
			ScoutAlert scoutAlert = new ScoutAlert();
			scoutAlert.setCreationDate(new Date(System.currentTimeMillis()));
			scoutAlert.setLocationId(locationId);
			scoutAlert.setLocationRequestId(locationRequestId);
			scoutAlert.setLocationProviderId(locationProvider.getId());
			scoutAlert.setViewed(false);
			scoutAlert.setShowAlert(true);
			
			scout.addRequestAlert(scoutAlert);
			// Persist the new alert
			scoutService.addScoutAlert(scoutAlert);
			
			StringBuilder emailBodyText = new StringBuilder();
			
			emailBodyText.append("<html><body>");
			emailBodyText.append("<h3>Location Submitted</h3>");
			emailBodyText.append("<p>A Location Provider has submitted their property for your approval. ");
			emailBodyText.append("Please login into your account to view pictures of this property.</p><br/>");
			emailBodyText.append("<img src=\"cid:inlineImage\"/>");
			emailBodyText.append("</body></html>");
			
			// Find which of the location images is set to be the main photo.
			coverImage = location.getCoverImage();
			StringArray emailAddress = new StringArray();
			
			emailAddress.add(scout.getEmailAddress());
			if(coverImage != null && scout != null) {
				emailService.sendEmailWithInlinePicture(emailAddress, coverImage.getAbsoluteFilePath(), "Location Submitted", emailBodyText.toString(), GlobalVars.SUPPORT_EMAIL_ADDRESS);
			}
			message = "Submission successful.";
			model.addAttribute("successfulSubmissionMessage", message);
		}
		else {
			message = "This location has already been submitted for this request.";
			model.addAttribute("errorSubmissionMessage", message);
		}
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "searchLocationRequestsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}

	/**
	 * Setup the navigation tile for the Delete Locations page.
	 * 
	 * @param model - Setup the parameters for the delete photos layout
	 * @return - Page being navigated to.
	 */
	@RequestMapping(value="setupDeleteLocation.request", method=RequestMethod.GET)
	protected String setupDeleteLocations(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "deleteLocationPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Delete selected Location objects
	 * 
	 * @param locationProvider - Iterate through this LocationProvider's locations and match
	 * the ids to the ones passed in by checkValues.
	 * @param checkValues - String Array of check box values.
	 * @param model - Setup our template values for returning to the next page.
	 * @return
	 */
	@RequestMapping(value="deleteLocations.request", method=RequestMethod.POST)
	protected String deleteLocations(@ModelAttribute("locationProvider") LocationProvider locationProvider,
			@RequestParam("deleteCheck") String[] locationsToDelete,
			Model model) {
		providerService.deleteLocations(locationProvider, locationsToDelete);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationProviderHomePage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Setup the ProviderSubmissions in the collection by setting the LocationRequest object
	 * associated with the locationRequestId variable of the ProviderSubmission instance.
	 * 
	 * @param locationProvider - Object containing the collection of ProviderSubmissions
	 * @param model - Add next page navigation attribute to the model.
	 * @return - Logical name of the next view
	 */
	@RequestMapping(value="setupSubmissions.request", method=RequestMethod.GET)
	protected String setupSubmissions(@ModelAttribute("locationProvider") LocationProvider locationProvider,
			Model model) {
		Iterator<ProviderSubmission> iterator;
		
		iterator = locationProvider.getRequestSubmissions().iterator();
		// Add the Location and LocationRequest Objects to the ProviderSubmissions
		while(iterator.hasNext() == true) {
			ProviderSubmission submission;
			Location location;
			LocationRequest locationRequest;
			Long id;
			
			submission = iterator.next();
			
			// Set the Location object so it can be displayed on the view submission jsp page.
			id = submission.getLocationId();
			location = providerService.getLocation(id);
			submission.setLocation(location);
			
			// Set the LocationRequest object so it can be displayed on the view submission jsp page.
			id = submission.getLocationRequestId();
			locationRequest = scoutService.getLocationRequest(id);
			submission.setLocationRequest(locationRequest);
		}
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationSubmissionsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Set the next page template attributes. Add a new CreditCardImpl
	 * object and the primary CreditCardImpl object to the model to use
	 * for information on the page.
	 * 
	 * @param locationProvider - The current user.
	 * @param model - Set the next page template attributes.
	 * @return Next Page
	 */
	@RequestMapping(value="setupManagePayments.request", method=RequestMethod.GET)
	protected String setupManagePayments(@ModelAttribute("locationProvider") LocationProvider locationProvider, Model model) {
		CreditCardImpl newCreditCard, primaryCreditCard = null;
		Set<CreditCardImpl>creditCards = locationProvider.getCreditCards();
		
		// We will add this newly instantiated Credit Card to the model to
		// be used if the user wants to add a new credit card.
		newCreditCard = new CreditCardImpl();
		
		// See if there is already an existing credit card assigned to this account.
		// If there is, get the first card in the collection and we will use that as 
		// the default billing address for adding a new card.
		primaryCreditCard = locationProvider.getPrimaryCreditCard();
		if(primaryCreditCard != null) {
			// Set the new credit card address fields to default to the primary card
			// address fields.
			newCreditCard.setBillingAddress(new String(primaryCreditCard.getBillingAddress()));
			newCreditCard.setBillingAddress2(new String(primaryCreditCard.getBillingAddress2()));
			newCreditCard.setBillingCity(new String(primaryCreditCard.getBillingCity()));
			newCreditCard.setBillingState(primaryCreditCard.getBillingState());
			newCreditCard.setBillingZipcode(new String(primaryCreditCard.getBillingZipcode()));
		}
		else {
			// Add an empty credit card object to the model
			primaryCreditCard = new CreditCardImpl();
		}
		
		// Add the new credit card and existing credit card objects to the model
		model.addAttribute("newCreditCard", newCreditCard);
		model.addAttribute("primaryCreditCard", primaryCreditCard);
		
		// Add the GlobalVars class so it can be used on the page.
		model.addAttribute("maxCreditCardsAllowed", GlobalVars.MAX_CREDIT_CARDS);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "manageProviderCreditCardsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * We are coming here from the FileUploadController because the user did not have a credit
	 * card set or the expiration of the primary credit card has already expired.
	 * 
	 * @param locationProvider - Pass the model to the setupManagePayments method.
	 * @param model - Pass the model to the setupManagePayments method.
	 * 
	 * @return - Next page
	 */
	@RequestMapping(value="setupManagePayments.request", method=RequestMethod.POST)
	protected String gotoCreditCardManagePageFromFileUploadController(@ModelAttribute("locationProvider") LocationProvider locationProvider, Model model) {
		return setupManagePayments(locationProvider, model);
	}
	
	/**
	 * Add a new credit card entry.
	 * 
	 * @param newCreditCard - New Credit Card object with data filled from the manageProviderCreditCardAccounts.jsp
	 * @param locationProvider
	 * @param model - Set the navigation template variables
	 * 
	 * @return
	 */
	@RequestMapping(value="addCreditCard.request", method=RequestMethod.POST)
	protected String addCreditCard(@ModelAttribute("newCreditCard") CreditCardImpl newCreditCard, 
			@ModelAttribute("locationProvider") LocationProvider locationProvider,
			Model model) {
		Date currentDate, expirationDate;
		Calendar calendar;
		
		// Add the GlobalVars class so it can be used on the page.
		model.addAttribute("maxCreditCardsAllowed", GlobalVars.MAX_CREDIT_CARDS);
		
		calendar = Calendar.getInstance();
		
		// Subtract 1 because Months are 0 based.
		calendar.set(Calendar.MONTH, Integer.valueOf(newCreditCard.getExpirationMonth()) - 1);
		calendar.set(Calendar.YEAR, Integer.valueOf(newCreditCard.getExpirationYear()));
		
		// Make sure the expiration date is after the current date.
		expirationDate =  calendar.getTime();
		currentDate = new Date(System.currentTimeMillis());
		
		if(expirationDate.after(currentDate) == true) {
			// Check to see if there is already an active credit card with
			// this account number.
			if(locationProvider.duplicateActiveCreditCard(newCreditCard.getAccountNumber()) == false) {
				// No duplicates found so add credit card
				boolean primaryCreditCard;
				
				primaryCreditCard = false;
				newCreditCard.setCreationDate(currentDate);
				newCreditCard.setActive(true);
				if((locationProvider.getPrimaryCreditCard()) == null) {
					// This is the first one so set it as default.
					primaryCreditCard = true;
				}
				newCreditCard.setPrimaryCreditCard(primaryCreditCard);
				
				locationProvider.addCreditCard(newCreditCard);
				providerService.addCreditCard(newCreditCard);			
			}
			else {
				// Duplicate found so display error message to the user.
				String errorMessage;
				
				errorMessage = "A credit card with this account number already exists. Please use a different credit card.";
				model.addAttribute("errorMessage", errorMessage);
				model.addAttribute("templateName", "manageProviderCreditCardsPage");
				
				return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;			
			}
		}
		else {
			String errorMessage;
			
			// Card has already expired so display error message to the user.
			errorMessage = "The expiration date entered has already expired. Please use an active credit card.";
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("templateName", "manageProviderCreditCardsPage");
			
			return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;			
		}
		// Set the name of the template to use for the next view
//		model.addAttribute("templateName", "manageProviderCreditCardsPage");
		
//		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
		return "forward:/setupManagePayments.request";
	}
	
	/**
	 * Setup the editCreditCard page.
	 * 
	 * @param creditCardId - Id of the CreditCardImpl object to edit.
	 * @param model - Add data to the model to be used on the web page
	 * 
	 * @return - The name of the page that we are going to.
	 */
	@RequestMapping(value="setupEditCreditCard.request", method=RequestMethod.POST)
	protected String setupEditCreditCard(@ModelAttribute("creditCardId") long creditCardId, Model model) {
		CreditCardImpl editCreditCard;
		
		// Get the CreditCard object and add it to the model
		editCreditCard = providerService.getCreditCard(creditCardId);
		model.addAttribute("editCreditCard", editCreditCard);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editProviderCreditCardsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Edit the Credit Card info.
	 * 
	 * @param editCreditCard - Credit Card object to update
	 * @param locationProvider - Modify the Credit Card object in the provider collection
	 * @param model - Set the navigation parameters
	 * 
	 * @return - Next page
	 */
	@RequestMapping(value="editCreditCard.request", method=RequestMethod.POST)
	protected String updateCreditCardInfo(@ModelAttribute("editCreditCard") CreditCardImpl editCreditCard, 
			@ModelAttribute("locationProvider")LocationProvider locationProvider, Model model) {
		Long creditCardId;
		
		creditCardId = editCreditCard.getId();
		
		// Set the modification date
		editCreditCard.setModifiedDate(new Date(System.currentTimeMillis()));
		
		providerService.modifyCreditCard(editCreditCard);
		
		// Remove the existing Credit Card object and add the updated one.
		locationProvider.removeCreditCard(creditCardId);
		locationProvider.addCreditCard(editCreditCard);
		
		// Add the GlobalVars class so it can be used on the page.
		model.addAttribute("maxCreditCardsAllowed", GlobalVars.MAX_CREDIT_CARDS);

		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "manageProviderCreditCardsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
	
	/**
	 * Deleting a credit card does actually delete the entry from the database.
	 * We mark the credit card active variable to false.
	 * 
	 * @param creditCardId - Id of the CreditCard object to mark as deleted
	 * @param model - Setting navigation attributes.
	 * 
	 * @return - Next Page
	 */
	@RequestMapping(value="deleteCreditCard.request", method=RequestMethod.POST)
	protected String deleteCreditCard(@ModelAttribute("locationProvider") LocationProvider locationProvider,
			@ModelAttribute("creditCardId") long creditCardId, Model model) {
		CreditCardImpl creditCard;
		boolean isPrimaryCard;
		
		creditCard = providerService.getCreditCard(creditCardId);
		// See if this is the primary credit card for this account.
		isPrimaryCard = creditCard.isPrimaryCreditCard();
		
		// Set the deletion attributes
		creditCard.setActive(false);
		creditCard.setDeactivationDate(new Date(System.currentTimeMillis()));
		creditCard.setPrimaryCreditCard(false);
		
		// Update in the database
		providerService.modifyCreditCard(creditCard);
		// Remove the credit card from the provider collection
		locationProvider.removeCreditCard(creditCard.getId());
		
		// If this is the primary card, we need to set another card as
		// the primary card.
		Iterator<CreditCardImpl>iterator;
		creditCard = null;
		
		iterator = locationProvider.getCreditCards().iterator();
		
		if(iterator.hasNext() == true) {
			creditCard = iterator.next();
		}
		
		if(creditCard != null) {
			creditCard.setPrimaryCreditCard(true);
			providerService.modifyCreditCard(creditCard);
		}
		
		// Add the GlobalVars class so it can be used on the page.
		model.addAttribute("maxCreditCardsAllowed", GlobalVars.MAX_CREDIT_CARDS);

		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "manageProviderCreditCardsPage");
		
		return GlobalVars.PROVIDER_TEMPLATE_HOME_PAGE_URL;	
	}
}
