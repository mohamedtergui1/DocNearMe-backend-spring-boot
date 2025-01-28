package ma.tr.docnearme.service.user;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.domain.entity.User;
import ma.tr.docnearme.dto.user.UserDtoRequest;
import ma.tr.docnearme.dto.user.UserDtoResponse;
import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.mapper.AuthMapper;
import ma.tr.docnearme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    @Override
    public List<UserDtoResponse> getUsers() {
        return userRepository.findAll().stream().map(authMapper::toDto).toList();
    }

    @Override
    public UserDtoResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return authMapper.toDto(user);
    }

    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {
        User newUser = authMapper.toEntity(userDtoRequest);
        newUser = userRepository.save(newUser);
        return authMapper.toDto(newUser);
    }

    @Override
    public UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest) {
        if (!userRepository.existsById(id)) {
            throw new ProcessNotCompletedException("User with id " + id + " not found");
        }
        User editUser = authMapper.toEntity(userDtoRequest);
        editUser.setId(id);
        editUser = userRepository.save(editUser);
        return authMapper.toDto(editUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ProcessNotCompletedException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
