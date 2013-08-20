package com.locationmatching.component;

import java.util.Date;

import com.locationmatching.enums.CreditCardType;

/**
 * Class for gathering and storing the credit card information.
 * For right now we are handling all of the different cards
 * (Visa, MasterCard, American Express, Discover) with one class.
 * If we need to break out each card type into it's own class,
 * we can do so because of the CreditCard interface;
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class CreditCardImpl implements CreditCard {
	/**
	 * Database Identifier
	 */
	private Long id;
	/**
	 * Enum containg the different types of credit card being used.
	 * VISA, MASTERCARD, AMERICAN_EXPRESS, DISCOVER
	 */
	private CreditCardType creditCardType;
	/**
	 * Credit Card account number
	 */
	private String accountNumber;
	/**
	 * User Name as it appears on the credit card
	 */
	private String userName;
	/**
	 * CVV number is the three-digit number on the back of a Discover, Visa or MasterCard;
	 * or the four-digit number on the front of the American Express card.
	 * @return
	 */
	private String cvvNumber;
	/**
	 * Expiration Date of the credit card
	 */
	Date expirationDate;
	
	// Getter Methods
	public Long getId() {
		return id;
	}

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getCvvNumber() {
		return cvvNumber;
	}

	public String getUserName() {
		return accountNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
