package ShiroANDjwt.Shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private Result responseResult = ResultUtils.forbiddenError(AuthConstant.AUTHENTICATE_FAIL);


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
