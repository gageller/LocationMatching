package com.locationmatching.service;

import java.util.List;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.domain.User;

public interface LocationProviderService {
	public void createUser(User user);
	public User getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(User user);
	public List<User> getAllUsers();
	public User authenticateUser(String userName, String password);
	public List<LocationRequest>getLocationRequests(LocationRequest searchRequest);
}
