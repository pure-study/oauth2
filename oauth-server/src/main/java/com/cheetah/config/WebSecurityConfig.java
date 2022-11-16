package com.cheetah.config;

import com.cheetah.handle.CustomAuthenticationFailureHandler;
import com.cheetah.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ISysUserService userService;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 指定认证对象的来源和加密方式
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    /**
     * 安全拦截机制（最重要）
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //CSRF禁用，因为不使用session
                .csrf().disable()
                .authorizeRequests()
                //登录接口和静态资源不需要认证
                .antMatchers("/login*","/css/*").permitAll()
                //除上面的所有请求全部需要认证通过才能访问
                .anyRequest().authenticated()
                //返回HttpSecurity以进行进一步的自定义,证明是一次新的配置的开始
                .and()
                .formLogin()
                //如果未指定此页面，则会跳转到默认页面
//                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .permitAll()
                //认证失败处理类
                .failureHandler(customAuthenticationFailureHandler);
    }

    /**
     * AuthenticationManager 对象在OAuth2.0认证服务中要使用，提前放入IOC容器中
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
