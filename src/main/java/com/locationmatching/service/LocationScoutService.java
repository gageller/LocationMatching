package com.locationmatching.service;

import java.util.List;
import java.util.Map;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.domain.User;

public interface LocationScoutService {
	public void createUser(LocationScout user);
	public User getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(User user);
	public List<User> getAllUsers();
	User authenticateUser(String userName, String password);
	public void addLocationRequest(LocationScout locationScout,
			LocationRequest locationRequest);
	public Map<Long, LocationRequest> getLocationRequests(LocationRequest searchRequest);
	public LocationRequest getLocationRequest(Long locationRequestId);
	public Map<Long, ScoutAlert>getScoutAlerts();
}
