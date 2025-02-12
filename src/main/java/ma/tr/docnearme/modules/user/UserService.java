package ma.tr.docnearme.modules.user;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDtoResponse> getUsers();
    UserDtoResponse getUserById(UUID id);
    UserDtoResponse createUser(UserDtoRequest user);
    UserDtoResponse updateUser(UUID id, UserDtoRequest user);
    void deleteUser(UUID id);
}
