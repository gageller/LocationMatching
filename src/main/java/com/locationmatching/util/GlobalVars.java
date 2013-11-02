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
	public final static String EMAIL_HOST_SERVER = "smtp-server.roadrunner.com";
	public final static int EMAIL_HOST_PORT = 587;
	// Admin Email Server Login Credentials
	public final static String ADMIN_EMAIL_NAME = "gageller";
	public final static String ADMIN_EMAIL_PASSWORD = "rayray";
	public final static String FROM_EMAIL_ADDRESS = "gageller@roadrunner.com";
	// Support Email Address
	public final static String SUPPORT_EMAIL_ADDRESS = "gageller@adelphia.net";
	// Customer Service Email Address
	public final static String CUSTOMER_SERVICE_EMAIL_ADDRESS = "gageller@adelphia.net";
	
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
		stateMap.put("Alabama", "Alabama(AL)");
		stateMap.put("Alaska", "Alaska(AK)");
		stateMap.put("Arizona", "Arizona(AZ)");
		stateMap.put("Arkansas", "Arkansas(AR)");
		stateMap.put("California", "California(CA)");
		stateMap.put("Colorado", "Colorado(CO)");
		stateMap.put("Connecticut", "Connecticut(CT)");
		stateMap.put("Delaware", "Delaware(DE)");
		stateMap.put("District of Columbia", "District of Columbia(DC)");
		stateMap.put("Florida", "Florida(FL)");
		stateMap.put("Georgia", "Georgia(GA)");
		stateMap.put("Hawaii", "Hawaii(HI)");
		stateMap.put("Idaho", "Idaho(ID)");
		stateMap.put("Illinois", "Illinois(IL)");
		stateMap.put("Indiana", "Indiana(IN)");
		stateMap.put("Iowa", "Iowa(IA)");
		stateMap.put("Kansas", "Kansas(KS)");
		stateMap.put("Kentucky", "Kentucky(KY)");
		stateMap.put("Louisiana", "Louisiana(LA)");
		stateMap.put("Maine", "Maine(ME)");
		stateMap.put("Maryland", "Maryland(MD)");
		stateMap.put("Massachusetts", "Massachusetts(MA)");
		stateMap.put("Michigan", "Michigan(MI)");
		stateMap.put("Minnesota", "Minnesota(MN)");
		stateMap.put("Mississippi", "Mississippi(MS)");
		stateMap.put("Missouri", "Missouri(MO)");
		stateMap.put("Montana", "Montana(MT)");
		stateMap.put("Nebraska", "Nebraska(NE)");
		stateMap.put("Nevada", "Nevada(NV)");
		stateMap.put("New Hampshire", "New Hampshire(NH)");
		stateMap.put("New Jersey", "New Jersey(NJ");
		stateMap.put("New Mexico", "New Mexico(NM)");
		stateMap.put("New York", "New York(NY)");
		stateMap.put("North Carolina", "North Carolina(NC)");
		stateMap.put("North Dakota", "North Dakota(ND)");
		stateMap.put("Ohio", "Ohio(OH)");
		stateMap.put("Oklahoma", "Oklahoma(OK)");
		stateMap.put("Oregon", "Oregon(OR)");
		stateMap.put("Pennsylvania", "Pennsylvania(PA)");
		stateMap.put("Rhode Island", "Rhode Island(RI)");
		stateMap.put("South Carolina", "South Carolina(SC)");
		stateMap.put("South Dakota", "South Dakota(SD)");
		stateMap.put("Tennessee", "Tennessee(TN)");
		stateMap.put("Texas", "Texas(TX)");
		stateMap.put("Utah", "Utah(UT)");
		stateMap.put("Vermont", "Vermont(VT)");
		stateMap.put("Virginia", "Virginia(VA)");
		stateMap.put("Washington", "Washington(WA)");
		stateMap.put("West Virginia", "West Virginia(WV)");
		stateMap.put("Wisconsin", "Wisconsin(WI)");
		stateMap.put("Wyoming", "Wyoming(WY)");	
	
		locationTypes.put(LocationType.BLANK, "");
		locationTypes.put(LocationType.APARTMENT, "Apartment");
		locationTypes.put(LocationType.CONDOMINIUM, "Condominium");
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
