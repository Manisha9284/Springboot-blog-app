package com.bloggingApp.services;

import java.util.List;

import com.bloggingApp.entities.Post;
import com.bloggingApp.payloads.PostDto;
import com.bloggingApp.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	
	//delete
	void deletePost(Integer postId);
	
	
	//get one post
	PostDto getPostById(Integer postId);
	
	
	//get all post
//	List<PostDto> getAllPost( Integer pageNumber, Integer pageSize);
	
	PostResponse getAllPost( Integer pageNumber, Integer pageSize , String sortBy , String sortDir);
	
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer postId);
	
	
	//search post by given keyword
	List<PostDto> searchPosts(String keyword);
	
}
