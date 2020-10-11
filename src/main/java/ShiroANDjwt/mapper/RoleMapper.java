package ShiroANDjwt.mapper;

import ShiroANDjwt.Dto.RoleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    RoleDto qryRoleByUserId(long userId);
}
