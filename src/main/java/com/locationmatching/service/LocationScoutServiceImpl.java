package com.locationmatching.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.domain.User;
import com.locationmatching.enums.LocationType;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.util.HibernateUtil;
@Service
public class LocationScoutServiceImpl implements LocationScoutService {

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
		// TODO Auto-generated method stub

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

	/**
	 * Add the newly created LocationRequest object to the database. 
	 * We do an update on the parent (LocationScout) to add the LocationRequest
	 * to the database through the association that was set up
	 * 
	 * @param LocationScout
	 * @param LocationRequest
	 */
	public void addLocationRequest(LocationScout scout, LocationRequest locationRequest) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(scout);

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
	
	/*
	public Map<Long, LocationRequest>getLocationRequests(LocationRequest searchRequest) {
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
*/
	@Override
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
/*
	@Override
	public Map<Long, LocationRequest> getLocationRequests(
			LocationRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
