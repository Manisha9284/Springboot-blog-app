package com.bloggingApp.services;



import com.bloggingApp.payloads.CategoryDto;
import com.bloggingApp.payloads.CategoryResponse;

public interface CategoryService {

	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//get
	public CategoryDto getCategoryById(Integer categoryId);
	
	//get all
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
}
