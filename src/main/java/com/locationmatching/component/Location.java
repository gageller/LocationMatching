package com.locationmatching.component;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.locationmatching.domain.LocationProvider;
import com.locationmatching.enums.PhotoPlanType;

/**
 * Location details including any pictures provided by the Location Provider.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
@Entity
@Table(name="Location")
public class Location {
	/**
	 * Database identity of this location object
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	/**
	 * Parent of this location
	 */
	@ManyToOne()
	@JoinColumn(name="LOCATIONPROVIDER_ID", nullable=false, insertable=true, updatable=false)
	LocationProvider locationOwner;
	
	/**
	 * Whether or not the location is currently available for use.
	 */
	@Column(name="ACTIVE")
	private Boolean active;
	
	/**
	 * Name given to the location by the user.
	 */
	@Column(name="LOCATION_NAME")
	private String locationName;
	
	/**
	 * Address of the location
	 */
	@Column(name="LOCATION_ADDRESS")
	private String locationAddress;
	
	/**
	 * Optional address field of the location.
	 * Holds data such as apartment number
	 */
	@Column(name="LOCATION_ADDRESS2")
	private String locationAddress2;
	
	/**
	 * City of the location
	 */
	@Column(name="LOCATION_CITY")
	private String locationCity;
	
	/**
	 * State code of the location
	 */
	@Column(name="LOCATION_STATE")
	private String locationState;
	
	/**
	 * Zipcode of the location
	 */
	@Column(name="LOCATION_ZIPCODE")
	private String locationZipcode;

	/**
	 * Plan type of this location. 
	 * Each location can have it's own individual plan.
	 * If the provider has a premium subscription, all
	 * of the locations are premium plan types.
	 */
