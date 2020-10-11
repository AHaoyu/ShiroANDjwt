package ShiroANDjwt.Vo;

import ShiroANDjwt.Dto.UserDto;

import java.util.List;

public class UserVo {
    private String LoginPWD;
    private String UserName;
    private long UserId;
    private List<RoleVo> RoleVoList;

    public UserVo(UserDto u) {
        this.UserId = u.getUserId();
    }

    public List<RoleVo> getRoleVoList() {
        return RoleVoList;
    }

    public void setRoleVoList(List<RoleVo> roleVoList) {
        RoleVoList = roleVoList;
    }

    public UserVo(String loginPWD, String userName) {
        this.LoginPWD = loginPWD;
        this.UserName = userName;
    }

    public String getLoginPWD() {
        return LoginPWD;
    }

    public void setLoginPWD(String loginPWD) {
        LoginPWD = loginPWD;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
