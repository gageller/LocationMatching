package com.locationmatching.enums;

public enum States {
	AL(Values.AL, Values.AL_NAME),
	AK(Values.AK, Values.AK_NAME),
	AZ(Values.AZ, Values.AZ_NAME),
	AR(Values.AR, Values.AR_NAME),
	CA(Values.CA, Values.CA_NAME),
	C0(Values.CO, Values.CO_NAME),
	CT(Values.CT, Values.CT_NAME),
	DE(Values.DE, Values.DE_NAME),
	DC(Values.DC, Values.DC_NAME),
	FL(Values.FL, Values.FL_NAME),
	GA(Values.GA, Values.GA_NAME),
	HI(Values.HI, Values.HI_NAME),
	ID(Values.ID, Values.ID_NAME),
	IL(Values.IL, Values.IL_NAME),
	IN(Values.IN, Values.IN_NAME),
	IA(Values.IA, Values.IA_NAME),
	KS(Values.KS, Values.KS_NAME),
	KY(Values.KY, Values.KY_NAME),
	LA(Values.LA, Values.LA_NAME),
	ME(Values.ME, Values.ME_NAME),
	MD(Values.MD, Values.MD_NAME),
	MA(Values.MA, Values.MA_NAME),
	MI(Values.MI, Values.MI_NAME),
	MN(Values.MN, Values.MN_NAME),
	MS(Values.MS, Values.MS_NAME),
	MO(Values.MO, Values.MO_NAME),
	MT(Values.MT, Values.MT_NAME),
	NE(Values.NE, Values.NE_NAME),
	NV(Values.NV, Values.NV_NAME),
	NH(Values.NH, Values.NH_NAME),
	NJ(Values.NJ, Values.NJ_NAME),
	NM(Values.NM, Values.NM_NAME),
	NY(Values.NY, Values.NY_NAME),
	NC(Values.NC, Values.NC_NAME),
	ND(Values.ND, Values.ND_NAME),
	OH(Values.OH, Values.OH_NAME),
	OK(Values.OK, Values.OK_NAME),
	OR(Values.OR, Values.OR_NAME),
	PA(Values.PA, Values.PA_NAME),
	RI(Values.RI, Values.RI_NAME),
	SC(Values.SC, Values.SC_NAME),
	SD(Values.SD, Values.SD_NAME),
	TN(Values.TN, Values.TN_NAME),
	TX(Values.TX, Values.TX_NAME),
	UT(Values.UT, Values.UT_NAME),
	VT(Values.VT, Values.VT_NAME),
	VA(Values.VA, Values.VA_NAME),
	WA(Values.WA, Values.WA_NAME),
	WV(Values.WV, Values.WV_NAME),
	WI(Values.WI, Values.WI_NAME),
	WY(Values.WY, Values.WY_NAME);
	
	private String stateName;
	private String stateCode;
	
	private States(String stateCode, String stateName) {
		this.stateCode = stateCode;
		this.stateName = stateName;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public String getStateCode() {
		return stateCode;
	}
	
	public String toString() {
		return stateName;
	}
	
	private static class Values {
		// State Names
		public static String AL_NAME = "Alabama";
		public static String AK_NAME = "Alaska";
		public static String AZ_NAME = "Arizona";
		public static String AR_NAME = "Arkansas";
		public static String CA_NAME = "California)";
		public static String CO_NAME = "Colorado";
		public static String CT_NAME = "Connecticut";
		public static String DE_NAME = "Delaware";
		public static String DC_NAME = "District of Columbia";
		public static String FL_NAME = "Florida";
		public static String GA_NAME = "Georgia";
		public static String HI_NAME = "Hawaii";
		public static String ID_NAME = "Idaho";
		public static String IL_NAME = "Illinois";
		public static String IN_NAME = "Indiana";
		public static String IA_NAME = "Iowa";
		public static String KS_NAME = "Kansas";
		public static String KY_NAME = "Kentucky";
		public static String LA_NAME = "Louisiana";
		public static String ME_NAME = "Maine";
		public static String MD_NAME = "Maryland";
		public static String MA_NAME = "Massachusetts";
		public static String MI_NAME = "Michigan";
		public static String MN_NAME = "Minnesota";
		public static String MS_NAME = "Mississippi";
		public static String MO_NAME = "Missouri";
		public static String MT_NAME = "Montana";
		public static String NE_NAME = "Nebraska";
		public static String NV_NAME = "Nevada";
		public static String NH_NAME = "New Hampshire";
		public static String NJ_NAME = "New Jersey";
		public static String NM_NAME = "New Mexico";
		public static String NY_NAME = "New York";
		public static String NC_NAME = "North Carolina";
		public static String ND_NAME = "North Dakota";
		public static String OH_NAME = "Ohio";
		public static String OK_NAME = "Oklahoma";
		public static String OR_NAME = "Oregon";
		public static String PA_NAME = "Pennsylvania";
		public static String RI_NAME = "Rhode Island";
		public static String SC_NAME = "South Carolina";
		public static String SD_NAME = "South Dakota";
		public static String TN_NAME = "Tennessee";
		public static String TX_NAME = "Texas";
		public static String UT_NAME = "Utah";
		public static String VT_NAME = "Vermont";
		public static String VA_NAME = "Virginia";
		public static String WA_NAME = "Washington";
		public static String WV_NAME = "West Virginia";
		public static String WI_NAME = "Wisconsin";
		public static String WY_NAME = "Wyoming";
		
		// State Codes
		public static String AL = "AL";
		public static String AK = "AK";
		public static String AZ = "AZ";
		public static String AR = "AR";
		public static String CA = "CA)";
		public static String CO = "CO";
		public static String CT = "CT";
		public static String DE = "DE";
		public static String DC = "DC";
		public static String FL = "FL";
		public static String GA = "GA";
		public static String HI = "HI";
		public static String ID = "ID";
		public static String IL = "IL";
		public static String IN = "IN";
		public static String IA = "IA";
		public static String KS = "KS";
		public static String KY = "KY";
		public static String LA = "LA";
		public static String ME = "ME";
		public static String MD = "MD";
		public static String MA = "MA";
		public static String MI = "MI";
		public static String MN = "MN";
		public static String MS = "MS";
		public static String MO = "MO";
		public static String MT = "MT";
		public static String NE = "NE";
		public static String NV = "NV";
		public static String NH = "NH";
		public static String NJ = "NJ";
		public static String NM = "NM";
		public static String NY = "NY";
		public static String NC = "NC";
		public static String ND = "NC";
		public static String OH = "OH";
		public static String OK = "OK";
		public static String OR = "OR";
		public static String PA = "PA";
		public static String RI = "RI";
		public static String SC = "SC";
		public static String SD = "SD";
		public static String TN = "TN";
		public static String TX = "TX";
		public static String UT = "UT";
		public static String VT = "VT";
		public static String VA = "VA";
		public static String WA = "WA";
		public static String WV = "WV";
		public static String WI = "WI";
		public static String WY = "WY";		
	}
}
