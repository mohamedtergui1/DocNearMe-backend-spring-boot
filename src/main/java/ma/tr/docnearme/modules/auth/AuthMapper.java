package ma.tr.docnearme.modules.auth;

import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserDtoRequest;
import ma.tr.docnearme.modules.user.UserDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toEntity(LoginRequest loginRequest);

    User toEntity(RegisterRequest registerRequest);

    UserDtoResponse toDto(User user);

    User toEntity(UserDtoRequest userDtoRequest);
}
