package com.locationmatching.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.locationmatching.domain.LocationScout;
import com.locationmatching.enums.LocationType;

/**
 * This is the request object that the Location Scout fills out and is posted
 * for the Location Provider to view to see if their location matches the request.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Entity
public class LocationRequest {
	/**
	 * Database identity
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	
	/**
	 * Pointer back to the parent scout
	 */
	@ManyToOne()
	@JoinColumn(name="USER_ID", nullable=false, insertable=true, updatable=false)
	private LocationScout requestOwner;
	
	@Column(name="LOCATION_REQUEST_NAME")
	private String locationRequestName;
	
	/**
	 * Location Type such as house, park, store...
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="LOCATION_TYPE")
	private LocationType locationType;
	
	/**
	 * Submission Date
	 */
	@DateTimeFormat(style="S-")
	@Column(name="SUBMISSION_DATE")
	private Date submissionDate = new Date(System.currentTimeMillis());
	
	/**
	 * Beginning date of the shoot
	 */
	@DateTimeFormat(style="S-")
	@Column(name="SHOOT_BEGIN_DATE")
	private Date shootBeginDate = new Date(System.currentTimeMillis());
	
	/**
	 * End date of the shoot
	 */
	@DateTimeFormat(style="S-")
	@Column(name="SHOOT_END_DATE")
	private Date shootEndDate = new Date(System.currentTimeMillis());
	
	/**
	 * Project Notes
	 */
	@Column(name="PROJECT_NOTES")
	private String projectNotes;
	
	/**
	 * Description of the location the scout is looking for.
	 */
	@Column(name="LOCATION_DESCRIPTION")
	private String locationDescription;
	
	/**
	 * Location Request City
	 */
	@Column(name="LOCATION_REQUEST_CITY")
	private String locationRequestCity;
	
	/**
	 * Location Request State
	 */
	@Column(name="LOCATION_REQUEST_STATE")
	private String locationRequestState;
	
	/**
	 * Location Request Zip Code
	 */
	@Column(name="LOCATION_REQUEST_ZIPCODE")
	private String locationRequestZipcode;
	
	/**
	 * Location Request County
	 */
	@Column(name="LOCATION_REQUEST_COUNTY")
	private String locationRequestCounty;
	/**
	 * Rate that the production is looking to pay for the use
	 * of the location.
	 */
	@Column(name="RATE")
	private Double rate;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getLocationRequestName() {
		return locationRequestName;
	}
	public LocationType getLocationType() {
		return locationType;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public Date getShootBeginDate() {
		return shootBeginDate;
	}
	public Date getShootEndDate() {
		return shootEndDate;
	}
	public String getProjectNotes() {
		return projectNotes;
	}
	public String getLocationDescription() {
		return locationDescription;
	}
	public Double getRate() {
		return rate;
	}
	public LocationScout getRequestOwner() {
		return requestOwner;
	}
	public String getLocationRequestCity() {
		return locationRequestCity;
	}
	public String getLocationRequestState() {
		return locationRequestState;
	}
	public String getLocationRequestZipcode() {
		return locationRequestZipcode;
	}
	public String getLocationRequestCounty() {
		return locationRequestCounty;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setLocationRequestName(String locationRequestName) {
		this.locationRequestName = locationRequestName;
	}
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public void setShootBeginDate(Date shootBeginDate) {
		this.shootBeginDate = shootBeginDate;
	}
	public void setShootEndDate(Date shootEndDate) {
		this.shootEndDate = shootEndDate;
	}
	public void setProjectNotes(String projectNotes) {
		this.projectNotes = projectNotes;
	}
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public void setRequestOwner(LocationScout requestOwner) {
		this.requestOwner = requestOwner;
	}
	public void setLocationRequestCity(String locationRequestCity) {
		this.locationRequestCity = locationRequestCity;
	}
	public void setLocationRequestState(String locationRequestState) {
		this.locationRequestState = locationRequestState;
	}
	public void setLocationRequestZipcode(String locationRequestZipcode) {
		this.locationRequestZipcode = locationRequestZipcode;
	}
	public void setLocationRequestCounty(String locationRequestCounty) {
		this.locationRequestCounty = locationRequestCounty;
	}
}
