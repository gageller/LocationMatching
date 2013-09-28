package com.locationmatching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.locationmatching.domain.User;
import com.locationmatching.enums.UserType;
import com.locationmatching.service.GeneralUserServiceImpl;

/**
 * Controller that handles the Login and creating accounts functionality
 * 
 * @author Greg Geller
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@SessionAttributes({"locationProvider", "locationScout"})
public class LoginCreateAccountController {
	@Autowired
	GeneralUserServiceImpl service;
	
	/**
	 * Inject dao service.
	 * 
	 * @param service - Service for persisting data
	 */
	public void setService(GeneralUserServiceImpl service) {
		this.service = service;
	}
	
	/**
	 * Authenticate the user. Redirect to the correct home page based on
	 * the UserType. If user does not exist, go back to the login page and
	 * display an error message.
	 * 
	 * @param userName - Entered user name
	 * @param password - Entered password
	 * @param model - Add navigation template attributes.
	 * 
	 * @return - Next page logical name.
	 */
	@RequestMapping(value="login.request", method=RequestMethod.POST)
	protected String login(@RequestParam("userName")String userName,
			@RequestParam("password") String password
			, Model model) {
		String nextPage = "index";
		User user;
		
		user = service.authenticateUser(userName, password);
		
		if(user == null) {
			// User does not exist so return to the login page and display an error message.
			model.addAttribute("loginErrorMessage", "The user does not exist. Try logging in again or create a new account.");
			nextPage = "index";
		}
		else {
			UserType userType;
			
			userType = user.getUserType();
			
			if(userType == UserType.PROVIDER) {
				nextPage = "forward:/setupLocationProviderSession.request";
				model.addAttribute("locationProvider", user);
			}
			
			if(userType == UserType.SCOUT) {
				nextPage = "forward:/setupLocationScoutSession.request";
				model.addAttribute("locationScout", user);
			}
			
			if(userType == UserType.ADMIN) {
				nextPage = "locationScoutHomePage";
			}
		}
		
		return nextPage;	
	}
}
