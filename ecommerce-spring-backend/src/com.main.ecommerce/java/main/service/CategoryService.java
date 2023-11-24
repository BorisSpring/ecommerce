package main.service;

import main.domain.Category;
import main.exception.CategoryException;
import main.model.CategoryDTO;
import main.requests.CreateCategoryRequest;
import main.requests.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {

     void addCategory(CreateCategoryRequest categoryRequest) throws CategoryException;

     void updateCategory(UpdateCategoryRequest updateCategoryRequest) throws  CategoryException;

     void deleteCategoryById(int id);

     List<CategoryDTO> getAllCategories();

     Category findById(Integer categoryId);
}
