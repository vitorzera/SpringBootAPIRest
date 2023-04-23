package br.upf.userdept.repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public abstract class CarRepositoryImpl implements ValueRepository {
	
	@PersistenceContext	
	private EntityManager em;	

}