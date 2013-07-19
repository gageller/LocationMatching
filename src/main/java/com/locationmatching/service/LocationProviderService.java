package com.locationmatching.service;

import java.util.List;

import com.locationmatching.domain.LocationProvider;

public interface LocationProviderService {
	public void createUser(LocationProvider user);
	public LocationProvider getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(LocationProvider user);
	public List<LocationProvider> getAllUsers();
}
