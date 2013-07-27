package com.locationmatching.domain;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.locationmatching.component.LocationPlanType;

/**
 * Location details including any pictures provided by the Location Provider.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Entity
@Table(name="Location")
public class Location {
	/**
	 * Database identity of this location object
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	/**
	 * Parent of this location
	 */
	@ManyToOne()
	@JoinColumn(name="LOCATIONPROVIDER_ID", nullable=false, insertable=true, updatable=false)
	User locationOwner;
	
	/**
	 * Whether or not the location is currently available for use.
	 */
	@Column(name="ACTIVE")
	private Boolean active;
	
	/**
	 * Name given to the location by the user.
	 */
	@Column(name="LOCATION_NAME")
	private String locationName;
	
	/**
	 * Address of the location
	 */
	@Column(name="LOCATION_ADDRESS")
	private String locationAddress;
	
	/**
	 * Optional address field of the location.
	 * Holds data such as apartment number
	 */
	@Column(name="LOCATION_ADDRESS2")
	private String locationAddress2;
	
	/**
	 * City of the location
	 */
	@Column(name="LOCATION_CITY")
	private String locationCity;
	
	/**
	 * State code of the location
	 */
	@Column(name="LOCATION_STATE_CODE")
	private String locationStateCode;
	
	/**
	 * Zipcode of the location
	 */
	@Column(name="LOCATION_ZIPCODE")
	private String locationZipcode;
	
	/**
	 * Plan type of this location. 
	 * Each location can have it's own individual plan.
	 * If the provider has a premium subscription, all
	 * of the locations are premium plan types.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="LOCATION_PLAN_TYPE")
	private LocationPlanType locationPlanType;
	
	/**
	 * Number of images associated with this location.
	 * The number of images will determine whether the
	 * provider needs to pay for additional photos or
	 * they can subscribe to the premium plan and get unlimited
	 * images.
	 */
	@Column(name="NUMBER_OF_IMAGES")
	private Integer numberOfImages;
	/**
	 * Collection of images associated with this location.
	 */
	@OneToMany(mappedBy="parentLocation") // mappedBy is equivalent to inverse=true
	@Fetch(value = FetchMode.JOIN)
	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.DELETE_ORPHAN, 
			org.hibernate.annotations.CascadeType.ALL})
	Set<Image>locationImages = new LinkedHashSet();
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public Boolean isActive() {
		return active;
	}
	public String getLocationName() {
		return locationName;
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
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
