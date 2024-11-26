package com.company.nervManagementConsole.config;

import javax.persistence.EntityManager;

public class EntityManagerHandler implements AutoCloseable {
	private final EntityManager entityManager;
	
	 public EntityManagerHandler(EntityManager entityManager) {
	        this.entityManager = entityManager;
	    }

	    public EntityManager getEntityManager() {
	        return entityManager;
	    }
	    
	    public void beginTransaction() {
	        if (!entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().begin();
	        }
	    }

	    public void commitTransaction() {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().commit();
	        }
	    }
	    
	    public void persist(Object entity) {
	        entityManager.persist(entity);
	    }

	    public void remove(Object entity) {
	        entityManager.remove(entity);
	    }
	    
	    public void clear() {
	        if (entityManager.isOpen()) {
	            entityManager.clear();
	        }
	    }

	    @Override
	    public void close() {
	        if (entityManager.isOpen()) {
	            entityManager.close();
	        }
	    }
}
