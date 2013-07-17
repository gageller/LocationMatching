package com.locationmatching.domain;

import java.util.Date;

import com.locationmatching.component.UserType;

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
	 * User name for this account
	 */
	private String userName;
	/**
	 * Password for this account
	 */
	private String password;
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
	 * Which type of user this is. 
	 * Provider or Scout
	 */
	private UserType userType;
	
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
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	public UserType getUserType() {
		return userType;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
