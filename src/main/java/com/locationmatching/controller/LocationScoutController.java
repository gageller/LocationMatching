package com.locationmatching.controller;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.locationmatching.component.LocationType;
import com.locationmatching.component.UserType;
import com.locationmatching.domain.LocationRequest;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.service.LocationScoutService;
import com.locationmatching.util.GlobalVars;

@Controller
@SessionAttributes({"locationScout", "locationRequest"})
public class LocationScoutController {
	@Autowired
	LocationScoutService service;
	
	public void setService(LocationScoutService service) {
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
		EnumMap<LocationType, String> locationTypes = new EnumMap<LocationType, String>(LocationType.class);
		
		locationTypes.put(LocationType.APARTMENT, "Apartment");
		locationTypes.put(LocationType.CONDOMINIUM, "Condominium");
		locationTypes.put(LocationType.HOUSE, "House");
		locationTypes.put(LocationType.OFFICE, "Office Building");
		locationTypes.put(LocationType.PARK, "Park");
		locationTypes.put(LocationType.TOWNHOUSE, "Townhouse");
		
		return locationTypes;
		
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
	public String createNewScout(LocationScout locationScout) {
		Date date = new Date(System.currentTimeMillis());
		String nextPage;
		
		// Set the current time and last accessed time for this new provider.
		locationScout.setCurrentDate(date);
		locationScout.setLastAccessDate(date);
		// Set the user type
		locationScout.setUserType(UserType.SCOUT);
		
		// Go to the provider.jsp page to add locations
		nextPage = "scout";
		
		try {
			Long id;
			
			service.createUser(locationScout);
			
		}
		catch(UserAlreadyExistsException ex) {
			System.out.println(ex.getMessage());
			// User name already in use. Send back to the register page.
			nextPage = "forward:./index.jsp";
			// Reset the user name and password to blank before sending
			// user back to register page
			locationScout.setUserName("");
			locationScout.setPassword("");
		}
		
		return nextPage;
	}
	
	/**
	 * Authenticate the provider logging in. If not found, send back to
	 * login page else send to their provider page.
	 * 
	 * @return String
	 */
	@RequestMapping(value="/scout.request", method=RequestMethod.POST)
	public String authenticateUser(@RequestParam("scoutUserName")String userName,
			@RequestParam("scoutPassword")String password, Model model) {
		String view;
		LocationScout scout;
		
		scout = (LocationScout) service.authenticateUser(userName, password);
		
		if(scout != null) {
			// Found the provider so proceed onto the provider
			// info page.
			model.addAttribute("locationScout", scout);
			view = "scout";
		}
		else {
			// Not found. Need to add code to handle error message
			view = "redirect:/index.jsp";
		}
		
		return view;
	}
	
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
		
		return "addLocationRequest";
	}
	
	/**
	 * Persist the new LocationRequest object to the database.
	 * 
	 * @return String
	 */
	@RequestMapping(value="addLocationRequest.request", method=RequestMethod.POST)
	protected String addNewLocationRequest(@ModelAttribute("locationRequest") LocationRequest locationRequest,
			@ModelAttribute("locationScout") LocationScout locationScout) {
		
		// Setup the Scout, Request association.
		locationScout.addLocationRequest(locationRequest);
		
		service.addLocationRequest(locationScout, locationRequest);
		
		return "scout";
	}
}
