package ma.tr.docnearme.service.user;

import ma.tr.docnearme.dto.user.UserDtoRequest;
import ma.tr.docnearme.dto.user.UserDtoResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDtoResponse> getUsers();
    UserDtoResponse getUserById(UUID id);
    UserDtoResponse createUser(UserDtoRequest user);
    UserDtoResponse updateUser(UUID id, UserDtoRequest user);
    void deleteUser(UUID id);
}
