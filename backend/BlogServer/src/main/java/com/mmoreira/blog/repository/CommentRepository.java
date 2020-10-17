package com.mmoreira.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmoreira.blog.repository.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
