package com.mmoreira.blog.service;

import java.io.IOException;
import java.util.List;

import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.PhotoAlbumDto;
import com.mmoreira.blog.object.PhotoDto;
import com.mmoreira.blog.repository.entity.Photo;
import com.mmoreira.blog.repository.entity.PhotoAlbum;

public interface PhotoAlbumService {

	PhotoAlbum createPhotoAlbum(PhotoAlbumDto photoAlbumDto, String userName) throws InvalidFieldsException;

	void deletePhoto(Integer code, String userName) throws ResourceNotFoundExeception, NotOwnerException;

	Photo createPhoto(PhotoDto photoDto, String userName)
			throws NotOwnerException, ResourceNotFoundExeception, InvalidFieldsException;

	void deletePhotoAlbum(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception;

	List<PhotoAlbum> getPhotoAlbums(String userName);

	String getPhotoFileBase64(Integer code) throws ResourceNotFoundExeception, IOException;

	List<Photo> getPhotos(Integer albumCode) throws ResourceNotFoundExeception;

}
