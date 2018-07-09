package com.jiahangchun.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/9 下午11:04
 **/
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Description：配置Spring Security
     * 1.开启对任何地址（"/**"）的访问控制，要求必须具备"ROLE_USER"的角色
     * 2.开启默认form表单形式的用户登入，访问地址为"/login"，登录成功后自动跳转到用户前一次的访问地址
     * 3.关闭csrf限制，该功能以后再讲，默认为开启状态<br>
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                antMatchers("/test/**").permitAll().
//                antMatchers("/assets/**").permitAll().
                antMatchers("/user/**").hasRole("USER").
//                and().formLogin().loginPage("/login.jsp").permitAll().loginProcessingUrl("/login").
                and().formLogin().
                and().logout().permitAll().
                and().rememberMe().
                and().csrf().disable();
    }
}