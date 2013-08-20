package com.locationmatching.service;

import java.util.List;

import com.locationmatching.component.LocationRequest;
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
}
