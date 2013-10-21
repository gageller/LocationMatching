package com.locationmatching.component;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.locationmatching.domain.User;
import com.locationmatching.enums.CreditCardType;

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
	public String getCardHolderName();
	public Date getExpirationDate();
	public String getBillingAddress();
	public String getBillingAddress2();
	public String getBillingCity();
	public String getBillingState();
	public String getBillingZipcode();
	public User getCreditCardHolder();
	public Boolean getPrimaryCreditCard();
	public Boolean getActive();
	public Date getCreationDate();
	public Date getModifiedDate();
	public Date getDeactivationDate();
	
	// Setter Methods
	public void setId(Long id);
	public void setCreditCardType(CreditCardType creditCardType);
	public void setAccountNumber(String accountNumber);
	public void setCvvNumber(String cvvNumber);
	public void setCardHolderName(String cardHolderName);
	public void setExpirationDate(Date expirationDate);
	public void setBillingAddress(String billingAddress);
	public void setBillingAddress2(String billAddress2);
	public void setBillingCity(String billingCity);
	public void setBillingState(String billingState);
	public void setBillingZipcode(String billingZipcode);
	public void setCreditCardHolder(User creditCardHolder);
	public void setPrimaryCreditCard(Boolean primaryCreditCard);
	public void setActive(Boolean active);
	public void setCreationDate(Date creationDate);
	public void setModifiedDate(Date modifiedDate);
	public void setDeactivationDate(Date deactivationDate);

	// Check to see if this card is active.
	public Boolean isActive();
	
	// Check to see if this card is the primary one for the account
	public Boolean isPrimaryCreditCard();
	
	// See if the card has expired.
	public Boolean hasCardExpired(Date currentDate);
}
