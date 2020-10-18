package ShiroANDjwt.controller;

import ShiroANDjwt.JWT.JwtAuthenticator;
import ShiroANDjwt.Shiro.AuthConstant;
import ShiroANDjwt.Utils.ResultUtils;
import ShiroANDjwt.pojo.Result;
import ShiroANDjwt.pojo.UserQuery;
import ShiroANDjwt.Vo.UserVo;
import ShiroANDjwt.service.UserServiceImpl.UserServiceImpl;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.apache.shiro.SecurityUtils.getSubject;

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
        List<UserVo> userVo = userService.qryUserByUserName(userQuery.getUserName());
        if(userVo.size() == 0){
            //账号不存在
            return ResultUtils.dataNotFoundError(AuthConstant.UNKNOWN_ACCOUNT);
        }else if(!userVo.get(0).getLoginPWD().equals(userQuery.getLoginPWD())){
            //密码错误
            return ResultUtils.error(AuthConstant.WRONG_PASSWORD);
        }else{
            //通过认证, 生成签名
            String token = userService.creatToken(userVo.get(0));
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
        //String token = JwtAuthenticator.getRequestToken((HttpServletRequest)request);
        //String userName = JwtAuthenticator.getUsername(token);
        //List<UserVo> userVo = userService.qryUserByUserName(userName);
        //userService.deleteToken(userVo.get(0));
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
        System.out.println(token);
        String userName = JwtAuthenticator.getUsername(token);
        List<UserVo> userVo = userService.qryUserByUserName(userName);
        if(userVo.size() <= 0){
            return ResultUtils.dataNotFoundError(AuthConstant.UNKNOWN_ACCOUNT);
        }
        return ResultUtils.success(userVo.get(0));
    }

    //This is only for database query test, returns user obj with his roles and permissions
    @GetMapping("/getUser")
    public Result getUser(@RequestParam("name") String userName, @RequestParam("pwd") String loginPWD) {
        List<UserVo> userVo = userService.login(new UserQuery(userName, loginPWD));
        if(userVo.size() <= 0){
            return ResultUtils.dataNotFoundError(AuthConstant.UNKNOWN_ACCOUNT);
        }
        return ResultUtils.success(userVo.get(0));
    }

    /**
     * 以方法的方式通过Realm查询当前用户角色或权限，进行授权访问
     * (调用Realm中的doGetAuthorization方法并将cookie中JWTtoken的authorization的值传入
     * @param
     * @return
     */
    @PostMapping("/forAdmin")
    public Result forAdmin() {
        if(getSubject().hasRole("admin")) {
            return ResultUtils.success("Welcom! my boss~");
        } else {
            return ResultUtils.forbiddenError("let op! Unauthorized account!!");
        }
    }

    /**
     * 这种以注解方式通过Realm查询当前用户角色或权限是无效的，why？
     * @param
     * @return
     */
    @PostMapping("/alsoForAdmin")
    @RequiresRoles("admin")
    public Result AlsoforAdmin() {
        return ResultUtils.success("Welcom! my boss~");
    }



}
