package com.mmoreira.blog.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.PhotoAlbumDto;
import com.mmoreira.blog.object.PhotoDto;
import com.mmoreira.blog.repository.entity.Photo;
import com.mmoreira.blog.repository.entity.PhotoAlbum;
import com.mmoreira.blog.service.PhotoAlbumService;

@RestController
@RequestMapping("/api/v1")
public class PhotoAlbumController {
	
	@Autowired
	@Qualifier("PhotoAlbumService")
	private PhotoAlbumService albumService;
	
	@GetMapping("/albums")
	public List<PhotoAlbum> getAlbums(Principal principal) {
		return albumService.getPhotoAlbums(principal.getName());
	}
	
	@GetMapping("/albums/photos/{code}") 
	public Map<String,String> getPhotoFile(@PathVariable Integer code, Principal principal) throws ResourceNotFoundExeception, IOException {
		Map<String,String> map = new HashMap<String, String>();
		String photo = albumService.getPhotoFileBase64(code);
		map.put("photo", photo);
		return map;
	}
	
	@PostMapping("/albums")
	public PhotoAlbum createAlbum(@RequestBody PhotoAlbumDto albumDto, Principal principal) throws InvalidFieldsException {
		PhotoAlbum album = albumService.createPhotoAlbum(albumDto, principal.getName());
		return album;
	}
	
	@DeleteMapping("/albums/{code}")
	public Map<String,String> deleteAlbum(@PathVariable Integer code, Principal principal) throws NotOwnerException, ResourceNotFoundExeception {
		Map<String,String> map = new HashMap<String, String>();
		albumService.deletePhotoAlbum(code, principal.getName());
		map.put("success", "Entidade excluida");
		return map;
	}
	
	@PostMapping("/albums/{code}/photos")
	public Photo createPhoto(@RequestBody PhotoDto albumDto, Principal principal) throws InvalidFieldsException, NotOwnerException, ResourceNotFoundExeception {
		Photo photo = albumService.createPhoto(albumDto, principal.getName());
		return photo;
	}
	
	@DeleteMapping("/albums/photos/{code}")
	public Map<String,String> deletePhoto(@PathVariable Integer code, Principal principal) throws NotOwnerException, ResourceNotFoundExeception {
		Map<String,String> map = new HashMap<String, String>();
		albumService.deletePhoto(code, principal.getName());
		map.put("success", "Entidade excluida");
		return map;
	}
}
