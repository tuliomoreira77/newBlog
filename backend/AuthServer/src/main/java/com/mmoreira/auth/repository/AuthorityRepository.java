package com.mmoreira.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmoreira.auth.repository.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
	List<Authority> findByUserCode(Integer code);
}
