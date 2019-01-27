package com.ghidini.tm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import javax.persistence.EntityManager;

import com.ghidini.tm.dao.DAO;
import com.ghidini.tm.exceptions.DBCommitException;
import com.ghidini.tm.exceptions.IdNotFoundException;

public class DAOImpl<T,K> implements DAO<T, K> {

	protected EntityManager em;
	
	private Class<T> classe;

	@SuppressWarnings("unchecked")
	public DAOImpl(EntityManager em) {
		this.em = em;
		classe = 
			(Class<T>) ((ParameterizedType)
					getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
	}

	@Override
	public void insert(T entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new DBCommitException(entity);
		}
	}

	@Override
	public void update(T entity) {
		try{
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		}catch(Exception e){
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new DBCommitException(entity);
		}
	}

	@Override
	public void delete(K id) {
		T entity = findById(id);
		if (Objects.isNull(entity)) 
			throw new IdNotFoundException(id);
		try{
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}catch(Exception e){
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new DBCommitException(entity);
		}
	}

	@Override
	public T findById(K id) {
		try {
			return em.find(classe, id);
		} catch(Exception e) {
			throw new IdNotFoundException(id);
		}
	}

}
