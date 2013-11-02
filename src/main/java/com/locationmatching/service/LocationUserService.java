package com.locationmatching.service;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.locationmatching.component.CreditCardImpl;
import com.locationmatching.component.Image;
import com.locationmatching.domain.User;
import com.locationmatching.exception.UserAlreadyExistsException;
import com.locationmatching.util.HibernateUtil;

/**
 * Generalized persistence service class that the LocationProviderService and LocationScoutService
 * inherit from. Handles functionality such as user authentication at login and credit card services.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class LocationUserService {
	
	public User authenticateUser(String userName, String password) {
		Session session = null;
		Transaction transaction = null;
		User user;
		
		try {
			Criteria criteria;

			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.add(Restrictions.eq("password", password));
			user = (User) criteria.uniqueResult();

			// Set the last access date and current access date values.
			if(user != null) {
				user.setLastAccessDate(new Date(System.currentTimeMillis()));
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
		
		return user;
	}

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
			criteria = session.createCriteria(User.class);
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
	
	
	public User getUser(Long id) {
		Session session = null;
		Transaction transaction = null;
		User user = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			user = (User) session.get(User.class, id);
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
	
	/**
	 * Returns a list of all users.
	 * 
	 * @return List<User>
	 */
	public List<User> getAllUsers() {
		List<User> userList = null;
		Session session = null;
		Transaction transaction = null;
		Query query = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			query = session.createQuery(" from User");
			
			userList = query.list();
			
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
		
		return userList;
	}

	/**
	 * Add image to the database.
	 * 
	 * @param image - Image object to add.
	 */
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
	
	/**
	 * Retrieve the Image object based on the id passed in.
	 * 
	 * @param id - id of the Image object to return.
	 * @return - Image object
	 */
	public Image getImage(Long id) {
		Session session = null;
		Transaction transaction = null;
		Image image = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			image = (Image) session.get(Image.class, id);
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
		
		return image;
	}
	
	/**
	 * Update the Image object passed in.
	 * 
	 * @param image - Image object to update.
	 */
	public void modifyImage(Image image) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(image);
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

	public void deleteImage(Image image, boolean deleteDirectory) {
		Set<Image> deleteImage = new HashSet<Image>();
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
	
			// We are only marking the Image to be deleted
			// so we are going to do an update of the Image object.
			session.update(image);
			
			// Add file to Set to be deleted
			deleteImage.add(image);
			
			// Delete the image file off of the server. If the directory is 
			// empty, delete it too.
			deleteImageFiles(deleteImage, deleteDirectory);
			
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
	 * Iterate through the collection of Image objects and delete the 
	 * associated image files off of the server.
	 * 
	 * @param deleteImages - image files to delete off of the server.
	 * @param deleteDirectory - Flag to determine whether to delete the directory folder. This would be done when all of the images have been deleted. 
	 */
	protected void deleteImageFiles(Set<Image>deleteImages, boolean deleteDirectory) {
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
	
	/**
	 * Save the CreditCardImpl object to the database
	 * 
	 * @param creditCard - Object to write to the database
	 */
	public void addCreditCard(CreditCardImpl creditCard) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.save(creditCard);
			
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
	 * Update the CreditCardImpl object to the database
	 * 
	 * @param creditCard - Object to update in the database
	 */
	public void modifyCreditCard(CreditCardImpl creditCard) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			session.update(creditCard);
			
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
