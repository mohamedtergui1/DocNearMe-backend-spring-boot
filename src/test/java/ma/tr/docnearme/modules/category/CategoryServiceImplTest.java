package ma.tr.docnearme.modules.category;

import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private UUID categoryId;
    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();
        category = new Category(categoryId, "Test Category", "Test Description");
        categoryRequest = new CategoryRequest("Test Category", "Test Description");
        categoryResponse = new CategoryResponse(categoryId, "Test Category", "Test Description");
    }

    @Test
    void getCategories_ShouldReturnListOfCategoryResponses() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        List<CategoryResponse> result = categoryService.getCategories();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(categoryResponse, result.get(0));
        verify(categoryRepository).findAll();
        verify(categoryMapper).categoryToCategoryResponse(category);
    }

    @Test
    void getCategory_WithValidId_ShouldReturnCategoryResponse() {
        // Arrange
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        CategoryResponse result = categoryService.getCategory(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryResponse, result);
        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).categoryToCategoryResponse(category);
    }

    @Test
    void getCategory_WithInvalidId_ShouldThrowNotFoundException() {
        // Arrange
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> categoryService.getCategory(categoryId));
        verify(categoryRepository).findById(categoryId);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    void addCategory_WithNewName_ShouldReturnSavedCategoryResponse() {
        // Arrange
        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(false);
        when(categoryMapper.categoryRequestToCategory(categoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        CategoryResponse result = categoryService.addCategory(categoryRequest);

        // Assert
        assertNotNull(result);
        assertEquals(categoryResponse, result);
        verify(categoryRepository).existsByName(categoryRequest.name());
        verify(categoryMapper).categoryRequestToCategory(categoryRequest);
        verify(categoryRepository).save(category);
        verify(categoryMapper).categoryToCategoryResponse(category);
    }

    @Test
    void addCategory_WithExistingName_ShouldThrowProcessNotCompletedException() {
        // Arrange
        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(true);

        // Act & Assert
        assertThrows(ProcessNotCompletedException.class, () -> categoryService.addCategory(categoryRequest));
        verify(categoryRepository).existsByName(categoryRequest.name());
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    void updateCategory_WithValidId_ShouldReturnUpdatedCategoryResponse() {
        // Arrange
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryMapper.categoryRequestToCategory(categoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        CategoryResponse result = categoryService.updateCategory(categoryRequest, categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryResponse, result);
        verify(categoryRepository).existsById(categoryId);
        verify(categoryMapper).categoryRequestToCategory(categoryRequest);
        verify(categoryRepository).save(category);
        verify(categoryMapper).categoryToCategoryResponse(category);
    }

    @Test
    void updateCategory_WithInvalidId_ShouldThrowNotFoundException() {
        // Arrange
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                categoryService.updateCategory(categoryRequest, categoryId));
        verify(categoryRepository).existsById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    void deleteCategory_WithValidId_ShouldDeleteCategory() {
        // Arrange
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void deleteCategory_WithInvalidId_ShouldThrowNotFoundException() {
        // Arrange
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(categoryId));
        verify(categoryRepository).existsById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
    }
}