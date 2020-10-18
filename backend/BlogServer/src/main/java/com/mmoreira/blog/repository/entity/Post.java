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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.mmoreira.blog.object.BlogEntity;

@Entity
@Table(name = "posts")
public class Post implements BlogEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@Column(nullable = false, length = 255)
	private String userName;
	
	private String text;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="photo_code", referencedColumnName="code", updatable=false)
	private Photo photo;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getOwner() {
		return userName;
	}
	
	
}
