package com.locationmatching.controller;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

/**
 * Controller for uploading of pictures to be stored on the server.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */

@Controller
public class FileUploadController implements ServletContextAware{
	private ServletContext context;
	
	
	@RequestMapping(value="setupFileUpload", method=RequestMethod.GET)
	protected String uploadImage(Model model) {
				
		return "";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
