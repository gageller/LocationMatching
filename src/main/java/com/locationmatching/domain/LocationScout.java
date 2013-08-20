package com.locationmatching.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.locationmatching.component.LocationRequest;

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

	public Set<LocationRequest> getLocationRequests() {
		return locationRequests;
	}

	public void setLocationRequests(Set<LocationRequest> locationRequests) {
		this.locationRequests = locationRequests;
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
}
