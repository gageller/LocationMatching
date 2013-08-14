package com.locationmatching.controller;

import java.util.Date;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.locationmatching.component.UploadForm;
import com.locationmatching.domain.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.LocationRequest;
import com.locationmatching.domain.User;
import com.locationmatching.enums.LocationPlanType;
import com.locationmatching.enums.LocationType;
import com.locationmatching.enums.UserType;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.service.LocationProviderService;
import com.locationmatching.service.LocationProviderServiceImpl;
import com.locationmatching.util.GlobalVars;

/**
 * Controller to handle request made by the Location Provider
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Controller
@SessionAttributes({"locationProvider", "location", "editLocation", "searchLocationRequest", "uploadForm"})
public class LocationProviderController {
	@Autowired
	LocationProviderService service;
	
	public void setService(LocationProviderService service) {
		this.service = service;
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
		LocationProvider user = (LocationProvider) service.getUser(Long.valueOf(id));
		
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
		
		provider = (LocationProvider) service.authenticateUser(userName, password);
		
		if(provider != null) {
			// Found the provider so proceed onto the provider
			// info page.
			model.addAttribute("locationProvider", provider);
			view = "providerNavigation";
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
		
		providerList = service.getAllUsers();
		
		model.addAttribute("providerList", providerList);
		
		return "allProviders";
	}
	
	/**
	 * Delete the Location Provider based on the id passed in.
	 */
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public String deleteProvider(@PathVariable("id") String id) {
		service.deleteUser(Long.valueOf(id));
		
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
	public String createLocationProvider(LocationProvider locationProvider, HttpServletResponse response) {
		Date date = new Date(System.currentTimeMillis());
		String nextPage;
		
		// Set the current time and last accessed time for this new provider.
		locationProvider.setCurrentDate(date);
		locationProvider.setLastAccessDate(date);
		// Set the user type
		locationProvider.setUserType(UserType.PROVIDER);
		// Set the new user's plan type to the Pay Per Photo plan type
		locationProvider.setUserPlanType(LocationPlanType.PER_PHOTO);
		
		// Go to the provider.jsp page to add locations
		nextPage = "providerNavigation";
		
		try {
			Long id;
			
			service.createUser(locationProvider);
			
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
		LocationPlanType providerPlanType;
		
		// Set the number of images to 0 for new location
		location.setNumberOfImages(0);
		// Set this as active. May not need active flag anymore.
		location.setActive(true);
		// Get the user plan type from the parent LocationProvider.
		// If plan is Premium set the plan type for this location to
		// premium, otherwise set it to free.
		providerPlanType = locationProvider.getUserPlanType();
		if(providerPlanType == LocationPlanType.PREMIUM) {
			location.setLocationPlanType(LocationPlanType.PREMIUM);
		}
		else {
			location.setLocationPlanType(LocationPlanType.PER_PHOTO);
		}
		model.addAttribute("location", location);
		
		return "addLocation";
//		return "forward:/setupFileUpload";
	}
	
	/**
	 * Persist the new location to the database
	 */
	@RequestMapping(value="addLocation.request", method=RequestMethod.POST)
	protected String addNewLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, Location location) {
/*		LocationPlanType providerPlanType;
		
		// Set the number of images to 0 for new location
		location.setNumberOfImages(0);
		// Set this as active. May not need active flag anymore.
		location.setActive(true);
		// Get the user plan type from the parent LocationProvider.
		// If plan is Premium set the plan type for this location to
		// premium, otherwise set it to free.
		providerPlanType = locationProvider.getUserPlanType();
		if(providerPlanType == LocationPlanType.PREMIUM) {
			location.setLocationPlanType(LocationPlanType.PREMIUM);
		}
		else {
			location.setLocationPlanType(LocationPlanType.FREE);
		}
*/		
		try {
			locationProvider.addLocation(location);
			((LocationProviderServiceImpl)service).addLocation(locationProvider, location);
		}
		catch(LocationProcessingException ex) {
			
		}
		
		return "providerNavigation";
	}
	
	@RequestMapping(value="returnFromFileUpload.request", method=RequestMethod.GET)
	protected String returnFromFileUpload(@ModelAttribute("locationProvier")LocationProvider locationProvider,
			@ModelAttribute("location")Location location,
			Model model) {
//		model.addAttribute("uploadForm", new UploadForm());
		
		return "addLocation";
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
		model.addAttribute("editLocation", editLocation);
		
		return "editLocation";
	}
	
	/**
	 * Update the edited location to the database
	 */
	@RequestMapping(value="editLocation.request", method=RequestMethod.POST)
	protected String editLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, @ModelAttribute("editLocation") Location editLocation) {
		// Persist the modifications to the database.
		service.modifyUser(locationProvider);
		
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
		List<LocationRequest> locationRequests;
		
		locationRequests = service.getLocationRequests(searchLocationRequest);
		
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
}
