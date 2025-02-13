package ma.tr.docnearme.modules.category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> getCategories();

    CategoryResponse getCategory(int id);

    CategoryResponse addCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(CategoryRequest categoryRequest, UUID id);
}
