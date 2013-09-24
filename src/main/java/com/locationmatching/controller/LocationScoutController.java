package com.locationmatching.controller;

import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.locationmatching.component.Location;
import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.domain.User;
import com.locationmatching.enums.LocationType;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.service.LocationProviderServiceImpl;
import com.locationmatching.service.LocationScoutServiceImpl;
import com.locationmatching.service.LocationUserService;
import com.locationmatching.util.GlobalVars;

@Controller
@SessionAttributes({"locationScout", "locationRequest"})
public class LocationScoutController {
	@Autowired
	LocationScoutServiceImpl scoutService;
	
	public void setScoutService(LocationScoutServiceImpl scoutService) {
		this.scoutService = scoutService;
	}

	@Autowired
	LocationProviderServiceImpl providerService;
	
	public void setProviderService(LocationProviderServiceImpl providerService) {
		this.providerService = providerService;
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
	 * Instantiate the new LocationScout object to be passed into
	 * the newLocationScout page through the Model.
	 * 
	 * @param Model
	 * @return String
	 */
	@RequestMapping(value="createNewScout.request", method=RequestMethod.GET)
	public String setupNewScout(Model model) {
		LocationScout scout = new LocationScout();
		
		model.addAttribute("locationScout", scout);
		
		return "newLocationScout";
	}
	
	/**
	 * Create the new Location Scout.
	 * 
	 * @return String
	 */
	@RequestMapping(value="createNewScout.request", method=RequestMethod.POST)
	public String createNewScout(LocationScout locationScout, Model model) {
		Date date = new Date(System.currentTimeMillis());
		String nextPage;
		
		// Set the current time and last accessed time for this new provider.
		locationScout.setCreationDate(date);
		locationScout.setLastAccessDate(date);
		locationScout.setActive(true);
		// Set the user type
//		locationScout.setUserType(UserType.SCOUT);
		
		// Go to the provider.jsp page to add locations
		nextPage = "locationScoutHomePage";
	
		try {
			Long id;
			
			scoutService.createUser(locationScout);
			
		}
		catch(UserAlreadyExistsException ex) {
			System.out.println(ex.getMessage());
			// User name already in use. Send back to the register page.
			nextPage = "forward:./index.jsp";
			// Reset the user name and password to blank before sending
			// user back to register page
			locationScout.setUserName("");
			locationScout.setPassword("");
			model.addAttribute("userScoutLoginErrorMessage", "This User Name already exists. Please select another User Name.");
		}
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationScoutHomePage");
		
		return nextPage;	

	}
	
	/**
	 * Authenticate the provider logging in. If not found, send back to
	 * login page else send to their provider page.
	 * 
	 * @return String
	 */
	@RequestMapping(value="scout.request", method=RequestMethod.POST)
	public String authenticateUser(@RequestParam("scoutUserName")String userName,
			@RequestParam("scoutPassword")String password, Model model) {
		String view;
		LocationScout scout;
		
		scout = (LocationScout) scoutService.authenticateUser(userName, password);
		
		if(scout != null) {
			// Found the provider so proceed onto the provider
			// info page.
			model.addAttribute("locationScout", scout);
			// Set the name of the template to use for the next view
			model.addAttribute("templateName", "locationScoutHomePage");
			
			view = "locationScoutHomePage";
		}
		else {
			model.addAttribute("userScoutLoginErrorMessage", "We could not find this User Name. Please try again.");
			view = "forward:/index.jsp";
		}
		
		return view;
	}
	
	/**
	 * Setup template navigation attributes for going to next page
	 * 
	 * @param model - Set template attributes to go to next page
	 * @return
	 */
	@RequestMapping(value="setupEditLocationScout.request", method=RequestMethod.GET)
	protected String setupEditLocationScout(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editLocationScoutPage");
		
		return "locationScoutHomePage";	
	}
	
	/**
	 * Persist the changes for the LocationScout to the database
	 * 
	 * @param locationScout - LocationScout object with the updated changes
	 * @param model - Template name to navigate to the next page.
	 * @return
	 */
	@RequestMapping(value="editLocationScout.request", method=RequestMethod.POST)
	protected String editScoutInformation(@ModelAttribute("locationScout") LocationScout locationScout,
			Model model) {
		// Update the changes to the database
		scoutService.modifyUser(locationScout);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "locationScoutHomePage");
		
		return "locationScoutHomePage";	
	}
	
	///////////////////////////////////////////////////////////
	// LOCATION REQUEST PROCESSING
	///////////////////////////////////////////////////////////
	/**
	 * Instantiate the new LocationRequest object and add it
	 * to the model. Proceed onto the addLocationRequest.jsp
	 * page.
	 * 
	 * @param Model
	 * @return String
	 */
	@RequestMapping(value="addLocationRequest.request", method=RequestMethod.GET)
	protected String setupNewLocationRequest(Model model) {
		LocationRequest locationRequest;
		
		locationRequest = new LocationRequest();
		model.addAttribute("locationRequest", locationRequest);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "addLocationRequestPage");
		
