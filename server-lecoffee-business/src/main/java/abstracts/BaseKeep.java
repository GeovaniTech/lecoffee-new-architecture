package abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BaseKeep {	
	@PersistenceContext(unitName = "lecoffee_datasource")
	private EntityManager entityManager;

	// Getters and Setters
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}