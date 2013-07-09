package com.locationmatching.domain;

import java.util.Date;

/**
 * This is the request object that the Location Scout fills out and is posted
 * for the Location Provider to view to see if their location matches the request.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class LocationRequest {
	/**
	 * Database identity
	 */
	private Long id;
	/**
	 * Location Type such as house, park, store...
	 */
	private String locationType;
	/**
	 * Submission Date
	 */
	private Date submissionDate;
	/**
	 * Beginning date of the shoot
	 */
	private Date shootBeginDate;
	/**
	 * End date of the shoot
	 */
	private Date shootEndDate;
	/**
	 * Project Notes
	 */
	private String projectNotes;
	/**
	 * Description of the location the scout is looking for.
	 */
	private String description;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getLocationType() {
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
	public String getDescription() {
		return description;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setLocationType(String locationType) {
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
	public void setDescription(String description) {
		this.description = description;
	}
}
