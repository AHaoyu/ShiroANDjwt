package ShiroANDjwt.Vo;

public class PermissionVo {
    private long PermissionId;
    private String PermissionName;

    public String getPermissionName() {
        return PermissionName;
    }

    public void setPermissionName(String permissionName) {
        PermissionName = permissionName;
    }

    public long getPermissionId() {
        return PermissionId;
    }

    public void setPermissionId(long permissionId) {
        PermissionId = permissionId;
    }
}
