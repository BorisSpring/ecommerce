package main.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.model.CategoryDTO;
import main.requests.CreateCategoryRequest;
import main.requests.UpdateCategoryRequest;
import main.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAllCategories(){
        return  ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@RequestParam Integer categoryId){
        categoryService.deleteCategoryById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        categoryService.addCategory(createCategoryRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest){
        categoryService.updateCategory(updateCategoryRequest);
    }

}
