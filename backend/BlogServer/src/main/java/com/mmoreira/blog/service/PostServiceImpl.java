package com.mmoreira.blog.service;

import java.util.Base64;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;
import com.mmoreira.blog.object.CommentDto;
import com.mmoreira.blog.object.PageDto;
import com.mmoreira.blog.object.PostDto;
import com.mmoreira.blog.repository.CommentRepository;
import com.mmoreira.blog.repository.PhotoAlbumRepository;
import com.mmoreira.blog.repository.PhotoRepository;
import com.mmoreira.blog.repository.PostRepository;
import com.mmoreira.blog.repository.entity.Comment;
import com.mmoreira.blog.repository.entity.Photo;
import com.mmoreira.blog.repository.entity.PhotoAlbum;
import com.mmoreira.blog.repository.entity.Post;
import com.mmoreira.blog.repository.entity.search.PostSearch;
import com.mmoreira.blog.util.FileIO;
import com.mmoreira.blog.validator.OwnerValidator;
import com.mmoreira.blog.validator.TextFieldValidator;

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
	
	@Autowired
	@Qualifier("LocalFileIO")
	private FileIO fileIO;
	
	@Override
	public PageDto<Post> getPosts(Integer pageNumber, String search) {
		pageNumber = pageNumber == null ? 0 : pageNumber;
		PostSearch postSearch = new PostSearch(search);
		Pageable pageable = PageRequest.of(pageNumber, 50, Sort.Direction.DESC, "date");
		Page<Post> posts = postRepository.findAll(postSearch, pageable);
		PageDto<Post> pageDto = new PageDto<>(posts);
		return pageDto;
	}
	
	@Override
	@Transactional
	public Post createPost(PostDto postDto, String userName) throws InvalidFieldsException {
		if(TextFieldValidator.isNull(postDto.getText())) {
			throw new InvalidFieldsException();
		}
		Post post = new Post();
		post.setText(postDto.getText());
		post.setUserName(userName);
		post.setPhoto(createPhotoForPost(postDto, userName));
		post.setDate(new Date());
		
		post = postRepository.save(post);
		return post;
	}
	
	@Override
	@Transactional
	public void deletePost(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception {
		Post post = new Post();
		post.setCode(code);
		OwnerValidator<Post> validator = new OwnerValidator<Post>(userName, post, postRepository);
		
		validator.isOwner();
			
		post = postRepository.findById(code).orElse(null);
		
		if(post.getPhoto() != null) {
			photoRepository.delete(post.getPhoto());
		}
		commentRepository.deleteAll(post.getComments());
		postRepository.delete(post);
		
		if(post.getPhoto() != null) {
			fileIO.deleteFile(post.getPhoto().getPath());
		}
	}
	
	@Override
	@Transactional
	public void deleteComment(Integer code, String userName) throws NotOwnerException, ResourceNotFoundExeception {
		Comment comment = new Comment();
		comment.setCode(code);
		OwnerValidator<Comment> validator = new OwnerValidator<Comment>(userName, comment, commentRepository);

		validator.isOwner();
		comment = commentRepository.findById(code).orElse(null);
		commentRepository.delete(comment);
	}

	@Override
	@Transactional
	public Comment createComment(CommentDto commentDto, String userName) throws ResourceNotFoundExeception, InvalidFieldsException {
		if(TextFieldValidator.isNull(commentDto.getText())) {
			throw new InvalidFieldsException();
		}
		
		Post post = postRepository.findById(commentDto.getPostCode()).orElse(null);
		if(post == null) {
			throw new ResourceNotFoundExeception();
		}
		
		Comment comment = new Comment();
		comment.setOwner(userName);
		comment.setText(commentDto.getText());
		comment.setPost(post);
		comment.setDate(new Date());
		comment = commentRepository.save(comment);
		
		return comment;
	}
	
	
	private Photo createPhotoForPost(PostDto postDto, String userName) {
		String img64 = postDto.getPhotoBase64();
		Photo photo = null;
		if(img64 != null && !img64.isEmpty()) {
			byte[] file = Base64.getDecoder().decode(img64.getBytes());
			String filePath = fileIO.saveFile(file);
			photo = new Photo();
			photo.setPath(filePath);
			photo.setAlbum(findDefaultAlbum(userName));
			photo.setDate(new Date());
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
			photoAlbum.setDate(new Date());
			photoAlbum.setName("Fotos da linha do tempo");
			photoAlbum = albumRepository.save(photoAlbum);
		}
		return photoAlbum;
	}
}
