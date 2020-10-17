package com.mmoreira.auth.repository.entity.enuns;

public enum AuthorityType {
	ROLE_USER(0, "Usuário");
	
	private int id;
	private String description;
	
	private AuthorityType(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
