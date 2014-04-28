package com.locationmatching.util;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.locationmatching.enums.CreditCardType;
import com.locationmatching.enums.LocationType;

public class GlobalVars {
	public static Map<String, String> stateMap = new LinkedHashMap<String, String>();
	
	// Location types
	public static EnumMap<LocationType, String> locationTypes = new EnumMap<LocationType, String>(LocationType.class);
	
	// Credit card types
	public static EnumMap<CreditCardType, String> creditCardTypes = new EnumMap<CreditCardType, String>(CreditCardType.class);
	
	// Total number of free images for Basic and Premium plans
	public final static int BASIC_FREE_PHOTO_AMOUNT = 5;
	public final static int PREMIUM_FREE_PHOTO_AMOUNT = 10;
	
	// Email Parameters
	// Email Server Parameters
//	public final static String EMAIL_HOST_SERVER = "smtp-server.socal.rr.com";
//	public final static String EMAIL_HOST_SERVER = "smtp-server.roadrunner.com";
//	public final static int EMAIL_HOST_PORT = 587;
	public final static String EMAIL_HOST_SERVER = "yourlocationaccess.com";
	public final static int EMAIL_HOST_PORT = 25;
	// Support Email Address
//	public final static String SUPPORT_EMAIL_ADDRESS = "gageller@adelphia.net";
	public final static String SUPPORT_EMAIL_ADDRESS = "support@yourlocationaccess.com";
	// Customer Service Email Address
	public final static String CUSTOMER_SERVICE_EMAIL_ADDRESS = "gageller@adelphia.net";
	// Admin Email Server Login Credentials
	public final static String ADMIN_EMAIL_NAME = "gageller";
	public final static String ADMIN_EMAIL_PASSWORD = "rayray";
	
	public final static String SUPPORT_EMAIL_NAME = "support@yourlocationaccess.com";
	public final static String SUPPORT_EMAIL_PASSWORD = "MainwauwiE2";

	public final static String FROM_EMAIL_ADDRESS = SUPPORT_EMAIL_ADDRESS;
	
	// Admin, Provider and Scout jsp page folders
	public final static String ADMIN_JSP_FOLDER = "admin";
	public final static String PROVIDER_JSP_FOLDER = "provider";
	public final static String SCOUT_JSP_FOLDER = "scout";
	
	// Admin, Provider and Scout template home page names
	public final static String ADMIN_TEMPLATE_HOME_PAGE = "adminHomePage";
	public final static String PROVIDER_TEMPLATE_HOME_PAGE = "locationProviderHomePage";
	public final static String SCOUT_TEMPLATE_HOME_PAGE = "locationScoutHomePage";
	
	// Admin, Provider and Scout template home page URLs
	public final static String ADMIN_TEMPLATE_HOME_PAGE_URL = "/" + ADMIN_JSP_FOLDER + "/" + ADMIN_TEMPLATE_HOME_PAGE;
	public final static String PROVIDER_TEMPLATE_HOME_PAGE_URL = "/" + PROVIDER_JSP_FOLDER + "/" + PROVIDER_TEMPLATE_HOME_PAGE;
	public final static String SCOUT_TEMPLATE_HOME_PAGE_URL = "/" + SCOUT_JSP_FOLDER + "/" + SCOUT_TEMPLATE_HOME_PAGE;
	
	// Credit Card Static Variables
	//Message stating that further photos need to be paid for.
	public final static String PAY_FOR_PHOTO_MESSAGE = "You have added the maximum amout of free photos for this location. If you would like to add more, you will need to pay for each additional photo at %s per photo. Please make sure that you have a credit card linked to this account.";
	public final static String PRICE_PER_LOCATION_PHOTO = "$1.09";
	public final static String CREDIT_CARD_EXPIRATION_WARNING = "";
	public final static String CREDIT_CARD_HAS_EXPIRED_MESSAGE_PHOTO = "";
	public final static String CREDIT_CARD_HAS_EXPIRED_PER_PLAN_TYPE = "";
	public final static int MAX_CREDIT_CARDS = 3;
	
