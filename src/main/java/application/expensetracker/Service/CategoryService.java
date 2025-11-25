package application.expensetracker.Service;



import application.expensetracker.Dto.CategoryDto;
import application.expensetracker.entity.Category;
import application.expensetracker.entity.User;
import application.expensetracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryDto dto, User user) {
        if (categoryRepository.existsByNameAndUser(dto.getName(), user)) {
            throw new IllegalArgumentException("You already have a category named '" + dto.getName() + "'");
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setUser(user);

        Category saved = categoryRepository.save(category);
        return convertToDto(saved);
    }

    public List<CategoryDto> getAllCategories(User user) {
        return categoryRepository.findByUser(user)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(Long id, CategoryDto dto, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user);

        if (!category.getName().equals(dto.getName()) &&
                categoryRepository.existsByNameAndUser(dto.getName(), user)) {
            throw new IllegalArgumentException("You already have a category named '" + dto.getName() + "'");
        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category updated = categoryRepository.save(category);
        return convertToDto(updated);
    }

    public void deleteCategory(Long id, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user);
        categoryRepository.delete(category);
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}