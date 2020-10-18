package com.mmoreira.blog.service;

import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.CommentDto;
import com.mmoreira.blog.object.PageDto;
import com.mmoreira.blog.object.PostDto;
import com.mmoreira.blog.repository.entity.Comment;
import com.mmoreira.blog.repository.entity.Post;

public interface PostService {

	Post createPost(PostDto postDto, String userName);
	Comment createComment(CommentDto commentDto, String userName) throws Exception;
	PageDto<Post> getPosts(Integer pageNumber, String search);
	void deletePost(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception;
	void deleteComment(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception;

}
