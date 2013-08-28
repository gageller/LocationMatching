package com.locationmatching.service;

import java.util.List;
import java.util.Map;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.domain.User;

public interface LocationProviderService {
	public void createUser(User user);
	public User getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(User user);
	public List<User> getAllUsers();
	public User authenticateUser(String userName, String password);
	public Map<Long, LocationRequest>getLocationRequests(LocationRequest searchRequest);
}
