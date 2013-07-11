package com.locationmatching.domain;

import java.util.Date;

import com.locationmatching.component.UserPlanType;

/**
 * Base class from which Location scouts and providers will inherit from.
 * 
 * @author Greg Geller
 * @since version 1.0
 * @version 1.0
 * 
 */
public abstract class User {
	/**
	 * Database identity of this user.
	 */
	private Long id;
	/**
	 * First name of this user.
	 */
	private String firstName;
	/**
	 * Last name of this user.
	 */
	private String lastName;
	/**
	 * Email address of this user.
	 */
	private String emailAddress;
	/**
	 * Phone number of this user.
	 */
	private String phoneNumber;
	/**
	 * Current date
	 */
	private Date currentDate;
	/**
	 * Date of last access.
	 */
	private Date lastAccessDate;
	/**
	 * Plan type of the user
	 */
	private UserPlanType userPlanType;
	
	// Getter Methods
	public Long getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public UserPlanType getUserPlanType() {
		return userPlanType;
	}
	
	// Setter Methods
	public void setId(Long id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public void setUserPlanType(UserPlanType userPlanType) {
		this.userPlanType = userPlanType;
	}
}
