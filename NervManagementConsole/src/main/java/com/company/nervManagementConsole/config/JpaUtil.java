package com.company.nervManagementConsole.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static final String PERSISTENCE_UNIT_NAME = "NervManagementConsole";
	private static EntityManagerFactory entityManagerFactory;
	
	//con static ci garantiamo che venga eseguit una volta sola al caricamento della classe
	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	
    // meotodo per ottenere entitymanagerfactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    // metodo per ottenere entityManagerHandler e quindi fare tutto closeable
    public static EntityManagerHandler getEntityManager() {
        return new EntityManagerHandler(entityManagerFactory.createEntityManager());
    }

    // metodo per chiudere entitymanagerfactory
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}
