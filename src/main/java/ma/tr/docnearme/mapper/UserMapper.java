package ma.tr.docnearme.mapper;

import ma.tr.docnearme.domain.entity.User;
import ma.tr.docnearme.dto.auth.LoginRequest;
import ma.tr.docnearme.dto.auth.RegisterRequest;
import ma.tr.docnearme.dto.auth.UpdateUserRequest;
import ma.tr.docnearme.dto.auth.UserDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {



    User toRegisterRequest(LoginRequest loginRequest);

    User toRegisterRequest(RegisterRequest registerRequest);

    User toRegisterRequest(UpdateUserRequest updateUserRequest);

    UserDtoResponse toDto(User user);

}
