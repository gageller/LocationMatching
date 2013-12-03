package com.locationmatching.component;

import java.util.Calendar;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.locationmatching.domain.User;
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
@Entity
@Table(name="CreditCard")
public class CreditCardImpl implements CreditCard {
	/**
	 * Database Identifier
	 */
	@Id
	@GenericGenerator(strategy="increment", name="myGenerator")
	@GeneratedValue(generator="myGenerator")
	private Long id;
	
	/**
	 * Enum containg the different types of credit card being used.
	 * VISA, MASTERCARD, AMERICAN_EXPRESS, DISCOVER
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="CREDIT_CARD_TYPE")
	private CreditCardType creditCardType;
	/**
	 * Credit Card account number
	 */
	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;
	/**
	 * User First Name as it appears on the credit card
	 */
	@Column(name="CARD_HOLDER_FIRST_NAME")
	private String cardHolderFirstName;
	/**
	 * User First Name as it appears on the credit card
	 */
	@Column(name="CARD_HOLDER_LAST_NAME")
	private String cardHolderLastName;
	/**
	 * CVV number is the three-digit number on the back of a Discover, Visa or MasterCard;
	 * or the four-digit number on the front of the American Express card.
	 * @return
	 */
	@Column(name="CVV_NUMBER")
	private String cvvNumber;
	
	/**
	 * Expiration Month of the credit card
	 */
	@Column(name="EXPIRATION_MONTH")
	private String expirationMonth;
	
	/**
	 * Expiration Year on the credit card
	 */
	@Column(name="EXPIRATION_YEAR")
	private String expirationYear;
	
	/**
	 * Owner of the Credit Card
	 */
	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false, insertable=true, updatable=false)
	private User creditCardHolder;
	
	/**
	 * Billing Address
	 */
	@Column(name="BILLING_ADDRESS")
	private String billingAddress;
	
	/**
	 * Billing Address 2 (Optional)
	 */
	@Column(name="BILLING_ADDRESS2")
	private String billingAddress2;
	
	/**
	 * Billing City
	 */
	@Column(name="BILLING_CITY")
	private String billingCity;
	
	/**
	 * Billing State
	 */
	@Column(name="BILLING_STATE")
	private String billingState;
	
	/**
	 * Billing Zipcode
	 */
	@Column(name="BILLING_ZIPCODE")
	private String billingZipcode;
	
	/**
	 * Primary credit card for the account
	 */
	@Column(name="PRIMARY_CREDIT_CARD")
	Boolean primaryCreditCard;
	
	/**
	 * Whether this credit card is active
	 */
	@Column(name="ACTIVE")
	Boolean active;
	
	/**
	 * Date this credit card object was created.
	 */
	@Column(name="CREATION_DATE")
	Date creationDate;
	
	/**
	 * Date this credit card object was modified (Edited)
	 */
	@Column(name="MODIFIED_DATE")
	Date modifiedDate;
	
	/**
	 * Date this credit card was deactivated (Deleted)
	 */
	@Column(name="DEACTIVATION_DATE")
	Date deactivationDate;
	
	/**
	 * Service to process the payment.
	 */
	@Transient
	PaymentService paymentService;
	
	// Getter Methods
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	@Override
	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public String getCvvNumber() {
		return cvvNumber;
	}

	@Override
	public String getCardHolderFirstName() {
		return cardHolderFirstName;
	}

	@Override
	public String getCardHolderLastName() {
		return cardHolderLastName;
	}

	@Override
	public String getExpirationMonth() {
		return expirationMonth;
	}
	
	@Override
	public String getExpirationYear() {
		return expirationYear;
	}
	
	@Override
	public User getCreditCardHolder() {
		return creditCardHolder;
	}
	
	@Override
	public String getBillingAddress() {
		return billingAddress;
	}
	
	@Override
	public String getBillingAddress2() {
		return billingAddress2;
	}
	
	@Override
	public String getBillingCity() {
		return billingCity;
	}
	
	@Override
	public String getBillingState() {
		return billingState;
	}
	
	@Override
	public String getBillingZipcode() {
		return billingZipcode;
	}
	
	@Override
	public Boolean getPrimaryCreditCard() {
		return primaryCreditCard;
	}
	
	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public Date getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}
	
	// Setter Methods
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setCardHolderFirstName(String cardHolderFirstName) {
		this.cardHolderFirstName = cardHolderFirstName;
	}

	@Override
	public void setCardHolderLastName(String cardHolderLastName) {
		this.cardHolderLastName = cardHolderLastName;
	}
	
	@Override
	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}

	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	@Override
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@Override
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@Override
	public void setCreditCardHolder(User creditCardHolder) {
		this.creditCardHolder = creditCardHolder;
	}
	
	@Override
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	@Override
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}
	
	@Override
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	
	@Override
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	
	@Override
	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}
	
	@Override
	public void setPrimaryCreditCard(Boolean primaryCreditCard){
		this.primaryCreditCard = primaryCreditCard;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	/**
	 *  Check to see if this card is the primary one for the account
	 */
	@Override
	public Boolean isPrimaryCreditCard() {
		return primaryCreditCard;
	}

	/**
	 * See if the card has expired.
	 */
	@Override
	public Boolean hasCardExpired(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		
		// Subtract 1 because Months are 0 based.
		calendar.set(Calendar.MONTH, Integer.valueOf(expirationMonth) - 1);
		calendar.set(Calendar.YEAR, Integer.valueOf(expirationYear));
		Date expirationDate = calendar.getTime();
		
		return currentDate.after(expirationDate);
	}
	
	/**
	 * Check to see if this card is active.
	 */
	@Override
	public Boolean isActive() {
		return active;
	}
}
