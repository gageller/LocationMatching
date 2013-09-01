package com.locationmatching.domain;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ScoutAlert;

/**
 * Location scout submits request for the location that is being sought.
 * The location scout can have multiple requests. The location scout will
 * manage requests, deleting expired requests.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
@Entity
@Table(name="LocationScout")
public class LocationScout extends User {
	/**
	 * Set of Request objects for this scout.
	 */
	@OneToMany(mappedBy="requestOwner") // mappedBy is equivalent to inverse=true in the mapping file.
	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.ALL, 
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Fetch(value=FetchMode.SELECT)
	private Set<LocationRequest> locationRequests = new LinkedHashSet<LocationRequest>();

	/**
	 * Set of alerts of Location Provider submissions.
	 */
	@OneToMany(mappedBy="alertOwner")
	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.ALL, 
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Fetch(value=FetchMode.SELECT)
	@OrderBy("creationDate")
	private Set<ScoutAlert> requestAlerts = new LinkedHashSet<ScoutAlert>();

	// Getter methods
	public Set<LocationRequest> getLocationRequests() {
		return locationRequests;
	}
	public Set<ScoutAlert> getRequestAlerts() {
		return requestAlerts;
	}
	
	/**
	 * Get the individual LocationRequest based on the id
	 * that is passed in. If not found, return null;
	 * 
	 * @param locationRequestId - id of the LocationRequest to search for.
	 * @return LocationRequest
	 */
	public LocationRequest getLocationRequest(Long locationRequestId) {
		LocationRequest locationRequest = null;
		Iterator<LocationRequest> iterator;
		
		iterator = locationRequests.iterator();
		while(iterator.hasNext() == true) {
			LocationRequest requestedLocationRequest;
			Long id;
			
			requestedLocationRequest = iterator.next();
			id = requestedLocationRequest.getId();
			
			if(locationRequestId.equals(id) == true) {
				// Found it.
				locationRequest = requestedLocationRequest;
				break;
			}
		}
		return locationRequest;
	}
	/**
	 * Get the individual ScoutAlert based on the id
	 * that is passed in. If not found, return null;
	 * 
	 * @param requestAlertId - Id of the request to search for.
	 * @return ScoutAlert
	 */
	public ScoutAlert getRequestAlert(Long requestAlertId) {
		ScoutAlert requestAlert = null;
		Iterator<ScoutAlert> iterator;
		
		iterator = requestAlerts.iterator();
		while(iterator.hasNext() == true) {
			ScoutAlert requestedAlert;
			Long id;
			
			requestedAlert = iterator.next();
			id = requestedAlert.getId();
			
			if(requestAlertId.equals(id) == true) {
				// Found it.
				requestAlert = requestedAlert;
				break;
			}
		}
		return requestAlert;
	}

	// Setter methods
	public void setLocationRequests(Set<LocationRequest> locationRequests) {
		this.locationRequests = locationRequests;
	}
	public void setRequestAlerts(Set<ScoutAlert> requestAlerts) {
		this.requestAlerts = requestAlerts;
	}
	
	/**
	 * Add the LocationRequest to the locationRequest collection and set the
	 * parent pointer of the LocationRequest.
	 * 
	 * @param locationRequest
	 */
	public void addLocationRequest(LocationRequest locationRequest) {
		locationRequests.add(locationRequest);
		locationRequest.setRequestOwner(this);
	}
	
	/**
	 * Remove the LocationRequest object from the collection
	 * @param locationRequest
	 */
	public void removeLocationRequest(LocationRequest locationRequest) {
		locationRequests.remove(locationRequest);
	}
	
	/**
	 * Add the Scout Alert to the collection and set the alert's
	 * parent pointer.
	 * 
	 * @param requestAlert
	 */
	public void addRequestAlert(ScoutAlert requestAlert) {
		requestAlerts.add(requestAlert);
		requestAlert.setAlertOwner(this);
	}
	
	/**
	 * Remove the passed in ScoutAlert from the collection.
	 * 
	 * @param requestAlert
	 */
	public void removeAlert(ScoutAlert requestAlert) {
		requestAlerts.remove(requestAlert);
	}
}
