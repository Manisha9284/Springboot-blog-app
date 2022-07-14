package com.bloggingApp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bloggingApp.entities.Category;
import com.bloggingApp.entities.User;
import com.bloggingApp.exceptions.ResourceNotFoundException;
import com.bloggingApp.payloads.CategoryDto;
import com.bloggingApp.payloads.CategoryResponse;
import com.bloggingApp.payloads.UserDto;
import com.bloggingApp.payloads.UserResponse;
import com.bloggingApp.repositories.CategoryRepo;
import com.bloggingApp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category cat = this.modelMapper.map(categoryDto, Category.class);
		
		Category addedCategory = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));

		 this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		
			
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	//@Override
//	public List<CategoryDto> getAllCategories() {
//
//		List<Category> categories = this.categoryRepo.findAll();
//		List<CategoryDto> categoryDtos = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
//		
//		return categoryDtos;
//	}
	
	@Override
	public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize) {
		
//		before pagination
//		List<Post> posts =  this.postRepo.findAll();
		
		//get page size and page number from params
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		
		Page<Category> pageOfCategory = this.categoryRepo.findAll(p);
		
		List<Category> allCategories = pageOfCategory.getContent();
		
		
		List<CategoryDto> categoryDtos = allCategories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		CategoryResponse categoryResponse = new CategoryResponse();
		
		categoryResponse.setContent(categoryDtos);
		categoryResponse.setPageNumber(pageOfCategory.getNumber());
		categoryResponse.setPageSize(pageOfCategory.getSize());
		categoryResponse.setTotalElement(pageOfCategory.getTotalElements());
		
		categoryResponse.setTotalPages(pageOfCategory.getTotalPages());
		
		categoryResponse.setLastPage(pageOfCategory.isLast());
		
		return categoryResponse;
	}

}
