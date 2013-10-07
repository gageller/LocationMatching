package com.locationmatching.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.locationmatching.component.Image;
import com.locationmatching.component.Location;
import com.locationmatching.domain.LocationProvider;
import com.locationmatching.domain.User;
import com.locationmatching.enums.PhotoStatus;
import com.locationmatching.util.HibernateUtil;

@Service
public class AdminServiceImpl extends LocationUserService {
	/**
	 * Return a map of unapproved photos to be evaluated. Map will be keyed by the user id and the value will be an 
	 * ArrayList of images that need approval.
	 * 
	 * @param userName - If searchByUserName == true, use the user name to find the user id.
	 * @return - Map of unapproved photos.
	 */
	public LocationProvider searchProviderUnapprovedPhotosByUserName(String userName) {
		Session session = null;
		Transaction transaction = null;
		LocationProvider user = null;
		Query query = null;
		// Used to store the Locations that have photos needing approval. This collection will
		// replace the providerLocations collection in the user.
		Set<Location> locations = new LinkedHashSet<Location>();
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			query = session.createQuery("from User where userName =:userName");
			query.setString("userName", userName);
			user = (LocationProvider) query.uniqueResult();
			
			if(user != null) {
				// See which of the locations has photos needing approval
				for(Location location: user.getProviderLocations()) {
					for(Image image: location.getLocationImages()) {
						// If photo needs approval set the flag to true so we know 
						PhotoStatus status;
						
						status = image.getStatus();
						if(status == PhotoStatus.NOT_REVIEWED || status == PhotoStatus.SKIPPED_ON_REVIEW) {
							// Found photo that needs approval
							// This location has photos needing approval so
							// add it to the collection to be added back into 
							// LocationProvider user.
							locations.add(location);
							break;
						}
					}
				}
				
				user.setProviderLocations(locations);
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
		
		return user;
	}
	
	/**
	 * Return a map of unapproved photos to be evaluated. Map will be keyed by the user id and the value will be an 
	 * ArrayList of images that need approval.
	 * 
	 * @param userName - If searchByUserName == true, use the user name to find the user id.
	 * @return - Map of unapproved photos.
	 */
	public List<LocationProvider> searchAllProviderUnapprovedPhotos() {
		Session session = null;
		Transaction transaction = null;
		Query query = null;
		List<LocationProvider> users = new ArrayList<LocationProvider>();
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			query = session.createQuery("select distinct u from User u, Location l, Image i where l.id = i.parentLocation.id and u.id = l.locationOwner.id and (i.status='NOT_REVIEWED' or i.status='SKIPPED_ON_REVIEW') order by u.userName");
			users = query.list();
			
			if(users != null) {
				// Go through each of the users and find out which locations have photos that 
				// to be reviewed.
				for(LocationProvider user : users) {
					// Used to store the Locations that have photos needing approval. This collection will
					// replace the providerLocations collection in the user.
					Set<Location> locations = new LinkedHashSet<Location>();
					// See which of the locations has photos needing approval
					for(Location location : user.getProviderLocations()) {
						for(Image image: location.getLocationImages()) {
							// If photo needs approval set the flag to true so we know 
							PhotoStatus status;
							
							status = image.getStatus();
							if(status == PhotoStatus.NOT_REVIEWED || status == PhotoStatus.SKIPPED_ON_REVIEW) {
								// Found photo that needs approval
								// This location has photos needing approval so
								// add it to the collection to be added back into 
								// LocationProvider user.
								locations.add(location);
								break;
							}
						}
					}
					
					user.setProviderLocations(locations);
				}
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
		
		return users;
	}

	/**
	 * Delete the user based on the id passed in. This will cascade down through the collections
	 * and delete them from the database. We also need to delete any image files associated with
	 * this User.
	 * 
	 * @param id - Id of the User to delete.
	 */
	public void deleteUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		User user;
//		Iterator<Location> locationIterator;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Retrieve the user from its id and delete it.
			user = (User) session.get(User.class, id);
			// Delete the image files.
			/*locationIterator = user.getProviderLocations().iterator();
			
			while(locationIterator.hasNext() == true) {
				Location location;
				
				location = locationIterator.next();
				deleteImageFiles(location.getLocationImages(), true);
			}
*/			
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

}
