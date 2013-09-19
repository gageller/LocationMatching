package com.locationmatching.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Entity()
@Table(name="ScoutAlert")
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
	@Column(name="VIEWED")
	private Boolean viewed;

	@ManyToOne()
	@JoinColumn(name="USER_ID", nullable=false, insertable=true, updatable=false)
	/**
	 * Location Scout owner of the alert
	 */
	private LocationScout alertOwner;
	
	/**
	 * Date the alert was created
	 */
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	/**
	 * Date the alert was viewed.
	 */
	@Column(name="VIEWED_DATE")
	private Date viewedDate;
	
	/**
	 * Whether or not to show the alert. Not sure if when the user deletes
	 * an alert whether we purge it from the database or hide it for some kind
	 * of audit purpose.
	 */
	@Column(name="SHOW_ALERT")
	private Boolean showAlert;
	
	/**
	 * The location object will be set when setting up for the Scout Alerts jsp page.
	 * There is no reason to have to persist the Location data to the database since we
	 * do have the location id.
	 */
	@Transient
	private Location location;
	
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
	public Date getCreationDate() {
		return creationDate;
	}
	public Date getViewedDate() {
		return viewedDate;
	}
	public Boolean getShowAlert() {
		return showAlert;
	}
	public Location getLocation() {
		return location;
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
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setViewedDate(Date viewedDate) {
		this.viewedDate = viewedDate;
	}
	public void setShowAlert(Boolean showAlert) {
		this.showAlert = showAlert;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Pass back the viewed flag
	 * 
	 * @return viewed flag
	 */
	public Boolean hasBeenViewed() {
		return viewed;
	}
	
	/**
	 * Pass back the showAlert flag.
	 * 
	 * @return showAlert flag
	 */
	public Boolean shouldShowAlert() {
		return showAlert;
	}
}
