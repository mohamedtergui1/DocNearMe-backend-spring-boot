package ma.tr.docnearme.modules.category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category category);
    Category categoryRequestToCategoryRequest(CategoryRequest categoryRequest);
}
