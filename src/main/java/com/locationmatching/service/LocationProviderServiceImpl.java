package com.locationmatching.service;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.locationmatching.component.LocationRequest;
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
public class LocationProviderServiceImpl extends LocationUserService {

//	@Override
	/**
	 * Persists the new user to the database. Throws an HibernateException
	 * if the commit or closing the session fails.
	 */
	/*
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
*/
//	@Override
	/**
	 * Retrieve the user from the database based on the id passed in.
	 * 
	 * @return User
	 */
/*	public User getUser(Long id) {
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

//	@Override
	/**
	 * FOR ADMIN USE.
	 * 
	 * Delete the user based on the id passed in. This will cascade down through the collections
	 * and delete them from the database. We also need to delete any image files associated with
	 * this LocationProvider.
	 * 
	 * @param id - Id of the LocationProvider to delete.
	 */
/*	public void deleteUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		LocationProvider user;
		Iterator<Location> locationIterator;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Retrieve the user from its id and delete it.
			user = (LocationProvider) session.get(LocationProvider.class, id);
			// Delete the image files.
			locationIterator = user.getProviderLocations().iterator();
			
			while(locationIterator.hasNext() == true) {
				Location location;
				
				location = locationIterator.next();
				deleteImageFiles(location.getLocationImages(), true);
			}
			
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
*/
//	@Override
	/**
	 * Persist the modified user to the database.
	 */
/*	public void modifyUser(User user) {
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
*/
//	@Override
	/**
	 * Returns a list of all of the Location Providers.
	 * 
	 * @return List<User>
	 */
/*	public List<User> getAllUsers() {
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
*/
//	@Override
	/**
	 * Retrieve the user based on the user name and password passed in. If not
	 * found, return null;
	 * 
	 * @return LocationProvider
	 */
/*	public User authenticateUser(String userName, String password) {
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
*/	
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

//	@Override
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

//	@Override
	/**
	 * 
	 * Mark the Location instance that are associated with the ids selected by the 
	 * user to inactive. Photos for this Location will be deleted.
	 * Locations are keep for audit purposes.
	 * 
	 * @param locationProvider - Need to have the location provider so we can get the
	 * locations based on the passed in ids
	 * @param locationsToDelete - Ids of the locations selected by the user to delete.
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
				
				deleteImageFiles(location.getLocationImages(), true);
				location.setActive(false);
				
				session.update(location);
			}
			
//			session.delete(locationProvider);
			
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
	
	/**
	 * Set the cover photo url and id in the Location object.
	 * Persist the image settings for the cover photo flag. The settings for
	 * the cover photo flag have been set by the setCoverPhotoUrl method of the 
	 * The cascading style for the parent Location object is set to delete-orphan.
	 * Because of this just updating the parent Location will not be cascading down
	 * to the Image collection.
	 * 
	 * @param location - Location object that contains the collection of images
	 */
//	@Override
	public void setCoverPicture(Location location) {
		Session session = null;
		Transaction transaction = null;
		Iterator<Image> iterator;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Update the Location parent object because the cover photo url
			// has been updated.
			session.update(location);
			
			// Now iterate through the Image collection and update each of Image
			// objects because the cover page flag has changed.
			iterator = location.getLocationImages().iterator();
			while(iterator.hasNext() == true) {
				Image image;
				
				image= iterator.next();
				session.update(image);
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

//	@Override
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

//	@Override
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

	/**
	 * Add image to the database.
	 * 
	 * @param image - Image object to add.
	 */
/*	@Override
	public void addImage(Image image) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.save(image);
			
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
*/	
	/**
	 * Iterate through the collection of Image objects and delete the 
	 * associated image files off of the server.
	 * 
	 * @param deleteImages - image files to delete off of the server.
	 * @param deleteDirectory - Flag to determine whether to delete the directory folder. This would be done when all of the images have been deleted. 
	 */
/*	private void deleteImageFiles(Set<Image>deleteImages, boolean deleteDirectory) {
		String absoluteFilePath = "";
		File deleteFile;

		// Delete the image files from the server.
		for(Image image: deleteImages) {
			absoluteFilePath = image.getAbsoluteFilePath();
			deleteFile = new File(absoluteFilePath);
			deleteFile.delete();
		}

		// If we are deleting the directory as well, we need to strip off
		// the file name and create a new file object for the directory and
		// delete it.
		if(deleteDirectory == true) {
			int index;
			
			// Strip off the file name and delete the entire directory.
			index = absoluteFilePath.lastIndexOf(File.separator);
			absoluteFilePath = absoluteFilePath.substring(0, index);
			
			deleteFile = new File(absoluteFilePath);
			
			if(deleteFile.isDirectory() == true) {
				deleteFile.delete();
			}
		}
	}
*/
	/**
	 * Delete photos based on the ids passed in from the
	 * String array
	 * 
	 * @param location - Location that owns the pictures;
	 * @param photoDeleteIds - Array of ids of Images to delete.
	 */
//	@Override
	public void deleteImages(Location location, String[] photoDeleteIds) {
		Session session = null;
		Transaction transaction = null;
		Set<Image> imagesToDelete = new HashSet<Image>();
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			// Loop through the delete Image id array and call the Location's
			// removeImage method which will return the Image object that has been
			// removed. Set that Image object's hidden flag to true and add it to 
			// the Image set that will be deleted from the server.
			// objects because the cover page flag has changed.
			for(int index = 0; index < photoDeleteIds.length; index++) {
				Image image;
				String imageId;
				
				imageId = photoDeleteIds[index];
				image = location.removeImage(Long.valueOf(imageId));
				image.setHidden(true);
				
				// If this image is the cover photo, we need to set the cover photo url
				// of the Location object to blank.
				if(image.isCoverPhoto() == true) {
					location.setCoverPhotoUrl("");
				}
				session.update(image);
				// Add image to set so we can get the file path and delete the image
				// off of the server.
				imagesToDelete.add(image);
			}
			
			// Update the Location object because the picture count has changed.
			session.update(location);
			
			transaction.commit();
			boolean deleteDirectory = false;
			
			// Delete the directory if there are no more Image objects in the collection.
			// Also set the Location's coverPhotoUrl to empty string.
			if(location.getLocationImages().isEmpty() == true) {
				deleteDirectory = true;
				location.setCoverPhotoUrl("");
			}
			deleteImageFiles(imagesToDelete, deleteDirectory);
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

	// Methods for the Location Scout that are defined as abstract in the LocationUserService
	// base class so just implement empty method bodies.
	@Override
	public LocationRequest getLocationRequest(Long id) {
		return null;
	}

	@Override
	public Map<Long, LocationRequest> getLocationRequests(LocationRequest searchRequest) {
		return null;
	}
}	

