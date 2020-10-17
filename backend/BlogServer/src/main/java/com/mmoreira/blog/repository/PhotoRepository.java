package com.mmoreira.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmoreira.blog.repository.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>{

}
