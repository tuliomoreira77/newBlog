package com.mmoreira.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmoreira.auth.repository.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
}
