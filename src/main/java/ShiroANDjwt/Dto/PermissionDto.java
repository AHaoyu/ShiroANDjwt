package ShiroANDjwt.Dto;

public class PermissionDto {
    private long RoleId;
    private long PermissionId;
    private String PermissionName;

    public long getRoleId() {
        return RoleId;
    }

    public void setRoleId(long roleId) {
        RoleId = roleId;
    }

    public long getPermissionId() {
        return PermissionId;
    }

    public void setPermissionId(long permissionId) {
        PermissionId = permissionId;
    }

    public String getPermissionName() {
        return PermissionName;
    }

    public void setPermissionName(String permissionName) {
        PermissionName = permissionName;
    }
}
