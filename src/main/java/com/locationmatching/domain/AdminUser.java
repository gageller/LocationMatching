package com.locationmatching.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.locationmatching.enums.UserType;

@Entity
@DiscriminatorValue(value=UserType.Values.ADMIN)
public class AdminUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
