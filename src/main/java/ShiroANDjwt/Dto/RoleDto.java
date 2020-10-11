package ShiroANDjwt.Dto;

public class RoleDto {
    private long RoleId;
    private long UserId;

    public long getRoleId() {
        return RoleId;
    }

    public void setRoleId(long roleId) {
        RoleId = roleId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
