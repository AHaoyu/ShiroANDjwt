package ShiroANDjwt.Dto;

public class UserDto {
    private long UserId;
    private String LoginPWD;
    private String UserName;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
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
}
