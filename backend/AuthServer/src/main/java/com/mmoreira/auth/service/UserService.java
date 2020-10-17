package com.mmoreira.auth.service;

import java.util.List;
import java.util.Map;

import com.mmoreira.auth.repository.entity.User;

public interface UserService {

	List<User> getUsers(Integer pageNumber);

	Map<String, Object> saveUser(User user);
}
