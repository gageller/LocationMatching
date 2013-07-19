package com.locationmatching.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locationmatching.domain.User;

@Service
public interface LocationUserService {
	public void createUser(User user);
	public User getUser(Long id);
	public void deleteUser(Long id);
	public void modifyUser(User user);
	public List<User> getAllUsers();
}
