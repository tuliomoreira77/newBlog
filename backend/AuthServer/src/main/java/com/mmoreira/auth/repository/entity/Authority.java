package com.mmoreira.auth.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.mmoreira.auth.repository.entity.enuns.AuthorityType;

@Entity
@Table(name="authorities")
public class Authority {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int code;
	
	@Column(nullable = false, columnDefinition = "VARCHAR(255)")
	@Enumerated(EnumType.STRING)
	private AuthorityType authority;
	
	@ManyToOne	
	@PrimaryKeyJoinColumn
	private User user;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public AuthorityType getAuthority() {
		return authority;
	}

	public void setAuthority(AuthorityType authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}

