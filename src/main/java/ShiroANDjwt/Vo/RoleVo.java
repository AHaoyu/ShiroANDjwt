package ShiroANDjwt.Vo;

import ShiroANDjwt.Dto.RoleDto;

import java.util.List;

public class RoleVo {
    private long RoleId;
    private String RoleName;
    private List<PermissionVo> PermissionVoList;

    public RoleVo(RoleDto role) {
        this.RoleId = role.getRoleId();
        this.RoleName = role.getRoleName();
    }

    public List<PermissionVo> getPermissionVoList() {
        return PermissionVoList;
    }

    public void setPermissionVoList(List<PermissionVo> permissionVoList) {
        PermissionVoList = permissionVoList;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
