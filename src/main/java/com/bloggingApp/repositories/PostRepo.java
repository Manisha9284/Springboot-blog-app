package com.bloggingApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingApp.entities.Category;
import com.bloggingApp.entities.Post;
import com.bloggingApp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	
	//if exception InvalidDataAccessApiUsageException is introduce in postman do following
	//@Query("select p from Post where p.title like : key")
	//List<Post> findByTitleContaining(@Param("key") String title);
	
	//and in PostServiceImpl same code as it is but change in one line as below
	//List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
	
	
	
	List<Post> findByTitleContaining(String title);
}
