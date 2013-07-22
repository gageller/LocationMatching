package com.locationmatching.domain;

import java.util.Set;

/**
 * Location Provider searches through list of Location Scout requests to see
 * if they are interested in any of the postings. If so they contact the Location Scout.
 * Location Provider will have a collection of Location objects for each of their available
 * properties. Each of the Location objects will have details about the property and a
 * collection of pictures of the property if so chosen.
 * 
 * @author Greg Geller
 * @since 0.1.1
 * @version 0.1.1
 *
 */
public class LocationProvider extends User {
	/**
	 * Collection of Location objects associated with this provider.
	 */
	Set<Location>providerLocations;
	
	public Set<Location> getProviderLocations() {
		return providerLocations;
	}

	public void setProviderLocations(Set<Location> providerLocations) {
		this.providerLocations = providerLocations;
	}


}
