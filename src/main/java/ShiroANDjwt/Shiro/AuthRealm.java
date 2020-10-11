package ShiroANDjwt.Shiro;

import ShiroANDjwt.JWT.JwtAuthenticator;
import ShiroANDjwt.Utils.StringUtil;
import ShiroANDjwt.Vo.AuthTokenVo;
import ShiroANDjwt.Vo.PermissionVo;
import ShiroANDjwt.Vo.RoleVo;
import ShiroANDjwt.pojo.UserQuery;
import ShiroANDjwt.Vo.UserVo;
import ShiroANDjwt.service.UserServiceImpl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义安全数据Realm
 */

public class AuthRealm extends AuthorizingRealm {

    private static final transient Logger logger = LoggerFactory.getLogger(AuthRealm.class);
    @Autowired
    private UserServiceImpl userService;

    /**
     * 重写，绕过身份令牌异常导致的shiro报错
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken){
        return authenticationToken instanceof AuthTokenVo;
    }

    /**
     * 执行授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("用户角色权限认证");
        //获取用户登录信息
        UserVo userVo = (UserVo)principals.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for(RoleVo role : userVo.getRoleVoList()){
            authorizationInfo.addRole(role.getRoleName());
            for(PermissionVo permissionVo : role.getPermissionVoList()){
                authorizationInfo.addStringPermission(permissionVo.getPermissionName());
            }
        }
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑");
        //获得token
        String token = (String)authenticationToken.getCredentials();
        //获得token中的用户信息
        String user = JwtAuthenticator.getUsername(token);
        //判空
        if(StringUtil.isBlank(user)){
            throw new AuthenticationException(AuthConstant.TOKEN_BLANK);
        }
        try{
            //查询用户是否存在
            List<UserVo> userVo = userService.login(new UserQuery(user, null));
            if(userVo.size() <= 0){
                throw new AuthenticationException(AuthConstant.TOKEN_INVALID);
                //token过期
            }else if(!(JwtAuthenticator.verifyToken(token, user, userVo.get(0).getLoginPWD()))){
                throw new AuthenticationException(AuthConstant.TOKEN_EXPIRE);
            }
        }catch (Exception e){
            throw e;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                token, token, "auth_realm");
        return authenticationInfo;
    }
}
