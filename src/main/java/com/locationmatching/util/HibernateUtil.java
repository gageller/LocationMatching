package com.locationmatching.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class to create the Hibernate SessionFactory. Also used to fetch
 * the Hibernate Session object from the factory.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 */
public class HibernateUtil {
	/**
	 * Session Factory is only created once at startup
	 * because it is expensive.
	 */
	private static SessionFactory sessionFactory;

	static {
		resetSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Instantiates the Session Factory
	 */
	public static void resetSessionFactory() {
		Configuration configuration;
		
		configuration = new Configuration();
		configuration = configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	/**
	 * Gets the Session to be used for persisting the domain objects
	 * 
	 * @return
	 */
	public static Session getSession() {
		return sessionFactory.openSession();
	}


}
