package com.locationmatching.util;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.locationmatching.enums.LocationType;

public class GlobalVars {
	public static Map<String, String> stateMap = new LinkedHashMap<String, String>();
	public static EnumMap<LocationType, String> locationTypes = new EnumMap<LocationType, String>(LocationType.class);
	
	static {
		stateMap.put("", "");
		stateMap.put("Alabama", "Alabama(AL)");
		stateMap.put("Alaska", "Alaska(AK)");
		stateMap.put("Arizona", "Arizona(AZ)");
		stateMap.put("Arkansas", "Arkansas(AR)");
		stateMap.put("California", "California(CA)");
		stateMap.put("Colorado", "Colorado(CO)");
		stateMap.put("Delaware", "Delaware(DE)");
		stateMap.put("District of Columbia", "District of Columbia(DC)");
		stateMap.put("Florida", "Florida(FL)");
		stateMap.put("Georgia", "Georgia(GA)");
		stateMap.put("Hawaii", "Hawaii(HI)");
		stateMap.put("Idaho", "Idaho(ID)");
		stateMap.put("Illinois", "Illinois(IL)");
		stateMap.put("Indiana", "Indiana(IN)");
		stateMap.put("Iowa", "Iowa(IA)");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("", "");
		stateMap.put("Nebraska", "Nebraska(NE)");
		stateMap.put("Nevada", "Nevada(NV)");
		stateMap.put("New Hampshire", "New Hampshire(NH)");
		stateMap.put("New York", "New York(NY)");
		stateMap.put("Ohio", "Ohio(OH)");
		stateMap.put("Oklahoma", "Oklahoma(OK)");
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
