package com.locationmatching.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.component.LocationRequest;
import com.locationmatching.component.ScoutAlert;
import com.locationmatching.domain.LocationScout;
import com.locationmatching.enums.LocationType;
import com.locationmatching.enums.States;
import com.locationmatching.exception.LocationProcessingException;
import com.locationmatching.util.HibernateUtil;
@Service
public class LocationScoutServiceImpl extends LocationUserService {
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
			States states;
			states = searchRequest.getLocationRequestState();
			if(states != null) {
				criteria.add(Restrictions.eq("locationRequestState", states));				
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
		catch(Exception ex) {
			ex.printStackTrace();
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
	/**
	 * Get the scout alerts when the scout access the
	 * alerts page. This is so the scout gets any added
	 * alerts since last accessing the page.
	 * 
	 * @return Map of alerts.
	 */
	@SuppressWarnings("unchecked")
	public Set<ScoutAlert>getScoutAlerts() {
		Set<ScoutAlert>scoutAlerts;
		Iterator<ScoutAlert> itr;
		Session session = null;
		Transaction transaction = null;
		Query query;
		
		scoutAlerts = new LinkedHashSet<ScoutAlert>();
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			query = session.createQuery("from ScoutAlert order by id");
			query.list();
			itr = query.iterate();
			while(itr.hasNext() == true) {
				ScoutAlert alert;
				
				alert = itr.next();
				scoutAlerts.add(alert);
			}
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
		return scoutAlerts;
	}
}
