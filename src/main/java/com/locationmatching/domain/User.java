package com.locationmatching.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.locationmatching.enums.UserType;

/**
 * Base class from which Location scouts and providers will inherit from.
 * 
 * @author Greg Geller
 * @since version 1.0
 * @version 1.0
 * 
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="User")
@DiscriminatorColumn(name="USER_TYPE", discriminatorType=DiscriminatorType.STRING)
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
	@Column(name="CREATION_DATE") 
	private Date creationDate;
	/**
	 * Date of last access.
	 */
	@Column(name="LAST_ACCESS_DATE")
	private Date lastAccessDate;
	
	/**
	 * Whether or not this user is active
	 */
	@Column(name="ACTIVE")
	Boolean active;
	
	/**
	 * Which type of user this is. 
	 * Admin, Provider or Scout
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="USER_TYPE", nullable=false, insertable=false, updatable=false)
	private UserType userType;
	
	/**
	 * Determines the level of the user. Level 1 means that the Provider has a Premium Account
	 * but does not have to pay for it. For Admin user level 1 can add or delete other users
	 * including admin users. Admin user 2 cannot delete or add Admin Users.
	 */
	@Column(name="USER_LEVEL")
	private Short userLevel;
	
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
	public Date getCreationDate() {
		return creationDate;
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
	public Boolean getActive() {
		return active;
	}
	public UserType getUserType() {
		return userType;
	}
	public Short getUserLevel() {
		return userLevel;
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
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public void setActive(Boolean active) {
		this.active = active;
	}
	public void setUserLevel(Short userLevel) {
		this.userLevel = userLevel;
	}
	
//	public void setUserType(UserType userType) {
//		this.userType = userType;
//	}
}
