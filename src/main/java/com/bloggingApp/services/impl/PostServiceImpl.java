package com.bloggingApp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bloggingApp.entities.Category;
import com.bloggingApp.entities.Post;
import com.bloggingApp.entities.User;
import com.bloggingApp.exceptions.ResourceNotFoundException;
import com.bloggingApp.payloads.CategoryDto;
import com.bloggingApp.payloads.PostDto;
import com.bloggingApp.payloads.PostResponse;
import com.bloggingApp.repositories.CategoryRepo;
import com.bloggingApp.repositories.PostRepo;
import com.bloggingApp.repositories.UserRepo;
import com.bloggingApp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
//	******************************
//	Create new post
//	******************************
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post  newPost= this.postRepo.save(post);
	
		return this.modelMapper.map(newPost, PostDto.class);
	}

	
	
//	******************************
//	Update post
//	******************************
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	
//	******************************
//	delete post
//	******************************
	@Override
	public void deletePost(Integer postId) {
		 Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		 
		 this.postRepo.delete(post);

	}

	
//	******************************
//	Get post by post id
//	******************************
	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	
//	******************************
//	Get all posts
//	******************************
	@Override
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize, String sortBy ,String sortDir) {
		
//		before pagination
//		List<Post> posts =  this.postRepo.findAll();
		
		
//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc"))
//		{
//			sort = Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort = Sort.by(sortBy).descending();
//		}
		
		//above logic of if , else using ternary operator
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		//get page size and page number from params
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		
		Page<Post> pageOfPost = this.postRepo.findAll(p);
		
		List<Post> allPosts = pageOfPost.getContent();
		
		
		List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageOfPost.getNumber());
		postResponse.setPageSize(pageOfPost.getSize());
		postResponse.setTotalElement(pageOfPost.getTotalElements());
		
		postResponse.setTotalPages(pageOfPost.getTotalPages());
		
		postResponse.setLastPage(pageOfPost.isLast());
		
		return postResponse;
	}

	
//	******************************
//	Get post by category
//	******************************
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {


		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","catyegory id", categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> 	postDtos= posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	
//	******************************
//	Get post by user
//	******************************
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	
//	******************************
//	Search post
//	******************************
	@Override
	public List<PostDto> searchPosts(String keyword) {

	 List<Post>	posts = this.postRepo.findByTitleContaining(keyword);
	 
	 List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	 return postDtos;
	}

}
