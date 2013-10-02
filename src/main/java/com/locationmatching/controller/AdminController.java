package com.locationmatching.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;

import com.locationmatching.domain.AdminUser;
import com.locationmatching.service.AdminServiceImpl;

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
		
		return "/admin/adminHomePage";	
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
		
		return "/admin/adminHomePage";

	}

	/**
	 * Setup the navigation template attributes
	 * 
	 * @param model - Store the navigation template attributes.
	 * @return - Next Page
	 */
	@RequestMapping(value="setupApproveDeclinePhotos.request", method=RequestMethod.GET)
	protected String setupApproveDeclinePhotos(Model model) {
		// Set the name of the template to use for the next view
		model.addAttribute("templateName", "adminApproveDeclinePhotosPage");
		
		return "/admin/adminHomePage";	
	}
}
