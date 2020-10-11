package ShiroANDjwt.controller;

import ShiroANDjwt.JWT.JwtAuthenticator;
import ShiroANDjwt.Shiro.AuthConstant;
import ShiroANDjwt.Utils.ResultUtils;
import ShiroANDjwt.pojo.Result;
import ShiroANDjwt.pojo.UserQuery;
import ShiroANDjwt.Vo.UserVo;
import ShiroANDjwt.service.UserServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录
     * @param userQuery
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserQuery userQuery, ServletResponse response) throws Exception{
        List<UserVo> userVo = userService.login(userQuery);
        if(userVo.size() == 0){
            //账号不存在
            return ResultUtils.dataNotFoundError(AuthConstant.UNKNOWN_ACCOUNT);
        }else if(!userVo.get(0).getLoginPWD().equals(userQuery.getLoginPWD())){
            //密码错误
            return ResultUtils.error(AuthConstant.WRONG_PASSWORD);
        }else{
            //通过认证, 生成签名
            String token = userService.saveToken(userVo.get(0));
            //token写入前端cookie
            JwtAuthenticator.editCookieToken(response, token);
            return ResultUtils.success(userVo);
        }
    }

    /**
     * 注销
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public Result logout(ServletRequest request, ServletResponse response){
        String token = JwtAuthenticator.getRequestToken((HttpServletRequest)request);
        String userName = JwtAuthenticator.getUsername(token);
        List<UserVo> userVo = userService.qryUserByUserName(userName);
        userService.deleteToken(userVo.get(0));
        //前端token置空
        JwtAuthenticator.editCookieToken(response, "");
        return ResultUtils.success();
    }

    /**
     * 查询当前用户，通过token解密查询
     * @param request
     * @return
     */
    @PostMapping("/fetchCurrentUser")
    public Result fetchCurrentUser(ServletRequest request){
        String token = JwtAuthenticator.getRequestToken((HttpServletRequest)request);
        String userName = JwtAuthenticator.getUsername(token);
        List<UserVo> userVo = userService.qryUserByUserName(userName);
        if(userVo.size() <= 0){
            return ResultUtils.dataNotFoundError(AuthConstant.UNKNOWN_ACCOUNT);
        }
        return ResultUtils.success(userVo.get(0));
    }
}
