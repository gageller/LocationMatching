package com.locationmatching.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.locationmatching.component.LocationPlanType;
import com.locationmatching.component.UserType;
import com.locationmatching.domain.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.service.LocationProviderService;

/**
 * Controller to handle request made by the Location Provider
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Controller
@SessionAttributes({"locationProvider", "location"})
public class LocationProviderController {
	@Autowired
	LocationProviderService service;
	
	public void setService(LocationProviderService service) {
		this.service = service;
	}
	
	@ModelAttribute("stateCodeList")
	protected Map<String, String> stateCodeList() {
		Map<String, String>stateCodeList = new LinkedHashMap<String, String>();
		
		stateCodeList.put("", "");
		stateCodeList.put("AL", "Alabama");
		stateCodeList.put("AK", "Alaska");
		stateCodeList.put("AZ", "Arizona");
		stateCodeList.put("AR", "Arkansas");
		stateCodeList.put("CA", "California");
		stateCodeList.put("CO", "Colorado");
		stateCodeList.put("DE", "Delaware");
		stateCodeList.put("DC", "District of Columbia");
		stateCodeList.put("FL", "Florida");
		stateCodeList.put("GA", "Georgia");
		stateCodeList.put("HI", "Hawaii");
		stateCodeList.put("ID", "Idaho");
		stateCodeList.put("IL", "Illinois");
		stateCodeList.put("IN", "Indiana");
		stateCodeList.put("IA", "Iowa");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("", "");
		stateCodeList.put("NH", "New Hampshire");
		stateCodeList.put("NY", "New Yori");
		stateCodeList.put("OH", "Ohio");
		stateCodeList.put("OK", "Oklahona");
		stateCodeList.put("PA", "Pennsylvania");
		stateCodeList.put("RI", "Rhode Island");
		stateCodeList.put("SC", "South Carolina");
		stateCodeList.put("SD", "South Dakota");
		stateCodeList.put("TN", "Tennessee");
		stateCodeList.put("TX", "Texas");
		stateCodeList.put("UT", "Utah");
		stateCodeList.put("VT", "Vermont");
		stateCodeList.put("VA", "Virginia");
		stateCodeList.put("WA", "Washington");
		stateCodeList.put("WV", "West Virginia");
		stateCodeList.put("WI", "Wisconsin");
		stateCodeList.put("WY", "Wyoming");

		return stateCodeList;
	}
	/**
	 * Get the Location Provider based on the id passed in.
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
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
	@RequestMapping(value="/provider.request", method=RequestMethod.POST)
	public String authenticateUser(@RequestParam("providerUserName")String userName,
			@RequestParam("providerPassword")String password, Model model) {
		String view;
		LocationProvider provider;
		
		provider = service.authenticateUser(userName, password);
		
		if(provider != null) {
			// Found the provider so proceed onto the provider
			// info page.
			model.addAttribute("locationProvider", provider);
			view = "provider";
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
	@RequestMapping(method=RequestMethod.GET)
	public String getAllProviders(Model model) {
		List<LocationProvider> providerList;
		
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
		
		model.addAttribute("provider", provider);
		
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
		// Set the new user's plan type to the free plan type
		locationProvider.setUserPlanType(LocationPlanType.FREE);
		
		// Go to the provider.jsp page to add locations
		nextPage = "provider";
		
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
		
		model.addAttribute("location", location);
		
		return "addLocation";
	}
	
	/**
	 * Persist the new location to the database
	 */
	@RequestMapping(value="addLocation.request", method=RequestMethod.POST)
	protected String addNewLocation(@ModelAttribute("locationProvider")LocationProvider locationProvider, Location location) {
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
			location.setLocationPlanType(LocationPlanType.FREE);
		}
		
		try {
			locationProvider.addLocation(location);
			service.addLocation(locationProvider, location);
		}
		catch(LocationProcessingException ex) {
			
		}
		
		return "provider";
	}
}
