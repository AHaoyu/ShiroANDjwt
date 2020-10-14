package ShiroANDjwt.mapper;

import ShiroANDjwt.Dto.RoleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleDto> qryRoleByUserId(long userId);
}
