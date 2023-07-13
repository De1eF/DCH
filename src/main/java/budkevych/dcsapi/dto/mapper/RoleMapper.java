package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.response.RoleResponseDto;
import budkevych.dcsapi.model.UserRole;
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
