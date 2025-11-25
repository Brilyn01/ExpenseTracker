package application.expensetracker.Controller;


import application.expensetracker.Dto.CategoryDto;
import application.expensetracker.entity.User;
import application.expensetracker.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }


    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto request) {
        User user = getCurrentUser();
        CategoryDto created = categoryService.createCategory(request, user);
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> list() {
        User user = getCurrentUser();
        List<CategoryDto> categories = categoryService.getAllCategories(user);
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto request) {
        User user = getCurrentUser();
        CategoryDto updated = categoryService.updateCategory(id, request, user);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = getCurrentUser();
        categoryService.deleteCategory(id, user);
        return ResponseEntity.noContent().build();
    }
}
