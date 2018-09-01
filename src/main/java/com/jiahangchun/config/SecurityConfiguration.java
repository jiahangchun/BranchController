package com.jiahangchun.config;

import com.jiahangchun.user.MyJdbcUserDetailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/29 下午7:17
 **/
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyHolder myHolder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll()
                .and().rememberMe()
                .and().csrf().disable();

        /**
         * 针对不同的请求路径配置不同的权限
         */
        myHolder.queryMethodAndAuth().forEach((k, v) -> {
            try {
                System.out.println("路径[" + k + "]需要权限[" + v + "]");
                http.authorizeRequests().antMatchers(k).hasRole(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 定义用户信息获取服务
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("test").password("test").roles("USER").build());
//        manager.createUser(User.withUsername("2").password("2").roles("测试用户1接口权限").build());

        MyJdbcUserDetailManager manager=new MyJdbcUserDetailManager();
        return manager;
    }
}
