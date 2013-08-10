package com.locationmatching.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.locationmatching.enums.LocationPlanType;

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
@Entity
@Table(name="LocationProvider")
public class LocationProvider extends User {
	/**
	 * Collection of Location objects associated with this provider.
	 */
	@OneToMany(mappedBy="locationOwner") // mappedBy is equivalent to inverse=true in the mapping file.
	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.ALL, 
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Fetch(value=FetchMode.SELECT)
	@OrderBy
	Set<Location> providerLocations = new LinkedHashSet<Location>();
	
	@Enumerated(EnumType.STRING)
	@Column(name="USER_PLAN_TYPE")
	private LocationPlanType userPlanType;
	
	// Getter Methods
	public Set<Location> getProviderLocations() {
		return providerLocations;
	}
	public LocationPlanType getUserPlanType() {
		return userPlanType;
	}
	
	// Setter Methods
	public void setProviderLocations(Set<Location> providerLocations) {
		this.providerLocations = providerLocations;
	}
	public void setUserPlanType(LocationPlanType userPlanType) {
		this.userPlanType = userPlanType;
	}
	
	/**
	 * Add the location object to the collection. Also
	 * set the parent pointer of the location.
	 * 
	 * @param location
	 */
	public void addLocation(Location location) {
		location.setLocationOwner(this);
		
		providerLocations.add(location);
	}
	
	/**
	 * Remove this location from the collection.
	 */
	public void removeLocation(Location location) {
		providerLocations.remove(location);
	}
}
