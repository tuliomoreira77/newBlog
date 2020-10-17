package com.mmoreira.blog.repository.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@ManyToOne(optional = false)
	@JoinColumn(updatable = false, referencedColumnName = "code")
	private PhotoAlbum album;
	
	private String path;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public PhotoAlbum getAlbum() {
		return album;
	}

	public void setAlbum(PhotoAlbum album) {
		this.album = album;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
