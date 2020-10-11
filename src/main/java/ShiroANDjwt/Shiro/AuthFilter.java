package ShiroANDjwt.Shiro;

import ShiroANDjwt.JWT.JwtAuthenticator;
import ShiroANDjwt.Utils.JsonConvertUtil;
import ShiroANDjwt.Utils.ResultUtils;
import ShiroANDjwt.Utils.StringUtil;
import ShiroANDjwt.Vo.AuthTokenVo;
import ShiroANDjwt.pojo.Result;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private Result responseResult = ResultUtils.forbiddenError(AuthConstant.AUTHENTICATE_FAIL);


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = JwtAuthenticator.getRequestToken((HttpServletRequest)request);
        if (!StringUtil.isBlank(token)){
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                // 应用异常
                logger.info(e.getMessage());
                responseResult = ResultUtils.forbiddenError(e.getMessage());
                return false;
            }
        } else {
            // cookie中未检查到token或token为空
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            String httpMethod = httpServletRequest.getMethod();
            String requestURI = httpServletRequest.getRequestURI();
            responseResult = ResultUtils.forbiddenError(AuthConstant.TOKEN_BLANK);
            logger.info("请求 {} 的Token为空 请求类型 {}", requestURI, httpMethod);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String result = JsonConvertUtil.objectToJson(responseResult);
        httpServletResponse.getWriter().print(result);
        return false;
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = JwtAuthenticator.getRequestToken((HttpServletRequest)request);
        AuthTokenVo jwtToken = new AuthTokenVo(token);
        // 提交给AuthRealm进行登录认证
        getSubject(request, response).login(jwtToken);
        return true;
    }

}
