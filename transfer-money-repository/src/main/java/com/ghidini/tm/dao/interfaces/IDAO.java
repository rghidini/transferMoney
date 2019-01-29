package com.ghidini.tm.dao.interfaces;

import java.util.List;

public interface IDAO<T,K> {
	
	void insert(T entity);
	T update(T entity);
	void delete(K id);
	T findById(K id);
	List<T> findAll();

}
