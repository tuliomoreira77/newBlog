package com.mmoreira.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	
	@GetMapping("/authTest")
	public Map<String, String> testAuth() {
		Map<String, String> map = new HashMap<>();
		map.put("Teste", "teste");
		return map;
	}
	
	@PostMapping("/")
}
