package ShiroANDjwt.JWT;

import ShiroANDjwt.Shiro.AuthConstant;
import ShiroANDjwt.Vo.UserVo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;


import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class JwtAuthenticator {
    /**
     * 校验token是否正确
     * @param token
     * @param username
     * @param secret
     * @return
     */
    public static boolean verifyToken(String token, String username, String secret){
        // 根据密码生成JWT校验器
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",username)
                    .build();
            // 校验token，这里是jwt的内部实现，可能会抛出错误（token错误或过期等），同样将错误抛回给上层
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获得token中的用户信息，无需解密
     * @param token
     * @return
     */
    public static String getUsername(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch (JWTDecodeException e){
            return null;
        }
    }

    /**
     * 生成签名
     * @return
     */
    public static String sign(UserVo userVo, Date expireTime) {
        String secret = userVo.getLoginPWD();
        String userName = userVo.getUserName();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息和过期信息
        return JWT.create()
                .withClaim("username", userName)
                .withExpiresAt(expireTime)
                .sign(algorithm);

    }

    /**
     * 从cookie中获取token
     * @param httpServletRequest
     * @return
     */
    public static String getRequestToken(HttpServletRequest httpServletRequest){
        String token = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        //StringUtils su = new StringUtils();
        if(cookies != null){
            for(Cookie ck : cookies){
                if(ck.getName().equals(AuthConstant.COOKIE_TOKEN_NAME)){
                    token = ck.getValue();
                    break;
                }
            }
        }
        return token;
    }

    /**
     * 编辑浏览器cookie
     * @param response
     * @param tokenValue
     */
    public static void editCookieToken(ServletResponse response, String tokenValue){
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        Cookie cookie = new Cookie(AuthConstant.COOKIE_TOKEN_NAME, tokenValue);
        cookie.setPath("/");
        cookie.setHttpOnly(true);//前端不可读cookie
        //跨域向前端写cookie
        httpServletResponse.setHeader("Access-Control-Allow-Origin",
                httpServletResponse.getHeader("Origin"));
        httpServletResponse.addCookie(cookie);
    }

}
