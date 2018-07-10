package com.jiahangchun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

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


//    /**
//     * 定义用户信息获取服务
//     * 这里使用内存方式获取用户信息，并添加了一个用户
//     *
//     * @return
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
//        return manager;
//    }

    /**
     * SET FOREIGN_KEY_CHECKS=0;
     *
     * -- ----------------------------
     * -- Table structure for authorities
     * -- ----------------------------
     * DROP TABLE IF EXISTS `authorities`;
     * CREATE TABLE `authorities` (
     *   `username` varchar(50) NOT NULL,
     *   `authority` varchar(50) NOT NULL,
     *   UNIQUE KEY `ix_auth_username` (`username`,`authority`),
     *   CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     *
     * -- ----------------------------
     * -- Records of authorities
     * -- ----------------------------
     * INSERT INTO `authorities` VALUES ('admin', 'ROLE_ADMIN');
     * INSERT INTO `authorities` VALUES ('admin', 'ROLE_USER');
     * INSERT INTO `authorities` VALUES ('user', 'ROLE_USER');
     *
     * -- ----------------------------
     * -- Table structure for users
     * -- ----------------------------
     * DROP TABLE IF EXISTS `users`;
     * CREATE TABLE `users` (
     *   `username` varchar(50) NOT NULL,
     *   `password` varchar(500) NOT NULL,
     *   `enabled` tinyint(1) NOT NULL,
     *   PRIMARY KEY (`username`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     *
     * -- ----------------------------
     * -- Records of users
     * -- ----------------------------
     * INSERT INTO `users` VALUES ('admin', 'password', '1');
     * INSERT INTO `users` VALUES ('user', 'password', '1');
     * SET FOREIGN_KEY_CHECKS=1;
     *
     * @param auth
     * @param dataSource
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
//        classpath:org/springframework/security/core/userdetails/jdbc/users.ddl
//        默认的数据库配置

        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

}