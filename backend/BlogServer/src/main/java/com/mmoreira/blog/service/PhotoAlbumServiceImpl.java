package com.mmoreira.blog.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.PhotoAlbumDto;
import com.mmoreira.blog.object.PhotoDto;
import com.mmoreira.blog.repository.PhotoAlbumRepository;
import com.mmoreira.blog.repository.PhotoRepository;
import com.mmoreira.blog.repository.entity.Photo;
import com.mmoreira.blog.repository.entity.PhotoAlbum;
import com.mmoreira.blog.util.FileIO;
import com.mmoreira.blog.validator.OwnerValidator;
import com.mmoreira.blog.validator.TextFieldValidator;

@Service
@Qualifier("PhotoAlbumService")
public class PhotoAlbumServiceImpl implements PhotoAlbumService {
	
	@Autowired
	private PhotoAlbumRepository albumRepository;
	
	@Autowired
	private PhotoRepository photoRepository;
	
	@Autowired
	@Qualifier("LocalFileIO")
	private FileIO fileIO;
	
	@Override
	public List<PhotoAlbum> getPhotoAlbums(String userName) {
		return albumRepository.findAllByUserName(userName);
	}
	
	@Override
	public List<Photo> getPhotos(Integer albumCode) throws ResourceNotFoundExeception {
		PhotoAlbum album = albumRepository.findById(albumCode).orElse(null);
		if(album == null) {
			throw new ResourceNotFoundExeception();
		}
		return album.getPhotos();
	}
	
	@Override
	public String getPhotoFileBase64(Integer code) throws ResourceNotFoundExeception, IOException {
		Photo photo = photoRepository.findById(code).orElse(null);
		if(photo == null) {
			throw new ResourceNotFoundExeception();
		}
		
		byte[] rawPhoto = fileIO.getFile(photo.getPath());
		return Base64.getEncoder().encodeToString(rawPhoto);
	}
	
	@Override
	public PhotoAlbum createPhotoAlbum(PhotoAlbumDto photoAlbumDto ,String userName) throws InvalidFieldsException {
		if(TextFieldValidator.isNull(photoAlbumDto.getName())) {
			throw new InvalidFieldsException();
		}
		PhotoAlbum photoAlbum = new PhotoAlbum();
		photoAlbum.setDate(new Date());
		photoAlbum.setDefaultAlbum(false);
		photoAlbum.setName(photoAlbumDto.getName());
		photoAlbum.setUserName(userName);
		
		photoAlbum = albumRepository.save(photoAlbum);
		return photoAlbum;
	}
	
	@Override
	@Transactional
	public void deletePhotoAlbum(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception {
		PhotoAlbum photoAlbum = new PhotoAlbum();
		photoAlbum.setCode(code);
		OwnerValidator<PhotoAlbum> validator = new OwnerValidator<PhotoAlbum>(userName, photoAlbum, albumRepository);
		validator.isOwner();
		
		photoAlbum = albumRepository.findById(code).orElse(null);
		if(photoAlbum.isDefaultAlbum()) {
			throw new NotOwnerException();
		}
		albumRepository.delete(photoAlbum);
		for(Photo photo : photoAlbum.getPhotos()) {
			photoRepository.delete(photo);
			fileIO.deleteFile(photo.getPath());
		}
	}
	
	@Override
	@Transactional
	public Photo createPhoto(PhotoDto photoDto, String userName) throws NotOwnerException, ResourceNotFoundExeception, InvalidFieldsException {
		PhotoAlbum photoAlbum = new PhotoAlbum();
		photoAlbum.setCode(photoDto.getAlbumCode());
		OwnerValidator<PhotoAlbum> validator = new OwnerValidator<PhotoAlbum>(userName, photoAlbum, albumRepository);
		validator.isOwner();
		if(TextFieldValidator.isNull(photoDto.getPhotoBase64())) {
				throw new InvalidFieldsException();
		}
		
		Photo photo = new Photo();
		byte[] file = Base64.getDecoder().decode(photoDto.getPhotoBase64().getBytes());
		String filePath = fileIO.saveFile(file);
		photo = new Photo();
		photo.setPath(filePath);
		photo.setAlbum(photoAlbum);
		photo.setDate(new Date());
		photo = photoRepository.save(photo);
		
		return photo;
	}
	
	@Override
	public void deletePhoto(Integer code, String userName) throws ResourceNotFoundExeception, NotOwnerException {
		Photo photo = photoRepository.findById(code).orElse(null);
		if(photo == null) {
			throw new ResourceNotFoundExeception();
		}
		OwnerValidator<PhotoAlbum> validator = new OwnerValidator<PhotoAlbum>(userName, photo.getAlbum(), albumRepository);
		validator.isOwner();
		
		photoRepository.delete(photo);
		fileIO.deleteFile(photo.getPath());
	}
}
