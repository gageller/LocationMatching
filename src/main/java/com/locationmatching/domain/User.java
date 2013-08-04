package com.locationmatching.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;

import com.locationmatching.component.UserType;

/**
 * Base class from which Location scouts and providers will inherit from.
 * 
 * @author Greg Geller
 * @since version 1.0
 * @version 1.0
 * 
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable{
	static final long serialVersionUID = 1L;
	
	/**
	 * Database identity of this user.
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	/**
	 * User name for this account
	 */
	@Column(name="USER_NAME")
	private String userName;
	/**
	 * Password for this account
	 */
	@Column(name="PASSWORD")
	private String password;
	/**
	 * First name of this user.
	 */
	@Column(name="FIRST_NAME")
	private String firstName;
	/**
	 * Last name of this user.
	 */
	@Column(name="LAST_NAME")
	private String lastName;
	/**
	 * Email address of this user.
	 */
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	/**
	 * Phone number of this user.
	 */
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	/**
	 * Current date
	 */
	@Column(name="CURRENT_ACCESS_DATE") 
	private Date currentDate;
	/**
	 * Date of last access.
	 */
	@Column(name="LAST_ACCESS_DATE")
	private Date lastAccessDate;
	/**
	 * Which type of user this is. 
	 * Provider or Scout
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="USER_TYPE")
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
