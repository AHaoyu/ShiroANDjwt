package ShiroANDjwt.Configuration;


import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import javax.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.httpserver.AuthFilter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);


    @Value("${jwt.auth}")
    private boolean auth;

    //配置shiro过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //启用认证
        String openAuth = auth ? "auth" : "anon";

        /*
        setFilters:
            作用是添加自己定义实现的过滤器类， 这里只有AuthFilter，它并取名为auth
            这些加入的自定义过滤器可以在下面的自定义url对应权限规则的kv映射中被使用成v
        */
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new AuthFilter());
        shiroFilterFactoryBean.setFilters(filters);

        /*
        setsetFilterChainDefinitionMap:
            作用是自定义url与权限规则的映射,即url与过滤器的映射;
            anon是shiro自带的匿名过滤器，说明任何人都可以访问;
            openAuth根据上面的三元运算条件决定是自带的anno还是我们自定义的过滤器auth（AuthFilter）
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/login", "anon");
        filterMap.put("user/fecthCurrentUser", "anon");
        filterMap.put("/**", openAuth);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        logger.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;

    }

    // SecurityManager 安全管理器；Shiro的核心
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    //自定义身份认证realm
    @Bean
    public AuthRealm userRealm() {
        return AuthRealm();
    }

    //管理shiro生命周期
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //Shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
