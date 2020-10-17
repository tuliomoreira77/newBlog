package com.mmoreira.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmoreira.auth.repository.UserRepository;
import com.mmoreira.auth.repository.entity.Authority;
import com.mmoreira.auth.repository.entity.User;
import com.mmoreira.auth.repository.entity.enuns.AuthorityType;
import com.mmoreira.auth.service.validator.UserRegistrationValidator;

@Service
@Qualifier("UserService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRegistrationValidator userRegistrationValidator;
	
	@Override
	public List<User> getUsers(Integer pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber, 50, Sort.Direction.ASC, "email");
		return userRepository.findAll(pageRequest).getContent();
	}
	
	@Override
	public Map<String, Object> saveUser(User user) {
		Map<String, Object> map = new HashMap<>();
		try {
			userRegistrationValidator.validate(user);
		} catch (Exception e) {
			map.put("error", e.getMessage());
			return map;
		}
		
		user.setAuthorities(getCommonUserAuthorities(user));
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(user);
		map.put("success", "true");
		return map;
	}
	
	private List<Authority> getCommonUserAuthorities(User user) {
		List<Authority> list = new ArrayList<>();
		Authority auth = new Authority();
		auth.setAuthority(AuthorityType.ROLE_USER);
		auth.setUser(user);
		list.add(auth);
		return list;
	}
}
