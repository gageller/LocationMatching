package com.locationmatching.domain;

import java.util.Set;

/**
 * Location scout submits request for the location that is being sought.
 * The location scout can have multiple requests. The location scout will
 * manage requests, deleting expired requests.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public class LocationScout extends User {
	/**
	 * Set of Request objects for this scout.
	 */
	Set<LocationRequest> locationRequests;

	public Set<LocationRequest> getLocationRequests() {
		return locationRequests;
	}

	public void setLocationRequests(Set<LocationRequest> locationRequests) {
		this.locationRequests = locationRequests;
	}
}
