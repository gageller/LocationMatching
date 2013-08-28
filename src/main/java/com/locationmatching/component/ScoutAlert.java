package com.locationmatching.component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.locationmatching.domain.LocationScout;

/**
 * After the provider submits his/her property for review by the Locaiton
 * Scout, the scout receives and email to logon and check his alerts to view
 * details and pictures of the submitted property.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public class ScoutAlert {
	/**
	 * Database identity
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	
	/**
	 * Id of the Location Provider who submitted the property
	 */
	@Column(name="LOCATION_PROVIDER_ID")
	private Long locationProviderId;
	
	/**
	 * Id of the Location
	 */
	@Column(name="LOCATION_ID")
	private Long locationId;
	
	/**
	 * Id of the Location Request that corresponds to this
	 * submission by the Location Provider.
	 */
	@Column(name="LOCATION_REQUEST_ID")
	private Long locationRequestId;
	
	/**
	 * Flag to test to see if the Location Scout has viewed this alert
	 */
	private Boolean viewed;

	/**
	 * Location Scout owner of the alert
	 */
	private LocationScout alertOwner;
	
	// Getter methods
	public Long getId() {
		return id;
	}
	public Long getLocationProviderId() {
		return locationProviderId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public Long getLocationRequestId() {
		return locationRequestId;
	}
	public Boolean getViewed() {
		return viewed;
	}
	public LocationScout getAlertOwner() {
		return alertOwner;
	}
	
	// Setter methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setLocationProviderId(Long locationProviderId) {
		this.locationProviderId = locationProviderId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public void setLocationRequestId(Long locationRequestId) {
		this.locationRequestId = locationRequestId;
	}
	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}
	public void setAlertOwner(LocationScout alertOwner) {
		this.alertOwner = alertOwner;
	}
	
	/**
	 * Pass back the viewed flag
	 */
	public Boolean hasBeenViewed() {
		return viewed;
	}
}
