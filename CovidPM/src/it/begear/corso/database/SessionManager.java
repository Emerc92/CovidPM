package it.begear.corso.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Classe di gestione delle sessioni, viene usata dai Dao per interagire col database
public class SessionManager {
	
	private StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
	private Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
	private SessionFactory sFactory = metadata.getSessionFactoryBuilder().build();
	private Session session;
	
	
	public SessionFactory getSessionFactory() {
		return sFactory;
	}
	
	public boolean open() {
		try {
			session = getSessionFactory().openSession();
			return true;
		} catch(HibernateException e) {
			System.out.println("Errore, connessione col database non eseguita!");
			return false;
		}
	}
	
	public void close() {
		try {
			if(session != null) {
				session.close();
			}
		} catch(HibernateException e) {
			System.out.println("Errore nella chiusura del database!");
		}
	}

	public Session getSession() {
		return session;
	}

}
