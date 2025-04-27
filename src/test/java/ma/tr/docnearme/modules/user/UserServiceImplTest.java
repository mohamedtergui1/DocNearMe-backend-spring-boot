package ma.tr.docnearme.modules.user;

import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.auth.AuthMapper;
import ma.tr.docnearme.modules.auth.LoginRequest;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDtoRequest userDtoRequest;
    private UserDtoResponse userDtoResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(UserRole.PATIENT);
        user.setPhoneNumber("066491402");

        // Use the record constructor to create DTO instances
        userDtoRequest = new UserDtoRequest("John Doe", "john.doe@example.com", "password", UserRole.PATIENT, "066491402");
        userDtoResponse = new UserDtoResponse(user.getId(), "John Doe", "john.doe@example.com", UserRole.PATIENT, "066491402");
    }

    @Test
    void getUsers_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(authMapper.toDto(user)).thenReturn(userDtoResponse);

        // Act
        List<UserDtoResponse> result = userService.getUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDtoResponse, result.get(0));
        verify(userRepository, times(1)).findAll();
        verify(authMapper, times(1)).toDto(user);
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        UUID userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authMapper.toDto(user)).thenReturn(userDtoResponse);

        // Act
        UserDtoResponse result = userService.getUserById(userId);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(userDtoResponse, result),
                () -> assertEquals("066491402", result.phoneNumber()) // Verify phoneNumber
        );
        verify(userRepository, times(1)).findById(userId);
        verify(authMapper, times(1)).toDto(user);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(authMapper, never()).toDto(any());
    }

    @Test
    void createUser_ShouldSaveAndReturnUser() {
        // Arrange
        when(authMapper.toEntity(userDtoRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(authMapper.toDto(user)).thenReturn(userDtoResponse);

        // Act
        UserDtoResponse result = userService.createUser(userDtoRequest);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(userDtoResponse, result),
                () -> assertEquals("066491402", result.phoneNumber()) // Verify phoneNumber
        );
        verify(authMapper, times(1)).toEntity(userDtoRequest);
        verify(userRepository, times(1)).save(user);
        verify(authMapper, times(1)).toDto(user);
    }

//    @Test
//    void updateUser_WhenUserExists_ShouldUpdateAndReturnUser() {
//        // Arrange
//        UUID userId = user.getId();
//        when(userRepository.existsById(userId)).thenReturn(true);
//        when(authMapper.toEntity(userDtoRequest)).thenReturn(user);
//        when(userRepository.save(user)).thenReturn(user);
//        when(authMapper.toDto(user)).thenReturn(userDtoResponse);
//
//        // Act
//        UserDtoResponse result = userService.updateUser(userId, userDtoRequest);
//
//        // Assert
//        assertAll(
//                () -> assertNotNull(result),
//                () -> assertEquals(userDtoResponse, result),
//                () -> assertEquals("066491402", result.phoneNumber()) // Verify phoneNumber
//        );
//        verify(userRepository, times(1)).existsById(userId);
//        verify(authMapper, times(1)).toEntity(userDtoRequest);
//        verify(userRepository, times(1)).save(user);
//        verify(authMapper, times(1)).toDto(user);
//    }
////
//    @Test
//    void updateUser_WhenUserDoesNotExist_ShouldThrowProcessNotCompletedException() {
//        // Arrange
//        UUID userId = UUID.randomUUID();
//        when(userRepository.existsById(userId)).thenReturn(false);
//
//        // Act & Assert
//        assertThrows(ProcessNotCompletedException.class, () -> userService.updateUser(userId, userDtoRequest));
//        verify(userRepository, times(1)).existsById(userId);
//        verify(authMapper, never()).toEntity((LoginRequest) any());
//        verify(userRepository, never()).save(any());
//        verify(authMapper, never()).toDto(any());
//    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        // Arrange
        UUID userId = user.getId();
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowProcessNotCompletedException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProcessNotCompletedException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}