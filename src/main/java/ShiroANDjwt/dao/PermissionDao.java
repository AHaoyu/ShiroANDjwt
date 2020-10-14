package ShiroANDjwt.dao;

import ShiroANDjwt.Dto.PermissionDto;
import ShiroANDjwt.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionDao {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<PermissionDto> qryPermissionByRoleIds(List<Long> roleIds) {
        List<PermissionDto> permissionDtos = new ArrayList<>();
        List<PermissionDto> permissionDto;
        for(long roleId : roleIds) {
            permissionDto = permissionMapper.qryPermissionByRoleId(roleId);
            permissionDtos.addAll(permissionDto);
        }
        return permissionDtos;
    }
}
