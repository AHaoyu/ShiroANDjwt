package ShiroANDjwt.Vo;

import org.apache.shiro.authc.AuthenticationToken;

public class AuthTokenVo implements AuthenticationToken {
    private String token;
    public AuthTokenVo(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
