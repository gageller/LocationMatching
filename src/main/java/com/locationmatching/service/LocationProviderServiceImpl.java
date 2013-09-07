package com.locationmatching.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.User;
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
				// Set the last accessed date
				provider.setLastAccessDate(new Date(System.currentTimeMillis()));
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

	@Override
	/**
	 * Get the Location associated with the id.
	 * 
	 * @param id - id of the Location
	 * @return - Location object associated with the passed in id
	 */
	public Location getLocation(Long id) {
		Session session = null;
		Transaction transaction = null;
		Location location = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			location = (Location) session.get(Location.class, id);
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
		
		return location;
	}

	@Override
	/**
	 * Delete the Location instance that are associated with
	 * the ids selected by the user to delete.
	 * 
	 * @param  locationsToDelete - Ids of the locations selected by the user to delete.
	 */
	public void deleteLocations(LocationProvider locationProvider, String[] locationsToDelete) {
		Session session = null;
		Transaction transaction = null;
		
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			for(int index = 0; index < locationsToDelete.length; index++) {
				Location location;
				String id;
				
				id = locationsToDelete[index];
				location = locationProvider.removeLocation(Long.valueOf(id));
				
				deleteImageFiles(location.getLocationImages());
				session.delete(location);
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
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void deleteImageFiles(Set<Image>deleteImages) {
		for(Image image: deleteImages) {
			String absoluteFilePath;
			File deleteFile;
			
			absoluteFilePath = image.getAbsoluteFilePath();
			deleteFile = new File(absoluteFilePath);
			deleteFile.delete();
		}
	}

	@Override
	public void addLocation(Location location) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.save(location);
			
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
			try {
				session.close();
			}
			catch(HibernateException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void modifyLocation(Location location) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(location);
			
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
}	

