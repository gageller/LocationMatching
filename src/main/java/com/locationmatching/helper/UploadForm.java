package com.locationmatching.helper;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Form that is made up of a MultipartFile to be
 * used for file uploads using the Apache Commons FileUpload
 * library
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public class UploadForm {
	/**
	 * Name of the file.
	 */
	private String fileName = null;
	
	/**
	 * Commons Multipart File that contains the file to
	 * be uploaded and its name
	 */
	private CommonsMultipartFile multipartFile;
	
	// Getter methods 
	public String getFileName() {
		return fileName;
	}
	public CommonsMultipartFile getMultipartFile() {
		return multipartFile;
	}
	
	// Setter methods
	public void setName(String fileName) {
		this.fileName = fileName;
	}

	public void setMultipartFile(CommonsMultipartFile multipartFile) {
		this.multipartFile = multipartFile;
		this.fileName = multipartFile.getOriginalFilename();
	}
	
}
