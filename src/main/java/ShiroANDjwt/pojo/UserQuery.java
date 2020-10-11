package ShiroANDjwt.pojo;

public class UserQuery {
    private String LoginPWD;
    private String UserName;

    public UserQuery(String userName, String loginPWD) {
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
}
