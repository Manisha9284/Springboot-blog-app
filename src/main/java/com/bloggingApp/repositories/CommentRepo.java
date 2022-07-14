package com.bloggingApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingApp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
