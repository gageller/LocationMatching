package com.locationmatching.component;

/**
 * Different plan types each location can belong to. If
 * the plan type is premium, then all the locations for that
 * provider become premium.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public enum LocationPlanType {
	/**
	 * Free - Up to three pictures per location
	 * PerPhoto - Up to three pictures plus one time cost for each additional picture.
	 * Premium - Unlimited locations with unlimited pictures per location.
	 */
	FREE, PER_PHOTO, PREMIUM;
}
