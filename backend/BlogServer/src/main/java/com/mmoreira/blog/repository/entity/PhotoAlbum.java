package com.mmoreira.blog.repository.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class PhotoAlbum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@Column(nullable = false, length = 255)
	private String userName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "album", cascade = CascadeType.ALL)
	private List<Photo> photos;
	
	private boolean defaultAlbum;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public boolean isDefaultAlbum() {
		return defaultAlbum;
	}

	public void setDefaultAlbum(boolean defaultAlbum) {
		this.defaultAlbum = defaultAlbum;
	}
	
	
}
