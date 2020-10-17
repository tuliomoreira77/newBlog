package com.mmoreira.blog.service;

import java.util.Base64;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mmoreira.blog.object.CommentDto;
import com.mmoreira.blog.object.PostDto;
import com.mmoreira.blog.repository.CommentRepository;
import com.mmoreira.blog.repository.PhotoAlbumRepository;
import com.mmoreira.blog.repository.PhotoRepository;
import com.mmoreira.blog.repository.PostRepository;
import com.mmoreira.blog.repository.entity.Comment;
import com.mmoreira.blog.repository.entity.Photo;
import com.mmoreira.blog.repository.entity.PhotoAlbum;
import com.mmoreira.blog.repository.entity.Post;
import com.mmoreira.blog.util.FileIO;

@Service
@Qualifier("PostService")
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PhotoAlbumRepository albumRepository;
	
	@Autowired
	private PhotoRepository photoRepository;
	
	private FileIO fileIO;
	
	@Transactional
	public Post createPost(PostDto postDto, String userName) {
		Post post = new Post();
		post.setText(postDto.getText());
		post.setUserName(userName);
		post.setPhoto(createPhotoForPost(postDto, userName));
		
		post = postRepository.save(post);
		return post;
	}
	
	@Transactional
	public Comment createComment(CommentDto commentDto, String userName) throws Exception {
		Post post = postRepository.findById(commentDto.getPostCode()).orElse(null);
		if(post == null) {
			throw new Exception("Post não encontrado");
		}
		
		Comment comment = new Comment();
		comment.setOwner(userName);
		comment.setText(commentDto.getText());
		comment.setPost(post);
		comment = commentRepository.save(comment);
		
		return comment;
	}
	
	
	private Photo createPhotoForPost(PostDto postDto, String userName) {
		String img64 = postDto.getPhotoBase64();
		Photo photo = null;
		if(img64 != null && !img64.isEmpty()) {
			byte[] compressedFile = fileIO.compressImage(Base64.getDecoder().decode(img64.getBytes()));
			fileIO.saveFile(fileIO.getFilePath(userName), compressedFile);
			photo = new Photo();
			photo.setPath(fileIO.getFilePath(userName));
			photo.setAlbum(findDefaultAlbum(userName));
			photoRepository.save(photo);
		}
		return photo;
	}
	
	private PhotoAlbum findDefaultAlbum(String userName) {
		PhotoAlbum photoAlbum = albumRepository.findByUserNameAndDefaultAlbum(userName, true);
		if(photoAlbum == null) {
			photoAlbum = new PhotoAlbum();
			photoAlbum.setDefaultAlbum(true);
			photoAlbum.setUserName(userName);
			photoAlbum = albumRepository.save(photoAlbum);
		}
		return photoAlbum;
	}
}
