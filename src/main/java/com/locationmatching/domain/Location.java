package com.locationmatching.domain;

import java.util.HashSet;
import java.util.Set;

import com.locationmatching.component.LocationPlanType;

/**
 * Location details including any pictures provided by the Location Provider.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class Location {
	/**
	 * Database identity of this location object
	 */
	private Long id;
	/**
	 * Parent of this location
	 */
	User locationOwner;
	/**
	 * Whether or not the location is currently available for use.
	 */
	private Boolean active;
	/**
	 * Address of the location
	 */
	private String locationAddress;
	/**
	 * Optional address field of the location.
	 * Holds data such as apartment number
	 */
	private String locationAddress2;
	/**
	 * City of the location
	 */
	private String locationCity;
	/**
	 * State code of the location
	 */
	private String locationStateCode;
	/**
	 * Zipcode of the location
	 */
	private String locationZipcode;
	/**
	 * Plan type of this location. 
	 * Each location can have it's own individual plan.
	 * If the provider has a premium subscription, all
	 * of the locations are premium plan types.
	 */
	private LocationPlanType locationPlanType;
	/**
	 * Number of images associated with this location.
	 * The number of images will determine whether the
	 * provider needs to pay for additional photos or
	 * they can subscribe to the premium plan and get unlimited
	 * images.
	 */
	private Integer numberOfImages;
	/**
	 * Collection of images associated with this location.
	 */
	Set<Image>locationImages;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public Boolean isActive() {
		return active;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	public String getLocationAddress2() {
		return locationAddress2;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public String getLocationStateCode() {
		return locationStateCode;
	}
	public String getLocationZipcode() {
		return locationZipcode;
	}
	public User getLocationOwner() {
		return locationOwner;
	}
	public LocationPlanType getLocationPlanType() {
		return locationPlanType;
	}
	public Integer getNumberOfImages() {
		return numberOfImages;
	}
	public Set<Image> getLocationImages() {
		return locationImages;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public void setLocationAddress2(String locationAddress2) {
		this.locationAddress2 = locationAddress2;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public void setLocationStateCode(String locationStateCode) {
		this.locationStateCode = locationStateCode;
	}
	public void setLocationZipcode(String locationZipcode) {
		this.locationZipcode = locationZipcode;
	}
	public void setLocationOwner(User locationOwner) {
		this.locationOwner = locationOwner;
	}
	public void setLocationPlanType(LocationPlanType locationPlanType) {
		this.locationPlanType = locationPlanType;
	}
	public void setNumberOfImages(Integer numberOfImages) {
		this.numberOfImages = numberOfImages;
	}
	public void setLocationImages(HashSet<Image> locationImages) {
		this.locationImages = locationImages;
	}	
}
