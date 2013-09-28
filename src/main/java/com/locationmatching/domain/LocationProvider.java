package com.locationmatching.domain;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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

import com.locationmatching.component.Location;
import com.locationmatching.component.ProviderSubmission;
import com.locationmatching.enums.UserPlanType;
import com.locationmatching.enums.UserType;

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
@DiscriminatorValue(value=UserType.Values.PROVIDER)
public class LocationProvider extends User {
	/**
	 * Collection of Location objects associated with this provider.
	 */
	@OneToMany(mappedBy="locationOwner") // mappedBy is equivalent to inverse=true in the mapping file.
//	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.ALL, 
	//		org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Fetch(value=FetchMode.SELECT)
	@OrderBy // Ordering by primary key is assumed when not given a value
	Set<Location> providerLocations = new LinkedHashSet<Location>();
	
	/**
	 * Collection of submissions for location requests
	 */
	@OneToMany(mappedBy="submissionOwner") // mapped by is equivalent to inverse=true in the mapping file.
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@LazyCollection(value=LazyCollectionOption.FALSE)
	@Fetch(value=FetchMode.SELECT)
	@OrderBy(value="submissionDate")
//	@MapKey(name="locationRequestId")
	Set<ProviderSubmission> requestSubmissions = new LinkedHashSet<ProviderSubmission>();
	
	@Enumerated(EnumType.STRING)
	@Column(name="USER_PLAN_TYPE")
	private UserPlanType userPlanType;
	
	// Getter Methods
	public Set<Location> getProviderLocations() {
		return providerLocations;
	}
	public UserPlanType getUserPlanType() {
		return userPlanType;
	}
	public Set<ProviderSubmission>getRequestSubmissions() {
		return requestSubmissions;
	}
	
	// Setter Methods
	public void setProviderLocations(Set<Location> providerLocations) {
		this.providerLocations = providerLocations;
	}
	public void setUserPlanType(UserPlanType userPlanType) {
		this.userPlanType = userPlanType;
	}
	public void setRequestSubmissions(Set<ProviderSubmission> requestSubmissions) {
		this.requestSubmissions = requestSubmissions;
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
	 * Add the ProviderSubmission object to the requestSubmission
	 * collection. Also set the parent pointer of the 
	 * ProviderSubmission object.
	 */
	public void addRequestSubmission(ProviderSubmission providerSubmission) {
		requestSubmissions.add(providerSubmission);
		providerSubmission.setSubmissionOwner(this);
	}
	
	/**
	 * Remove this location from the collection.
	 */
	public void removeLocation(Location location) {
		providerLocations.remove(location);
	}
	
	/**
	 * Remove the instance of the Location
	 * from the collection based on the id passed in.
	 * 
	 * @param id - Id of the ProviderSubmission instance to remove
	 * @return Location - Instance that is being removed from the collection.
	 * If not in the collection, return null.
	 */
	public Location removeLocation(Long id) {
		Location deleteLocation = null;
		Iterator<Location> iterator;
		
		iterator = providerLocations.iterator();
		while(iterator.hasNext() == true) {
			Long deleteId;
			
			deleteLocation = iterator.next();
			deleteId = deleteLocation.getId();
			
			if(deleteId.equals(id) == true) {
				// We found it so break out of loop
				// and remove ProviderSubmission from
				// the collection.
				break;
			}
		}
		if(deleteLocation != null) {
			providerLocations.remove(deleteLocation);
		}
		
		return deleteLocation;
	}
	
	/**
	 * Remove the passed in instance of ProviderSubmission from the collection.
	 * 
	 * @param providerSubmission - The ProviderSubmission instance to remove from the collection.
	 */
	public void removeRequestSubmission(ProviderSubmission providerSubmission) {
		requestSubmissions.remove(providerSubmission);
	}
	
	/**
	 * Remove the instance of the ProviderSubmission
	 * from the collection based on the id passed in.
	 * 
	 * @param id - Id of the ProviderSubmission instance to remove
	 * @return ProviderSubmission - Instance that is being removed from the collection.
	 * If not in the collection, return null.
	 */
	public ProviderSubmission removeRequestSubmissionById(Long id) {
		ProviderSubmission deleteSubmission = null;
		Iterator<ProviderSubmission> iterator;
		
		iterator = requestSubmissions.iterator();
		while(iterator.hasNext() == true) {
			Long deleteId;
			
			deleteSubmission = iterator.next();
			deleteId = deleteSubmission.getId();
			
			if(deleteId.equals(id) == true) {
				// We found it so break out of loop
				// and remove ProviderSubmission from
				// the collection.
				break;
			}
		}
		if(deleteSubmission != null) {
			requestSubmissions.remove(deleteSubmission);
		}
		
		return deleteSubmission;
	}
	
	/**
	 * 
	 * @param id - Id of the location object to get.
	 * @return Location object. If not found, return null.
	 */
	public Location getLocation(Long locationId) {
		Location requestedLocation =  null;
		Iterator<Location> iterator;
		
		// Iterate through the Location collection to 
		// find the location that has the same id as
		// the one we are looking for.
		iterator = providerLocations.iterator();
		
		while(iterator.hasNext() == true) {
			Location location;
			Long id;
			
			location = iterator.next();
			if(location != null) {
				id = location.getId();
				if(locationId.equals(id) == true) {
					// Found our location
					requestedLocation = location;
					break;
				}
			}
		}
		
		return requestedLocation;
	}
	
	/**
	 * Check to see if they prospective submission for this request has already been 
	 * submitted with this location.
	 * 
	 * @param locationId
	 * @param locationRequestId
	 * @return whether or not has already been submitted
	 */
	public boolean hasLocationBeenSubmittedWithThisRequest(Long locationId, Long locationRequestId) {
		boolean hasBeenSubmitted = false;
		Iterator<ProviderSubmission> iterator;
		
		iterator = requestSubmissions.iterator();
		while(iterator.hasNext() == true) {
			ProviderSubmission submission;
			Long submissionLocationRequestId;
			
			submission = iterator.next();
			// Check to see if the location request is the same.
			submissionLocationRequestId = submission.getLocationRequestId();
			if(submissionLocationRequestId.equals(locationRequestId) == true) {
				// Found the request id. Now look to see if the location ids match
				Long submissionLocationId;
				
				submissionLocationId = submission.getLocationId();
				if(submissionLocationId.equals(locationId) == true) {
					// Found it. This has already been submitted.
					hasBeenSubmitted = true;
					break;
				}
			}
		}
		
		return hasBeenSubmitted;
	}
}
