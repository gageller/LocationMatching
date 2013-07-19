package com.locationmatching.service;

import java.util.List;

import com.locationmatching.domain.LocationScout;

public interface LocationScoutService {
	public void createUser(LocationScout user);
	public LocationScout getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(LocationScout user);
	public List<LocationScout> getAllUsers();
}
