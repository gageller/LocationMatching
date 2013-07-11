package com.locationmatching.domain;

import java.util.Date;

import com.locationmatching.component.CreditCardType;

/**
 * Interface for Credit Cards. Made this an interface in case we need to
 * break out each credit card into its own class.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public interface CreditCard {
	// Getter Methods
	public Long getId();
	public CreditCardType getCreditCardType();
	public String getAccountNumber();
	/**
	 * CVV number is the three-digit number on the back of a Discover, Visa or MasterCard;
	 * or the four-digit number on the front of the American Express card.
	 * @return
	 */
	public String getCvvNumber();
	public String getUserName();
	public Date getExpirationDate();
	
	// Setter Methods
	public void setId(Long id);
	public void setCreditCardType(CreditCardType creditCardType);
	public void setAccountNumber(String accountNumber);
	public void setCvvNumber(String cvvNumber);
	public void setUserName(String userName);
	public void setExpirationDate(Date expirationDate);
}
