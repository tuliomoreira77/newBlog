package com.mmoreira.blog.validator;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.BlogEntity;

public class OwnerValidator<T extends BlogEntity> {
	
	private String canditate;
	private T entity;
	private JpaRepository<T, Integer> repository;
	
	public OwnerValidator(String canditate, T entity, JpaRepository<T, Integer> repository) {
		super();
		this.canditate = canditate;
		this.entity = entity;
		this.repository = repository;
	}
	
	public boolean isOwner() throws NotOwnerException, ResourceNotFoundExeception{
		T entityFromDb = repository.findById(entity.getCode()).orElse(null);
		if(entityFromDb != null && entityFromDb.getOwner().equals(canditate)) {
			return true;
		} else {
			if(entityFromDb == null) {
				throw new ResourceNotFoundExeception();
			}
			throw new NotOwnerException();
		}
	}
}
