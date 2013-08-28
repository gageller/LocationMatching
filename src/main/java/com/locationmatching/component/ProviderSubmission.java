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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.SubmissionResponse;

/**
 * Class that keeps track of the submissions a Location
 * Provider makes to Location Scouts's Location Requests.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
@Entity
@Table(name="ProviderSubmissions")
public class ProviderSubmission {
	/**
	 * Database identifier
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;

	/**
	 * Date the Location Provider sent the submission
	 */
	@Column(name="SUBMISSION_DATE")
	private Date submissionDate;
	
	/**
	 * Date the Location Provider received a response from
	 * the Location Scout
	 */
	@Column(name="SUBMISSION_RESPONSE_DATE")
	private Date submissionResponseDate;
	
	/**
	 * Current response status.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="SUBMISSION_RESPONSE")
	private SubmissionResponse submissionResponse;
	
	/**
	 * Id of the Location Request
	 */
	@Column(name="LOCATION_REQUEST_ID")
	private Long locationRequestId;
	
	/**
	 * Id of the location this submission is for.
	 */
	@Column(name="LOCATION_ID")
	private Long locationId;
	
	/**
	 * Pointer back to the Location Provider parent
	 */
	@ManyToOne()
	@JoinColumn(name="LOCATIONPROVIDER_ID", nullable=false, insertable=true, updatable=false)
	private LocationProvider submissionOwner;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public SubmissionResponse getSubmissionResponse() {
		return submissionResponse;
	}
	public Date getSubmissionResponseDate() {
		return submissionResponseDate;
	}
	public Long getLocationRequestId() {
		return locationRequestId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public LocationProvider getSubmissionOwner() {
		return submissionOwner;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public void setSubmissionResponse(SubmissionResponse submissionResponse) {
		this.submissionResponse = submissionResponse;
	}
	public void setSubmissionResponseDate(Date submissionResponseDate) {
		this.submissionResponseDate = submissionResponseDate;
	}
	public void setLocationRequestId(Long locationRequestId) {
		this.locationRequestId = locationRequestId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public void setSubmissionOwner(LocationProvider submissionOwner) {
		this.submissionOwner = submissionOwner;
	}
}

