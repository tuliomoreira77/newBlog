package com.mmoreira.blog.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmoreira.blog.object.BlogEntity;

@Entity
@Table(name = "photos")
public class Photo implements BlogEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@ManyToOne(optional = false)
	@JoinColumn(updatable = false, referencedColumnName = "code")
	@JsonIgnore
	private PhotoAlbum album;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date date;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getOwner() {
		return this.album.getOwner();
	}
	
}
