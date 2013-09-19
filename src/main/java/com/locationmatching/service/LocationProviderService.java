package com.locationmatching.service;

import java.util.List;

import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.User;

public interface LocationProviderService {
	public void createUser(User user);
	public User getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(User user);
	public List<User> getAllUsers();
	public User authenticateUser(String userName, String password);
	public void addLocation(Location location);
	public void modifyLocation(Location location);
	public Location getLocation(Long id);
	public void deleteLocations(LocationProvider locationProvider, String[] locationsToDelete);
	public void addImage(Image image);
	public void setCoverPicture(Location location);
	public void deleteImages(Location location, String[] photoDeleteIds);
}
