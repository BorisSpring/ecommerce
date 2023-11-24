package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Category;
import main.exception.CategoryException;
import main.exception.ResourceNotFoundException;
import main.mappers.CategoryMapper;
import main.model.CategoryDTO;
import main.repository.CategoryRepository;
import main.requests.CreateCategoryRequest;
import main.requests.UpdateCategoryRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Transactional
    @Override
    public void addCategory(CreateCategoryRequest categoryRequest) throws CategoryException {
        if(categoryRepository.existsByCategoryName(categoryRequest.getCategoryName()))
            throw new CategoryException("There is alerdy category with same name!");

        categoryRepository.save(Category
                                .builder()
                                .categoryName(categoryRequest.getCategoryName())
                                .build());
    }

    @Transactional
    @Override
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) throws CategoryException {
        Category category = categoryRepository.findById(updateCategoryRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id" + updateCategoryRequest.getId() + " not found!"));

        if(categoryRepository.existsByCategoryName(updateCategoryRequest.getCategoryName()))
            throw new CategoryException("There is alerdy category with same name!");

        category.setCategoryName(updateCategoryRequest.getCategoryName());
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategoryById(int id) {
        if(!categoryRepository.existsById(id))
            throw new ResourceNotFoundException("Category with id " + id + " not found!");

        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return   categoryRepository.findAll()
                                    .stream()
                                    .map(categoryMapper::categoryToDto)
                                    .collect(Collectors.toList());
    }

    @Override
    public Category findById(Integer categoryId) {
        return  categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
    }
}
