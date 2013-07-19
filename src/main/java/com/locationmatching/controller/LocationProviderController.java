package com.locationmatching.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.User;
import com.locationmatching.service.LocationProviderService;

/**
 * Restful Controller to handle request made by the Location Provider
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Controller
public class LocationProviderController {
	@Autowired
	LocationProviderService service;
	
	public void setService(LocationProviderService service) {
		this.service = service;
	}
	
	/**
	 * Get the Location Provider based on the id passed in.
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public ModelAndView getProvider(@PathVariable("id") String id) {
		ModelAndView modelView = new ModelAndView();
		LocationProvider user = (LocationProvider) service.getUser(Long.valueOf(id));
		
		modelView.addObject("locationProvider", user);
		modelView.setViewName("provider");
		
		return modelView;
	}
	
	/**
	 * Get all of the Location Providers in the system
	 */
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
	 * We got here from the request to create a new Provider account
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value="/createNewProvider.request", method=RequestMethod.POST)
	public ModelAndView createLocationProvider() {
		ModelAndView modelView;
		//LocationProvider provider = new LocationProvider();
		modelView = new ModelAndView("provider");
		
		return modelView;
	}
}