	static {
		stateMap.put("", "");
		stateMap.put("AL", "Alabama(AL)");
		stateMap.put("AK", "Alaska(AK)");
		stateMap.put("AZ", "Arizona(AZ)");
		stateMap.put("AR", "Arkansas(AR)");
		stateMap.put("CA", "California(CA)");
		stateMap.put("CO", "Colorado(CO)");
		stateMap.put("CT", "Connecticut(CT)");
		stateMap.put("DE", "Delaware(DE)");
		stateMap.put("DC", "District of Columbia(DC)");
		stateMap.put("FL", "Florida(FL)");
		stateMap.put("GA", "Georgia(GA)");
		stateMap.put("HI", "Hawaii(HI)");
		stateMap.put("ID", "Idaho(ID)");
		stateMap.put("IL", "Illinois(IL)");
		stateMap.put("IN", "Indiana(IN)");
		stateMap.put("IA", "Iowa(IA)");
		stateMap.put("KS", "Kansas(KS)");
		stateMap.put("KY", "Kentucky(KY)");
		stateMap.put("LA", "Louisiana(LA)");
		stateMap.put("ME", "Maine(ME)");
		stateMap.put("MD", "Maryland(MD)");
		stateMap.put("MA", "Massachusetts(MA)");
		stateMap.put("MI", "Michigan(MI)");
		stateMap.put("MN", "Minnesota(MN)");
		stateMap.put("MS", "Mississippi(MS)");
		stateMap.put("MO", "Missouri(MO)");
		stateMap.put("MT", "Montana(MT)");
		stateMap.put("NE", "Nebraska(NE)");
		stateMap.put("NV", "Nevada(NV)");
		stateMap.put("NH", "New Hampshire(NH)");
		stateMap.put("NJ", "New Jersey(NJ");
		stateMap.put("NM", "New Mexico(NM)");
		stateMap.put("NY", "New York(NY)");
		stateMap.put("NC", "North Carolina(NC)");
		stateMap.put("ND", "North Dakota(ND)");
		stateMap.put("OH", "Ohio(OH)");
		stateMap.put("OK", "Oklahoma(OK)");
		stateMap.put("OR", "Oregon(OR)");
		stateMap.put("PA", "Pennsylvania(PA)");
		stateMap.put("RI", "Rhode Island(RI)");
		stateMap.put("SC", "South Carolina(SC)");
		stateMap.put("SD", "South Dakota(SD)");
		stateMap.put("TN", "Tennessee(TN)");
		stateMap.put("TX", "Texas(TX)");
		stateMap.put("UT", "Utah(UT)");
		stateMap.put("VT", "Vermont(VT)");
		stateMap.put("VA", "Virginia(VA)");
		stateMap.put("WA", "Washington(WA)");
		stateMap.put("WV", "West Virginia(WV)");
		stateMap.put("WI", "Wisconsin(WI)");
		stateMap.put("WY", "Wyoming(WY)");	
	
		locationTypes.put(LocationType.BLANK, "");
		locationTypes.put(LocationType.APARTMENT, "Apartment");
		locationTypes.put(LocationType.CONDOMINIUM, "Condominium");
		locationTypes.put(LocationType.GARAGE, "Garage");
		locationTypes.put(LocationType.HOUSE, "House");
		locationTypes.put(LocationType.OFFICE_BUILDING, "Office Building");
		locationTypes.put(LocationType.PARK, "Park");
		locationTypes.put(LocationType.TOWNHOUSE, "Townhouse");	
		locationTypes.put(LocationType.ART_GALLERY, "Art Gallery");
		locationTypes.put(LocationType.CHURCH, "Church");
		locationTypes.put(LocationType.SYNAGOGUE, "Synagogue");
		locationTypes.put(LocationType.BUDDIST_TEMPLE, "Buddist Temple");
		locationTypes.put(LocationType.HOSPITAL, "Hospital");
		locationTypes.put(LocationType.MUSEUM, "Museum");
		locationTypes.put(LocationType.CEMETARY, "Cemetary");
		
		// Credit Card Type select box
		creditCardTypes.put(CreditCardType.BLANK, "");
		creditCardTypes.put(CreditCardType.VISA, "Visa");
		creditCardTypes.put(CreditCardType.MASTERCARD, "MasterCard");
		// Are not allowing American Express or Discover cards yet.
		//creditCardTypes.put(CreditCardType.AMEX, "American Express");
		//creditCardTypes.put(CreditCardType.DISCOVER, "Discover");
	}
}
