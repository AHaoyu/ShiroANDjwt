package ShiroANDjwt.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserQuery {
    @JsonProperty(value = "password")
    private String LoginPWD;

    @JsonProperty(value = "name")
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
