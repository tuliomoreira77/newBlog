package com.mmoreira.blog.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.CommentDto;
import com.mmoreira.blog.object.PageDto;
import com.mmoreira.blog.object.PostDto;
import com.mmoreira.blog.repository.entity.Comment;
import com.mmoreira.blog.repository.entity.Post;
import com.mmoreira.blog.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	
	@Autowired
	@Qualifier("PostService")
	private PostService postService;
	
	@GetMapping(path = {"/posts", "/posts/{page}" ,"/posts/{page}/{query}"})
	public PageDto<Post> getPosts(@PathVariable(required = false) Integer page, @PathVariable(required = false) String query, Principal principal) {
		PageDto<Post> postPage = postService.getPosts(page, query);
		return postPage;
	}
	
	@PostMapping("/posts")
	public Post createPost(@RequestBody PostDto postDto, Principal principal) throws InvalidFieldsException {
		Post post = postService.createPost(postDto, principal.getName());
		return post;
	}
	
	@PostMapping("/posts/{code}/comments")
	public Comment createComent(@PathVariable Integer code, @RequestBody CommentDto commentDto, Principal principal) throws Exception {
		commentDto.setPostCode(code);
		Comment comment = postService.createComment(commentDto, principal.getName());
		return comment;
	}
	
	@DeleteMapping("/posts/{code}")
	public Map<String,String> deletePost(@PathVariable Integer code, Principal principal) throws NotOwnerException, ResourceNotFoundExeception {
		Map<String,String> map = new HashMap<String, String>();
		postService.deletePost(code, principal.getName());
		map.put("success", "Entidade excluida");
		return map;
	}
	
	@DeleteMapping("/posts/comments/{code}")
	public Map<String,String> deleteComment(@PathVariable Integer code, Principal principal) throws NotOwnerException, ResourceNotFoundExeception {
		Map<String,String> map = new HashMap<String, String>();
		postService.deleteComment(code, principal.getName());
		map.put("success", "Entidade excluida");
		return map;
	}
}
