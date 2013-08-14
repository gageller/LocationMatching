package com.locationmatching.domain;

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
	 * The image must first be reviewed before it can be visible
	 * to location scouts. The default for this will be false.
	 */
	@Column(name="APPROVED")
	private Boolean approved;
	
	/**
	 * Date the file was uploaded
	 */
	@Column(name="UPLOAD_DATE")
	private Date uploadDate;
	
	/**
	 * Date the image was approved to be displayed
	 */
	@Column(name="APPROVAL_DATE")
	private Date approvalDate;
	
	/**
	 * Absolute path to the file location.
	 */
	@Column(name="ABSOLUTE_FILE_PATH")
	private String absoluteFilePath;
	
	/**
	 * Relative path to the file. Used for displaying
	 * on the page.
	 */
	@Column(name="RELATIVE_FILE_PATH")
	private String relativeFilePath;
	
	/**
	 * Type of the image. 
	 * Supported types: JPEG, GIF, PNG
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="IMAGE_TYPE")
	private ImageType imageType;
	
	/**
	 * Parent location for this image.
	 */
	@ManyToOne()
	@JoinColumn(name="LOCATION_ID")
	private Location parentLocation;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public Boolean getApproved() {
		return approved;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}
	public String getRelativeFilePath() {
		return relativeFilePath;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public Location getParentLocation() {
		return parentLocation;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}
	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}
}
