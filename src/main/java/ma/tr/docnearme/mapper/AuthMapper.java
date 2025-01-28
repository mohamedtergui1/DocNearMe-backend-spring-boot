package ma.tr.docnearme.mapper;

import ma.tr.docnearme.domain.entity.User;
import ma.tr.docnearme.dto.auth.LoginRequest;
import ma.tr.docnearme.dto.auth.RegisterRequest;
import ma.tr.docnearme.dto.user.UserDtoRequest;
import ma.tr.docnearme.dto.user.UserDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toEntity(LoginRequest loginRequest);

    User toEntity(RegisterRequest registerRequest);

    UserDtoResponse toDto(User user);

    User toEntity(UserDtoRequest userDtoRequest);
}
