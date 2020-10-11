package ShiroANDjwt.mapper;

import ShiroANDjwt.Dto.PermissionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionDto qryPermissionByRoleId(long roleId);
}
