package budkevych.squareapi.dto.mapper;

import budkevych.squareapi.dto.response.RoleResponseDto;
import budkevych.squareapi.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponseDto mapToDto(UserRole role) {
        RoleResponseDto responseDto = new RoleResponseDto();
        responseDto.setId(role.getId());
        responseDto.setName(role.getRoleName().name());
        return responseDto;
    }
}
