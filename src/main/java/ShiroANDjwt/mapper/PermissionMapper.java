package ShiroANDjwt.mapper;

import ShiroANDjwt.Dto.PermissionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<PermissionDto> qryPermissionByRoleId(long roleId);
}
