package com.locationmatching.domain;

import com.locationmatching.component.ImageType;

/**
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public class Image {
	/**
	 * Database identifier
	 */
	private Long id;
	/**
	 * Name of the image
	 */
	private String fileName;
	/**
	 * Type of the image. 
	 * Supported types: JPEG, GIF, PNG
	 */
	private ImageType imageType;
	/**
	 * Parent location for this image.
	 */
	private Location parentLocation;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public Location getParentLocation() {
		return parentLocation;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}
}
