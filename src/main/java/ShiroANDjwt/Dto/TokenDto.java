package ShiroANDjwt.Dto;

import java.util.Date;

public class TokenDto {
    private String Token;
    private Long UserId;
    private Date UpdateTime;
    private Date ExpireTime;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    public Date getExpireTime() {
        return ExpireTime;
    }

    public void setExpireTime(Date expireTime) {
        ExpireTime = expireTime;
    }
}
