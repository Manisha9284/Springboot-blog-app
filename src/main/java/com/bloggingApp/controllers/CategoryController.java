package com.bloggingApp.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingApp.payloads.ApiResponse;
import com.bloggingApp.payloads.CategoryDto;
import com.bloggingApp.payloads.CategoryResponse;
import com.bloggingApp.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		 CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		 
		 return new ResponseEntity<CategoryDto>(createCategory ,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable Integer catId )
	{
		CategoryDto updatedCat = this.categoryService.updateCategory(categoryDto, catId);
		
		return new ResponseEntity<CategoryDto>(updatedCat, HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory( @PathVariable Integer catId)
	{
		this.categoryService.deleteCategory(catId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!", true), HttpStatus.OK);
	}
	
	//get only one
	@GetMapping("/{cateId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer cateId)
	{
		CategoryDto categoryDto = this.categoryService.getCategoryById(cateId);
		
		return new ResponseEntity<CategoryDto>(categoryDto , HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(value = "pageNumber" , defaultValue = "1", required = false) Integer pageNumber,
			 @RequestParam(value = "pageSize" , defaultValue = "5" , required = false) Integer pageSize){
		CategoryResponse categories = this.categoryService.getAllCategories(pageNumber , pageSize);
		
		return ResponseEntity.ok(categories);
	}
}
