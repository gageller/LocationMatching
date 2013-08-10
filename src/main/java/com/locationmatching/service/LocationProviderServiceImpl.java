package com.locationmatching.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.domain.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.LocationRequest;
import com.locationmatching.domain.User;
import com.locationmatching.enums.LocationType;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.util.HibernateUtil;

/**
 * Service that creates, returns, modifies, or deletes a LocationProvider.
 * Hibernate is used to handle the persistence to the database.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
@Service
public class LocationProviderServiceImpl implements LocationProviderService {

	@Override
	/**
	 * Persists the new user to the database. Throws an HibernateException
	 * if the commit or closing the session fails.
	 */
	public void createUser(User user) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			Criteria criteria;
			String userName;
			Integer rowCount;

			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// We need to see if the userName is already being used
			userName = user.getUserName();			
			criteria = session.createCriteria(LocationProvider.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.setProjection(Projections.rowCount());
			rowCount = (Integer) criteria.uniqueResult();

			if(rowCount > 0) {
				// User name already exists so let the user know.
				StringBuilder message = new StringBuilder();
				
				message.append("The user name, ");
				message.append(userName);
				message.append(", is already being used. Please select another user name.");
				throw new UserAlreadyExistsException(message.toString());
			}
			else {
				session.save(user);
			}
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				if(session != null) {
					session.close();
				}
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	/**
	 * Retrieve the user from the database based on the id passed in.
	 * 
	 * @return User
	 */
	public User getUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		LocationProvider user = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			user = (LocationProvider) session.get(LocationProvider.class, id);
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
		
		return user;
	}

	@Override
	/**
	 * Delete the user based on the id passed in.
	 */
	public void deleteUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		LocationProvider user;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Retrieve the user from its id and delete it.
			user = (LocationProvider) session.get(LocationProvider.class, id);
			session.delete(user);
			
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	/**
	 * Persist the modified user to the database.
	 */
	public void modifyUser(User user) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(user);
			
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	/**
	 * Returns a list of all of the Location Providers.
	 * 
	 * @return List<User>
	 */
	public List<User> getAllUsers() {
		List<User> providerList = null;
		Session session = null;
		Transaction transaction = null;
		Query query = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			query = session.createQuery(" from LocationProvider");
			
			providerList = query.list();
			
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
		
		return providerList;
	}

	@Override
	/**
	 * Retrieve the user based on the user name and password passed in. If not
	 * found, return null;
	 * 
	 * @return LocationProvider
	 */
	public User authenticateUser(String userName, String password) {
		Session session = null;
		Transaction transaction = null;
		LocationProvider provider;
		
		try {
			Criteria criteria;

			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			criteria = session.createCriteria(LocationProvider.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.add(Restrictions.eq("password", password));
			provider = (LocationProvider) criteria.uniqueResult();

			// Set the last access date and current access date values.
			if(provider != null) {
				Date date;
				
				// Get the last current date value and use it to set the
				// last access date value.
				date = provider.getCurrentDate();
				provider.setLastAccessDate(date);
				
				// Set the new current access date
				provider.setCurrentDate(new Date(System.currentTimeMillis()));
			}
			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				if(session != null) {
					session.close();
				}
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
		
		return provider;
	}
	
	/**
	 * Add a new location for this provider to the database. 
	 */
	public void addLocation(LocationProvider provider, Location location) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(provider);

			transaction.commit();
		}
		catch(HibernateException ex) {
			StringBuilder exceptionMessage;
			
			ex.printStackTrace();
			
			exceptionMessage = new StringBuilder();
			exceptionMessage.append("There was an error adding Location ");
			exceptionMessage.append(location.getLocationName());
			exceptionMessage.append(". Please try to request at a later time. If the problem ");
			exceptionMessage.append("persists, contact technical support. Sorry for the inconvience.");
			
			throw new LocationProcessingException(exceptionMessage.toString());
		}
		finally {
			if(session != null) {
				try {
					session.close();
				}
				catch(HibernateException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public List<LocationRequest>getLocationRequests(LocationRequest searchRequest) {
		ArrayList<LocationRequest>locationRequests = null;
		Session session = null;
		Transaction transaction = null;
		
		try {
			Criteria criteria;
			String value;
			LocationType locationType;
			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			criteria = session.createCriteria(LocationRequest.class);
			
			// Location Request Name
			value = searchRequest.getLocationRequestName();
			if(value != null && value.isEmpty() == false) {
				criteria.add(Restrictions.eq("locationRequestName", value));				
			}

			// Location Request City
			value = searchRequest.getLocationRequestCity();
			if(value != null && value.isEmpty() == false) {
				criteria.add(Restrictions.eq("locationRequestCity", value));				
			}

			// Location Request State
			value = searchRequest.getLocationRequestState();
			if(value != null && value.isEmpty() == false) {
				criteria.add(Restrictions.eq("locationRequestState", value));				
			}

			// Location Request Zip Code
			value = searchRequest.getLocationRequestZipcode();
			if(value != null && value.isEmpty() == false) {
				criteria.add(Restrictions.eq("locationRequestZipcode", value));				
			}

			// Location Request County
			value = searchRequest.getLocationRequestCounty();
			if(value != null && value.isEmpty() == false) {
				criteria.add(Restrictions.eq("locationRequestCounty", value));				
			}

			// Location Type
			locationType = searchRequest.getLocationType();
			if(locationType != null && locationType != LocationType.BLANK) {
				criteria.add(Restrictions.eq("locationType", locationType));				
			}

			locationRequests = (ArrayList<LocationRequest>) criteria.list();

			transaction.commit();
		}
		catch(HibernateException ex) {
			try{
				if(transaction != null) {
					// The commit failed so roll back the changes
					transaction.rollback();
				}
			}
			catch(HibernateException rollbackException) {
				rollbackException.printStackTrace();
				
				throw rollbackException;
			}
			ex.printStackTrace();
			
			throw ex;
		}
		finally {
			try {
				if(session != null) {
					session.close();
				}
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	
		return locationRequests;
	}
}	

