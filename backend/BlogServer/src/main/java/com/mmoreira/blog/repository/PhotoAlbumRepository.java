package com.mmoreira.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmoreira.blog.repository.entity.PhotoAlbum;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Integer>{
	PhotoAlbum findByUserNameAndDefaultAlbum(String userName, boolean defaultAlbum);
	List<PhotoAlbum> findAllByUserName(String userName);
}
