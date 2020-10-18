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

import com.mmoreira.blog.object.BlogEntity;

@Entity
@Table(name = "comments")
public class Comment implements BlogEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
	
	@Column(nullable = false, length = 255)
	private String owner;
	
	@ManyToOne(optional = false)
	@JoinColumn(updatable = false, referencedColumnName = "code")
	private Post post;
	
	@Column(nullable = false, length = 255)
	private String text;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date date;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
