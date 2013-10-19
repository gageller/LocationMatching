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

import com.locationmatching.enums.ImageType;
import com.locationmatching.enums.PhotoPlanType;
import com.locationmatching.enums.PhotoStatus;

/**
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
@Entity()
@Table(name="Image")
public class Image {
	/**
	 * Database identifier
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	
	/**
	 * Name of the image
	 */
	@Column(name="FILE_NAME")
	private String fileName;
	
	/**
	 * Date the file was uploaded
	 */
	@Column(name="UPLOAD_DATE")
	private Date uploadDate;
	
	/**
	 * Date the image was approved to be displayed
	 */
	@Column(name="ADMIN_REVIEWED_DATE")
	private Date adminReviewedDate;
	
	/**
	 * Date the user deleted the photos
	 */
	@Column(name="DELETION_DATE")
	private Date deletionDate;
	
	/**
	 * Absolute path to the file location.
	 */
	@Column(name="ABSOLUTE_FILE_PATH")
	private String absoluteFilePath;
	
	/**
	 * Relative path to the file. Used for displaying
	 * on the page.
	 */
	@Column(name="RELATIVE_URL_PATH")
	private String relativeUrlPath;
	
	/**
	 * Type of the image. 
	 * Supported types: JPEG, GIF, PNG
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="IMAGE_TYPE")
	private ImageType imageType;
	
	/**
	 * Flag to determine which photo will be used as the cover or
	 * main picture of the location. This picture will be included
	 * in the email to the location scout who owns the location request.
	 */
	@Column(name="COVER_PHOTO")
	private Boolean coverPhoto = false;
	
	/**
	 * Enum of the photo plan this Image belongs to. FREE_PHOTO or PAID_PHOTO
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="PHOTO_PLAN_TYPE")
	private PhotoPlanType photoPlanType;
	
	/**
	 * Parent location for this image.
	 */
	@ManyToOne()
	@JoinColumn(name="LOCATION_ID")
	private Location parentLocation;
	
	/**
	 * Enum of Status of this image. Could be not reviewed, approved, declined,
	 * deleted, review skipped
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private PhotoStatus status;
	
	/**
	 * Notes added by person reviewing this image. Will probably be used for declined images.
	 */
	@Column(name="admin_notes")
	private String adminNotes;
	
	/**
	 * Flag to tell if the user has hidden the photo. We are not deleting photos.
	 * We are letting the user hide the ones they don't want to show instead.
	 */
	@Column(name="hidden")
	private Boolean hidden;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public Date getAdminReviewedDate() {
		return adminReviewedDate;
	}
	public Date getDeletionDate() {
		return deletionDate;
	}
	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}
	public String getRelativeUrlPath() {
		return relativeUrlPath;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public Location getParentLocation() {
		return parentLocation;
	}
	public Boolean getCoverPhoto() {
		return coverPhoto;
	}
	public PhotoPlanType getPhotoPlanType() {
		return photoPlanType;
	}
	public PhotoStatus getStatus() {
		return status;
	}
	public String getAdminNotes() {
		return adminNotes;
	}
	public Boolean getHidden() {
		return hidden;
	}
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public void setAdminReviewedDate(Date adminReviewedDate) {
		this.adminReviewedDate = adminReviewedDate;
	}
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}
	public void setRelativeUrlPath(String relativeUrlPath) {
		this.relativeUrlPath = relativeUrlPath;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}
	public void setCoverPhoto(Boolean isCoverPhoto) {
		this.coverPhoto = isCoverPhoto;
	}
	public void setPhotoPlanType(PhotoPlanType photoPlanType) {
		this.photoPlanType = photoPlanType;
	}
	public void setStatus(PhotoStatus status) {
		this.status = status;
	}
	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	/**
	 * Flag to tell whether this image will be used as the main or cover image
	 * that will be emailed to prospective clients.
	 * 
	 * @return Boolean
	 */
	public Boolean isCoverPhoto() {
		return coverPhoto;
	}
	
	/**
	 * Flag that tells us if the photo is hidden or not.
	 * 
	 * @return Boolean
	 */
	public Boolean isHidden() {
		return hidden;
	}
}