//	@Enumerated(EnumType.STRING)
//	@Column(name="LOCATION_PLAN_TYPE")
//	private UserPlanType locationPlanType;
	
	/**
	 * Number of free photos associated with this location.
	 * The number of free photos will determine whether the
	 * provider needs to pay for additional photos or
	 */
	@Column(name="NUMBER_OF_FREE_PHOTOS")
	private Integer numberOfFreePhotos = 0;
	
	/**
	 * Number of paid for photos
	 */
	@Column(name="NUMBER_OF_PAID_PHOTOS")
	private Integer numberOfPaidPhotos;
	
	/**
	 * Number of free videos. Not currently being used.
	 */
	@Column(name="NUMBER_OF_FREE_VIDEOS")
	private Integer numberOfFreeVideos;
	
	/**
	 * Number of paid for videos. Not currently being used.
	 */
	@Column(name="NUMBER_OF_PAID_VIDEOS")
	private Integer numberOfPaidVideos;
	
	/**
	 * Collection of images associated with this location.
	 */
	@OneToMany(mappedBy="parentLocation") // mappedBy is equivalent to inverse=true
	@Fetch(value = FetchMode.JOIN)
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Where(clause="hidden=0 and approved=1")
//	@org.hibernate.annotations.Cascade(value={org.hibernate.annotations.CascadeType.DELETE_ORPHAN, 
//			org.hibernate.annotations.CascadeType.ALL})
	Set<Image>locationImages = new LinkedHashSet();
	
	/**
	 * Url to the main/cover photo
	 */
	@Column(name="COVER_PHOTO_URL")
	private String coverPhotoUrl;
	
	/**
	 * Date Created
	 */
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	/**
	 * Date Modified
	 */
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public Boolean getActive() {
		return active;
	}
	public String getLocationName() {
		return locationName;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	public String getLocationAddress2() {
		return locationAddress2;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public String getLocationState() {
		return locationState;
	}
	public String getLocationZipcode() {
		return locationZipcode;
	}
	public LocationProvider getLocationOwner() {
		return locationOwner;
	}
	/*
	public UserPlanType getLocationPlanType() {
		return locationPlanType;
	}
*/
	public Integer getNumberOfFreePhotos() {
		return numberOfFreePhotos;
	}
	public Integer getNumberOfPaidPhotos() {
		return numberOfPaidPhotos;
	}
	public Integer getNumberOfFreeVideos() {
		return numberOfFreeVideos;
	}
	public Integer getNumberOfPaidVideos() {
		return numberOfPaidVideos;
	}
	public Set<Image> getLocationImages() {
		return locationImages;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public String getCoverPhotoUrl() {
		return coverPhotoUrl;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public void setLocationAddress2(String locationAddress2) {
		this.locationAddress2 = locationAddress2;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}
	public void setLocationZipcode(String locationZipcode) {
		this.locationZipcode = locationZipcode;
	}
	public void setLocationOwner(LocationProvider locationOwner) {
		this.locationOwner = locationOwner;
	}
/*
	public void setLocationPlanType(UserPlanType locationPlanType) {
		this.locationPlanType = locationPlanType;
	}
*/
	public void setNumberOfFreePhotos(Integer numberOfFreePhotos) {
		this.numberOfFreePhotos = numberOfFreePhotos;
	}
	public void setNumberOfPaidPhotos(Integer numberOfPaidPhotos) {
		this.numberOfPaidPhotos = numberOfPaidPhotos;
	}
	public void setNumberOfFreeVideos(Integer numberOfFreeVideos) {
		this.numberOfFreeVideos = numberOfFreeVideos;
	}
	public void setNumberOfPaidVideos(Integer numberOfPaidVideos) {
		this.numberOfPaidVideos = numberOfPaidVideos;
	}
	public void setLocationImages(HashSet<Image> locationImages) {
		this.locationImages = locationImages;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setCoverPhotoUrl(String coverPhotoUrl) {
		this.coverPhotoUrl = coverPhotoUrl;
	}
	
	/**
	 * Reset the isCoverPhoto flag to false for all of the Image objects.
	 * Pull out the Image associated with the passed in Id and set its
	 * isCoverPhoto flag to true. Take the url and set the this Location's
	 * coverPhotoUrl to the Image's relativeUrl.
	 * 
	 * @param id - Id of the photo to set as the cover/main photo
	 */
	public void setCoverPhotoUrl(Long id) {
		Iterator<Image> iterator;
		
		// Iterate through the Image's isCoverPhoto to false except
		// for the Image that matches the passed in id. Set its flag
		// to true.
		iterator = locationImages.iterator();
		while(iterator.hasNext() == true) {
			Long imageId;
			Image image;
			
			image = iterator.next();
			imageId = image.getId();
			if(imageId.equals(id) == true) {
				// Found the Image object to set as the Cover/Main image
				image.setCoverPhoto(true);
				coverPhotoUrl = image.getRelativeUrlPath();
			}
			else {
				image.setCoverPhoto(false);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		int idInt, objIdInt;
		
		if(obj == null) {
			return false;
		}
		if((obj instanceof Location) == false) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(id == null){
			// This instance of the location has not been
			// persisted yet so the id has not been set so
			// return false;
			return false;
		}
		idInt = id.intValue();
		objIdInt = ((Location)obj).getId().intValue();
		
		if(idInt != objIdInt) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		HashCodeBuilder builder;
		
		// Using two prime numbers to instantiate HashCodeBuilder
		builder = new HashCodeBuilder(17, 31);
		builder.append(id);
		
		return builder.toHashCode();
	}
	
	public Boolean isActive() {
		return active;
	}

	/**
	 * Add image to image collection and setup
	 * the bidirectional association
	 */
	public void addImage(Image image) {
		locationImages.add(image);
		image.setParentLocation(this);
		// Increment the number of photos based on
		// whether the photo is free or paid for
		PhotoPlanType photoPlanType;
		
		photoPlanType = image.getPhotoPlanType();
		if(photoPlanType == PhotoPlanType.FREE_PHOTO) {
			numberOfFreePhotos++;
		}
		else {
			numberOfPaidPhotos++;
		}
	}
	
	/**
	 * Remove image from the collection using the
	 * image object.
	 */
	public void removeImage(Image image) {
		if (locationImages.remove(image) == true) {
			// The collection contained the image
			// So check to see which photo type plan the 
			// image belongs to. Then decrement the correct
			// number of photos variable depending on whether
			// the image is free or paid for.
			PhotoPlanType photoPlanType;
			
			photoPlanType = image.getPhotoPlanType();
			if(photoPlanType == PhotoPlanType.FREE_PHOTO) {
				numberOfFreePhotos--;
			}
			else {
				numberOfPaidPhotos--;
			}
		}
	}
	
	/**
	 * Remove the image using the id
	 */
	public void removeImage(Long id) {
		Iterator<Image> itr;
		Image image = null;
		
		itr = locationImages.iterator();
		
		while(itr.hasNext() == true) {
			image = itr.next();
			if(image.getId().equals(id)) {
				// Found the image so break out.
				break;
			}
		}
		
		if(image != null) {
			locationImages.remove(image);
			// The collection contained the image
			// So check to see which photo type plan the 
			// image belongs to. Then decrement the correct
			// number of photos variable depending on whether
			// the image is free or paid for.
			PhotoPlanType photoPlanType;
			
			photoPlanType = image.getPhotoPlanType();
			if(photoPlanType == PhotoPlanType.FREE_PHOTO) {
				numberOfFreePhotos--;
			}
			else {
				numberOfPaidPhotos--;
			}
		}
	}
	
	/**
	 * Iterate through the collection of images until we
	 * find the one that is marked as the cover image.
	 * 
	 * @return The cover image also called main image.
	 */
	public Image getCoverImage() {
		Image coverImage = null;
		Iterator<Image> iterator;
		
		// Iterate through images until we find the main
		// image. If not found, return null.
		iterator = locationImages.iterator();
		while(iterator.hasNext() == true) {
			Image image;
			
			image = iterator.next();
			
			if(image.isCoverPhoto() == true) {
				coverImage = image;
				break;
			}
		}
		
		return coverImage;
	}
	
	/**
	 * 
	 * @param requestedImageId - Id of the image to retrieve.
	 * @return The image is found, otherwise null.
	 */
	public Image getImage(Long requestedImageId) {
		Image requestedImage = null;
		Iterator<Image> iterator;
		
		// Iterate through the collection of images until we
		// find the image associated with the passed in id.
		// If not found, return null.
		iterator = locationImages.iterator();
		while(iterator.hasNext() == true) {
			Image image;
			Long id;
			
			image = iterator.next();
			id = image.getId();
			
			if(requestedImageId.equals(id) == true) {
				requestedImage = image;
				break;
			}
		}
		return requestedImage;
	}
}
