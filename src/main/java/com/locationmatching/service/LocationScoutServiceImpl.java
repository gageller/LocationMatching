package com.locationmatching.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.enums.LocationType;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.util.HibernateUtil;
@Service
public class LocationScoutServiceImpl extends LocationUserService {
/*
	@Override
	public void createUser(LocationScout user) {
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
			criteria = session.createCriteria(LocationScout.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.setProjection(Projections.rowCount());
			rowCount = (Integer) criteria.uniqueResult();

			if(rowCount != null && rowCount > 0) {
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
	public User getUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		LocationScout scout;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			scout = (LocationScout) session.get(LocationScout.class, id);
			
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
		return scout;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyUser(User user) {
		Session session = null;
		Transaction transaction = null;
		LocationScout scout;
		
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
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public User authenticateUser(String userName, String password) {
		Session session = null;
		Transaction transaction = null;
		LocationScout scout;
		
		try {
			Criteria criteria;

			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			criteria = session.createCriteria(LocationScout.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.add(Restrictions.eq("password", password));
			scout = (LocationScout) criteria.uniqueResult();

			// Set the last access date and current access date values.
			if(scout != null) {
				scout.setLastAccessDate(new Date(System.currentTimeMillis()));
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
		
		return scout;
	}
*/
	/**
	 * Add the newly created LocationRequest object to the database. 
	 * We do an update on the parent (LocationScout) to add the LocationRequest
	 * to the database through the association that was set up
	 * 
	 * @param LocationRequest - New LocationRequest object to save to database.
	 */
	public void addLocationRequest(LocationRequest locationRequest) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.save(locationRequest);

			transaction.commit();
		}
		catch(HibernateException ex) {
			StringBuilder exceptionMessage;
			
			ex.printStackTrace();
			
			exceptionMessage = new StringBuilder();
			exceptionMessage.append("There was an error adding Location Request");
			exceptionMessage.append(locationRequest.getLocationRequestName());
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
	
//	@Override
	public Map<Long, LocationRequest> getLocationRequests(LocationRequest searchRequest) {
		ArrayList<LocationRequest>locationRequestsList = null;
		Map<Long, LocationRequest>locationRequests = new TreeMap<Long, LocationRequest>();
		Iterator<LocationRequest> iterator;
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

			locationRequestsList = (ArrayList<LocationRequest>) criteria.list();
			iterator = locationRequestsList.iterator();
			
			// Iterate through the collection and add each LocationRequest to the 
			// map keyed by its id.
			while(iterator.hasNext() == true) {
				Long id;
				LocationRequest locationRequest;
				
				locationRequest = iterator.next();
				locationRequests.put(locationRequest.getId(), locationRequest);
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
	
		return locationRequests;
	}

//	@Override
	public LocationRequest getLocationRequest(Long id) {
		Session session = null;
		Transaction transaction = null;
		LocationRequest locationRequest = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			locationRequest = (LocationRequest) session.get(LocationRequest.class, id);
			
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
		return locationRequest;
	}

	public void deleteLocationRequest(LocationScout locationScout, String[] locationRequestIdsToDelete) {
		Session session = null;
		Transaction transaction = null;
		LocationRequest locationRequest = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Iterate through array of ids to delete and delete the associated 
			// LocationRequests
			for(int index = 0; index < locationRequestIdsToDelete.length; index++) {
				String deleteId;
				
				deleteId = locationRequestIdsToDelete[index];
				locationRequest = (LocationRequest) session.get(LocationRequest.class, Long.valueOf(deleteId));
				// Remove from the LocationRequest collection
				locationScout.removeLocationRequest(locationRequest.getId());
				
				session.delete(locationRequest);
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

	/**
	 * Update the modified LocationRequest object to the database.
	 * 
	 * @param locationRequest - The update LocationRequest
	 */
	public void modifyLocationRequest(LocationRequest locationRequest) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(locationRequest);
			
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
	
	/**
	 * Add the new ScoutAlert to the database
	 * 
	 * @param scoutAlert - The new alert to be added.
	 */
	public void addScoutAlert(ScoutAlert scoutAlert) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.save(scoutAlert);

			transaction.commit();
		}
		catch(HibernateException ex) {
			StringBuilder exceptionMessage;
			
			ex.printStackTrace();
			
			exceptionMessage = new StringBuilder();
			exceptionMessage.append("There was an error adding the Alert ");
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
	
	/**
	 * Modify the ScoutAlert object to the database
	 * 
	 * @param scoutAlert - The alert to be updated.
	 */
	public void modifyScoutAlert(ScoutAlert scoutAlert) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(scoutAlert);

			transaction.commit();
		}
		catch(HibernateException ex) {
			StringBuilder exceptionMessage;
			
			ex.printStackTrace();
			
			exceptionMessage = new StringBuilder();
			exceptionMessage.append("There was an error modifying the Alert ");
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
}
