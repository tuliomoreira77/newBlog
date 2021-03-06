package com.mmoreira.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmoreira.blog.repository.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	Page<Post> findAll(Specification<Post> search, Pageable page);
}
