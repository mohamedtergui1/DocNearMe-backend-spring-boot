package ma.tr.docnearme.modules.category;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategory(int id) {
        return null;
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new ProcessNotCompletedException("the name already used");
        }
        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(categoryMapper.categoryRequestToCategoryRequest(categoryRequest)));
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, UUID id) {
        return null;
    }
}
