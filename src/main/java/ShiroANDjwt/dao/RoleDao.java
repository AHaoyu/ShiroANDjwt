package ShiroANDjwt.dao;

import ShiroANDjwt.Dto.RoleDto;
import ShiroANDjwt.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDao {

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleDto> qryRoleByUserIds(List<Long> userIds) {
        List<RoleDto> roleDtos = new ArrayList<>();
        for(long userId : userIds) {
            roleDtos.add(roleMapper.qryRoleByUserId(userId));
        }
        return roleDtos;
    }
}
