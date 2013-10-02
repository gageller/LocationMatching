package com.locationmatching.util;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.locationmatching.enums.LocationType;

public class GlobalVars {
	public static Map<String, String> stateMap = new LinkedHashMap<String, String>();
	public static EnumMap<LocationType, String> locationTypes = new EnumMap<LocationType, String>(LocationType.class);
	// Total number of free images for Basic and Premium plans
	public final static int BASIC_FREE_PHOTO_AMOUNT = 5;
	public final static int PREMIUM_FREE_PHOTO_AMOUNT = 10;
	
	// Email parameters
	public final static String ADMIN_EMAIL_NAME = "gageller";
	public final static String ADMIN_EMAIL_PASSWORD = "rayray";
	public final static String EMAIL_HOST_SERVER = "smtp-server.socal.rr.com";
	public final static int EMAIL_HOST_PORT = 587;
	
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
		locationTypes.put(LocationType.OFFICE, "Office Building");
		locationTypes.put(LocationType.PARK, "Park");
		locationTypes.put(LocationType.TOWNHOUSE, "Townhouse");	
	}
}
