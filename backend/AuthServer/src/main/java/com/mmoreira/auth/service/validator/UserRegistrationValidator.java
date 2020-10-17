package com.mmoreira.auth.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mmoreira.auth.repository.UserRepository;
import com.mmoreira.auth.repository.entity.User;

@Component
public class UserRegistrationValidator {
	
	@Autowired
	private UserRepository userRepository;
	
	public void validate(User user) throws Exception{
		if(user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new Exception("Email vazio!");
		}
		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new Exception("Email vazio!");
		}
		
		User userFromDb = userRepository.findByEmail(user.getEmail());
		if(userFromDb != null) {
			throw new Exception("Usuário já cadastrado!");
		}
	}
}