		return "locationScoutHomePage";	
	}
	

	/**
	 * Add new LocationRequest to the database
	 * 
	 * @param locationRequest - New LocationRequest
	 * @param locationScout - Owner of the LocationRequest
	 * @param model - Add navigation template name.
	 * @return
	 */
	@RequestMapping(value="addLocationRequest.request", method=RequestMethod.POST)
	protected String addNewLocationRequest(@ModelAttribute("locationRequest") LocationRequest locationRequest,
			@ModelAttribute("locationScout") LocationScout locationScout, Model model) {
		
		// Setup the Scout, Request association.
		locationScout.addLocationRequest(locationRequest);
		
		// Save the new LocationRequest to the database.
		scoutService.addLocationRequest(locationRequest);
		
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "addLocationRequestPage");
		
		return "locationScoutHomePage";	
	}
	
	/**
	 * Set the navigation attributes for the next page template.
	 * 
	 * @param model - Add navigation template name for the next page
	 * @return
	 */
	@RequestMapping(value="setupEditLocationRequestListings.request")
	protected String setupEditLocationRequestListings(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editLocationRequestListingsPage");
		
		return "locationScoutHomePage";	
	}
	
	/**
	 * Set the navigation attributes for the next page template.
	 * 
	 * @param model - Navigation attributes for next page template
	 * @return
	 */
	@RequestMapping(value="	setupEditLocationRequest.request", method=RequestMethod.POST)
	protected String setupEditLocationRequest(@RequestParam("editId") long editId, 
			@ModelAttribute("locationScout")LocationScout locationScout, 
			Model model) {
		
		Set<LocationRequest> locationRequests;
		LocationRequest editLocationRequest = null;
		Iterator<LocationRequest>itr;
		
		locationRequests = locationScout.getLocationRequests();
		itr = locationRequests.iterator();


		while(itr.hasNext() == true) {
			Long id;
			
			editLocationRequest = itr.next();
			id = editLocationRequest.getId();
			
			if(id.equals(editId) == true) {
				break;
			}
		}
		model.addAttribute("locationRequest", editLocationRequest);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editLocationRequestPage");
		
		return "locationScoutHomePage";	
	}
	
	/**
	 * Update the modified LocationRequest object to the database.
	 * 
	 * @param locationRequest - The modified LocationRequest object.
	 * @param model - Attributes for navigation to next page template.
	 * @return
	 */
	@RequestMapping(value="editLocationRequest.request", method=RequestMethod.POST)
	protected String editLocationRequest(@ModelAttribute("locationRequest") LocationRequest locationRequest,
			Model model) {
		scoutService.modifyLocationRequest(locationRequest);
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "editLocationRequestListingsPage");
		
		return "locationScoutHomePage";	
	}
	
	/**
	 * Setup of the individual alerts for this scout by injecting the Location into it
	 * 
	 * @param locationScout 
	 * @return - Name of the view we are going to.
	 */
	@RequestMapping(value="setupScoutAlerts.request", method=RequestMethod.GET)
	protected String setupScoutAlerts(@ModelAttribute("locationScout")LocationScout locationScout) {
		Set<ScoutAlert>alerts;
		Iterator<ScoutAlert> iterator;
		
		alerts = locationScout.getRequestAlerts();
		iterator = alerts.iterator();
		
		while(iterator.hasNext() == true) {
			ScoutAlert alert;
			Long locationId;
			Location location;
			
			alert = iterator.next();
			
			locationId = alert.getLocationId();
			location = providerService.getLocation(locationId);
			alert.setLocation(location);
		}
		
		return "scoutAlerts";
	}
	
	/**
	 * Go to the viewScoutAlert page to display the photos for this particular Location
	 * 
	 * @param - LocationScout object associated with this location.
	 * @param - Form parameter requestId to pull the individual request alert out of the LocationScout
	 * @param - Model to add the selected Location object to get to its pictures.
	 */
	@RequestMapping(value="viewAlertLocationPhotos.request", method=RequestMethod.POST)
	protected String viewLocationPhotos(@ModelAttribute("locationScout") LocationScout locationScout,
			@RequestParam("requestedAlertId") long requestedAlertId, Model model) {
		ScoutAlert requestedAlert;
		Location alertLocation;
		
		requestedAlert = locationScout.getRequestAlert(requestedAlertId);
		// Set the viewed flag to true.
		requestedAlert.setViewed(true);
		requestedAlert.setViewedDate(new Date(System.currentTimeMillis()));
		
		// Update the LocationScout values to the database.
		scoutService.modifyUser(locationScout);
		
		alertLocation = requestedAlert.getLocation();
		
		model.addAttribute("alertLocation", alertLocation);
		
		return "viewLocationPhotos";
	}
	
	/**
	 * Return to the main page.
	 * 
	 * @param model - Put the template name that we are navigating back to display
	 * @return
	 */
	@RequestMapping(value="returnScoutMainPage.request", method=RequestMethod.GET)
	protected String returnToScoutMainPage(Model model)
	{
		model.addAttribute("templateName", "locationScoutHomePage");
		
		return "locationScoutHomePage";

	}

}
