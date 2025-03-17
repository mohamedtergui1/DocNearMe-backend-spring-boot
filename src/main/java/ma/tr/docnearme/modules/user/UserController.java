package ma.tr.docnearme.modules.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUser(@PathVariable UUID id) {
        UserDtoResponse user = userService.getUserById(id);
        ApiResponse<UserDtoResponse> response = ApiResponse.<UserDtoResponse>builder()
                .message("User retrieved successfully")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDtoResponse>>> getAllUsers() {
        List<UserDtoResponse> users = userService.getUsers();
        ApiResponse<List<UserDtoResponse>> response = ApiResponse.<List<UserDtoResponse>>builder()
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDtoResponse>> createUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        UserDtoResponse createdUser = userService.createUser(userDtoRequest);
        ApiResponse<UserDtoResponse> response = ApiResponse.<UserDtoResponse>builder()
                .message("User created successfully")
                .data(createdUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDtoResponse>> updateUser(@RequestBody @Valid UserDtoRequest userDtoRequest, @PathVariable UUID id) {
        UserDtoResponse updatedUser = userService.updateUser(id, userDtoRequest);
        ApiResponse<UserDtoResponse> response = ApiResponse.<UserDtoResponse>builder()
                .message("User updated successfully")
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("User deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}