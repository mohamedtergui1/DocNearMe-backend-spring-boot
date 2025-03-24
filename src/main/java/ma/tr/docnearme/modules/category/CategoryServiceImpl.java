package ma.tr.docnearme.modules.category;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategory(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryToCategoryResponse)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new ProcessNotCompletedException("Category name already exists");
        }

        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        category.setId(id);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}