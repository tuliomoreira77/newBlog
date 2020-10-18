package com.mmoreira.blog.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.mmoreira.blog.object.BlogEntity;

@Entity
@Table(name = "photo_albums")
public class PhotoAlbum implements BlogEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@Column(nullable = false, length = 255)
	private String userName;
	
	@Column(length = 255)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "album", cascade = CascadeType.ALL)
	private List<Photo> photos;
	
	private boolean defaultAlbum;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getOwner() {
		return this.userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
