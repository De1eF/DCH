package budkevych.squareapi.dto.mapper;

import budkevych.squareapi.dto.request.UserRequestDto;
import budkevych.squareapi.dto.response.RoleResponseDto;
import budkevych.squareapi.dto.response.UserResponseDto;
import budkevych.squareapi.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        List<RoleResponseDto> roles = user.getRoles()
                .stream()
                .map(roleMapper::mapToDto)
                .toList();
        userResponseDto.setRoles(roles);
        return userResponseDto;
    }

    public User mapToModel(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getLogin());
        user.setPassword(requestDto.getPassword());
        return user;
    }
}
