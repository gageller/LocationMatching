package com.locationmatching.controller;

import java.net.URI;
import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ProviderSubmission;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.domain.User;
import com.locationmatching.enums.UserPlanType;
import com.locationmatching.enums.LocationType;
import com.locationmatching.enums.SubmissionResponse;
import com.locationmatching.enums.UserType;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.helper.UploadForm;
import com.locationmatching.service.EmailService;
import com.locationmatching.service.LocationProviderService;
import com.locationmatching.service.LocationProviderServiceImpl;
import com.locationmatching.service.LocationScoutService;
import com.locationmatching.util.GlobalVars;

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
@SessionAttributes({"locationProvider", "location", "editLocation", "searchLocationRequest", "requestSearchResults"})
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
	LocationProviderService providerService;
	
	/**
	 * Hibernate based service for persisting LocationScout data.
	 */
	@Autowired
	LocationScoutService scoutService;
	
	public void setProviderService(LocationProviderService providerService) {
		this.providerService = providerService;
	}
	
	public void setScoutService(LocationScoutService scoutService) {
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
	 */
	@ModelAttribute("stateSelectList")
	protected Map<String, String> stateSelectList() {
		return GlobalVars.stateMap;
	}
	
	// Used for the request to set the type of property
	@ModelAttribute("locationTypeList")
	protected EnumMap<LocationType, String> locationTypeList() {
		return GlobalVars.locationTypes;
	}

	/**
	 * Get the Location Provider based on the id passed in.
	 */
	@RequestMapping(value="getProvider.request", method=RequestMethod.GET)
	public ModelAndView getProvider(@PathVariable("id") String id) {
		ModelAndView modelView = new ModelAndView();
		LocationProvider user = (LocationProvider) providerService.getUser(Long.valueOf(id));
		
		modelView.addObject("", user);
		modelView.setViewName("provider");
		
		return modelView;
	}
	
	/**
	 * Authenticate the provider logging in. If not found, send back to
	 * login page else send to their provider page.
	 * 
	 * @return String
	 */
	@RequestMapping(value="/providerNavigation.request", method=RequestMethod.POST)
	public String authenticateUser(@RequestParam("providerUserName")String userName,
			@RequestParam("providerPassword")String password, Model model) {
		String view;
		LocationProvider provider;
		
		provider = (LocationProvider) providerService.authenticateUser(userName, password);
		
		if(provider != null) {
			// Found the provider so proceed onto the provider
			// info page.
			model.addAttribute("locationProvider", provider);
			model.addAttribute("mainIFrameUrl", "provider.jsp");
			view = "iFrames";
		}
		else {
			// Not found. Need to add code to handle error message
			view = "redirect:/index.jsp";
		}
		
		return view;
	}
	
	/**
	 * Get all of the Location Providers in the system
	 */
	@RequestMapping(value="getAllProviders.request", method=RequestMethod.GET)
	public String getAllProviders(Model model) {
		List<User> providerList;
		
		providerList = providerService.getAllUsers();
		
		model.addAttribute("providerList", providerList);
		
		return "allProviders";
	}
	
	/**
	 * Delete the Location Provider based on the id passed in.
	 */
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public String deleteProvider(@PathVariable("id") String id) {
		providerService.deleteUser(Long.valueOf(id));
		
		return "deletedUser";
	}
	
	/**
	 * Instantiate new Location Provider object and add to the Model.
	 * Then continue onto the newLocationProvider.jsp page.
	 */
	@RequestMapping(value="createNewProvider.request", method=RequestMethod.GET)
	protected String setupNewProvider(Model model) {
		LocationProvider provider = new LocationProvider();
		
		model.addAttribute("locationProvider", provider);
		
		return "newLocationProvider";
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
		// Set the user type
		locationProvider.setUserType(UserType.PROVIDER);
		// Set the new user's plan type to the Pay Per Photo plan type
		locationProvider.setUserPlanType(UserPlanType.BASIC);
		
		// Go to the provider.jsp page to add locations
		nextPage = "providerNavigation";
		
		try {
			Long id;
			
			providerService.createUser(locationProvider);
			
			id = locationProvider.getId();
			response.setHeader("location", "/providers/" + id);
		}
		catch(UserAlreadyExistsException ex) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			System.out.println(ex.getMessage());
			// User name already in use. Send back to the register page.
			nextPage = "forward:./index.jsp";
			// Reset the user name and password to blank before sending
			// user back to register page
			locationProvider.setUserName("");
			locationProvider.setPassword("");
			model.addAttribute("userProviderAlreadyExistsMessage", "This User Name already exists. Please select another User Name.");
		}
		
		return nextPage;
	}
	
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

		return "addLocation";
	}
	
	/**
	 * Persist the new location to the database
	 */
	@RequestMapping(value="addLocation.request", method=RequestMethod.POST)
	protected String addNewLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, 
			@ModelAttribute("location") Location location) {
		try {
			locationProvider.addLocation(location);
			providerService.addLocation(location);
		}
		catch(LocationProcessingException ex) {
			
		}
		
		return "addPhoto";
	}
	
	/**
	 * Update the LocationProvider's new Location with the added photos. Persist data to
	 * database.
	 * 
	 * @param locationProvider
	 * @param location
	 * @return 
	 */
	@RequestMapping(value="returnFromFileUpload.request", method=RequestMethod.POST)
	protected String returnFromFileUpload(@ModelAttribute("locationProvider")LocationProvider locationProvider,
			@ModelAttribute("location")Location location, 
			@RequestParam(value="mainPhotoRadio", defaultValue="0") long coverPhotoImageId,
			@RequestParam("nextPage") String nextPage) {
		location.setCoverPhotoUrl(coverPhotoImageId);
		providerService.modifyLocation(location);
		
		return nextPage;
	}
	
	/**
	 * Setup the edit location page by getting location requested for editing
	 * 
	 * @param editId
	 * @return String
	 */
	@RequestMapping(value="editLocationSetup.request", method=RequestMethod.POST)
	protected String editLocationSetup(@RequestParam("editId") int editId, @ModelAttribute("locationProvider")LocationProvider locationProvider, Model model) {
		Set<Location> providerLocations;
		Location editLocation = null;
		Iterator<Location>itr;
		
		providerLocations = locationProvider.getProviderLocations();
		itr = providerLocations.iterator();


		while(itr.hasNext() == true) {
			Long id;
			
			editLocation = itr.next();
			id = editLocation.getId();
			
			if(id == editId) {
				break;
			}
		}
		model.addAttribute("location", editLocation);
		
		return "editLocation";
	}
	
	/**
	 * Update the edited location to the database
	 */
	@RequestMapping(value="editLocation.request", method=RequestMethod.POST)
	protected String editLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, @ModelAttribute("location") Location editLocation) {
		// Persist the modifications to the database.
		providerService.modifyLocation(editLocation);
		
		return "editLocationListings";
	}
	
	/**
	 * Setup the ModelAttribute LocationRequest to be used to fill
	 * in the parameters for the search.
	 * @param Model
	 * @return String
	 */
	@RequestMapping(value="setupSearchLocationRequest.request", method=RequestMethod.GET)
	protected String setupSearchLocationRequests(Model model) {
		LocationRequest searchLocationRequest;
		
		searchLocationRequest = new LocationRequest();
		
		model.addAttribute("searchLocationRequest", searchLocationRequest);
		
		return "searchLocationRequests";
	}
	
	@RequestMapping(value="processSearchLocationRequest.request", method=RequestMethod.POST)
	protected String searchLocationRequests(@ModelAttribute("searchLocationRequest")LocationRequest searchLocationRequest, Model model) {
		Map<Long, LocationRequest> locationRequests;

		locationRequests = scoutService.getLocationRequests(searchLocationRequest);
		
		if(locationRequests != null) {
			model.addAttribute("requestSearchResults", locationRequests);
		}
		
		return "searchLocationRequests";
	}
	
	@RequestMapping(value="gotoPhoto.request", method=RequestMethod.POST)
	protected String gotoPhoto(@ModelAttribute("location")Location location, Model model) {
		UploadForm uploadForm = new UploadForm();
		
		model.addAttribute("uploadForm", uploadForm);
		return "addPhoto";
	}
	
	/**
	 * Save location request submission and add to the Location
	 * Provider collection.
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
			providerService.modifyUser(locationProvider);
			
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
			scoutService.modifyUser(scout);
			
			StringBuilder emailBodyText = new StringBuilder();
			
			emailBodyText.append("<html><body>");
			emailBodyText.append("<h3>Location Submitted</h3>");
			emailBodyText.append("<p>A Location Provider has submitted their property for your approval. ");
			emailBodyText.append("Please login into your account to view pictures of this property.</p><br/>");
			emailBodyText.append("<img src=\"cid:coverImage\"/>");
			emailBodyText.append("</body></html>");
			
			// Find which of the location images is set to be the main photo.
			coverImage = location.getCoverImage();
			
			if(coverImage != null && scout != null) {
				emailService.sendEmailWithInlinePicture(scout.getEmailAddress(), coverImage.getAbsoluteFilePath(), "Location Submitted", emailBodyText.toString());
			}
			message = "Submission successful.";
			model.addAttribute("successfulSubmissionMessage", message);
		}
		else {
			message = "This location has already been submitted for this request.";
			model.addAttribute("errorSubmissionMessage", message);
		}
		
		return "searchLocationRequests";
	}

	/**
	 * Delete seleted Location objects
	 * 
	 * @param checkValues - String Array of check box values.
	 * @return
	 */
	@RequestMapping(value="deleteLocations.request", method=RequestMethod.POST)
	protected String deleteLocations(@ModelAttribute("locationProvider") LocationProvider locationProvider, @RequestParam("deleteCheck") String[] locationsToDelete) {
		providerService.deleteLocations(locationProvider, locationsToDelete);
		
		return "deleteLocations";
	}
	
	/**
	 * Setup the ProviderSubmissions in the collection by setting the LocationRequest object
	 * associated with the locationRequestId variable of the ProviderSubmission instance.
	 * 
	 * @param locationProvider - Object containing the collection of ProviderSubmissions
	 * @return - Logical name of the next view
	 */
	@RequestMapping(value="setupSubmissions.request", method=RequestMethod.GET)
	protected String setupSubmissions(@ModelAttribute("locationProvider") LocationProvider locationProvider) {
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
		
		return "viewSubmissionsList";
	}
	/*
	protected String navigateFromSubmissionListingPage() {
		
		return nextPage;
	}
	*/
}
