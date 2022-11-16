package com.cheetah.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
@AllArgsConstructor
//@EnableConfigurationProperties({IgnoreProperties.class})
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启Security内置的动态配置
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 该对象是在配置文件中配置datasource时注入到Spring IOC中的
     */
    private final DataSource dataSource;

//    private final IgnoreProperties properties;

    /**
     * 指定token的持久化策略
     * InMemoryTokenStore表示将token存储在内存中
     * RedisTokenStore表示将token存储在redis中
     * JdbcTokenStore表示将token存储在数据库中
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 指定当前资源的id和token的存储策略
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("product_api").tokenStore(jdbcTokenStore());
    }


    /**
     * 设置请求权限和header处理
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //固定写法
        http.authorizeRequests()
                //指定不同请求方式访问资源所需的权限，一般查询是read，其余都是write
                .antMatchers(HttpMethod.GET,"/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST,"/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH,"/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT,"/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE,"/**").access("#oauth2.hasScope('write')")
                .and()
                .headers().addHeaderWriter((request,response) -> {
                    //域名不同或者子域名不一样并且是ajax请求就会出现跨域问题
                    //允许跨域
                    response.addHeader("Access-Control-Allow-Origin","*");
                    //跨域中会出现预检请求，如果不能通过，则真正请求也不会发出
                    //如果是跨域的预检请求，则原封不动向下传递请求头信息，否则预检请求会丢失请求头信息（主要是token信息）
                    if(request.getMethod().equals("OPTIONS")){
                        response.setHeader("Access-Control-Allow-Methods",request.getHeader("Access-Control-Allow-Methods"));
                        response.setHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Allow-Headers"));
                    }
        });

        //项目中的代码
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
//                .ExpressionInterceptUrlRegistry config = http.requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests();
//        properties.getUrls().forEach(e -> {
//            config.antMatchers(e).permitAll();
//        });
    }
}
