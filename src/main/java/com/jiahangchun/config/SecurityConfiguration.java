package com.jiahangchun.config;

import com.jiahangchun.user.MyJdbcUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/29 下午7:17
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- register url
            "/users/signUp",//测试使用
            "/users/add",
            "/group/add",
            "/user_group/add",
            "/permission/add",
            "/group_permission/add",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };


    @Autowired
    private MyHolder myHolder;
    @Autowired
    private CustomAuthenticationFailHander customAuthenticationFailHander;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHander;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers("/user/1").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/users/signUp").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin().permitAll()

//                .antMatchers("/test/1").permitAll()

//                .antMatchers("/user/*").access("@webSecurity.check(authentication,request)")
        // 使其支持跨域
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .and().formLogin().permitAll()
//                .failureHandler(customAuthenticationFailHander)
//                .successHandler(customAuthenticationSuccessHander)


//                .and().logout().permitAll()
//                .and().rememberMe()
//                .and().csrf().disable()

        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/user/*").access("@webSecurity.check(authentication,request)")
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
//                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService()));
    }


    /**
     * 定义用户信息获取服务
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
//     内存方式的用户管理
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("name").password("pwd").roles("USER").build());
//        manager.createUser(User.withUsername("2").password("2").roles("测试用户1接口权限").build());
        MyJdbcUserDetailsManager manager = new MyJdbcUserDetailsManager();
        return manager;
    }
}
