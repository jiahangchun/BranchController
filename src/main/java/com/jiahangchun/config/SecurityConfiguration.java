package com.jiahangchun.config;

import com.jiahangchun.filter.IpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/9 下午11:04
 **/
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


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
//                and().exceptionHandling().accessDeniedPage("/accessDenied").
                and().rememberMe().tokenRepository(persistentTokenRepository()).
                and().addFilterBefore(ipFilter(), FilterSecurityInterceptor.class).//过滤器拦截
                csrf().disable();
    }


    /**
     * 可持久化的cookie token服务
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
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
     * <p>
     * -- ----------------------------
     * -- Table structure for authorities
     * -- ----------------------------
     * DROP TABLE IF EXISTS `authorities`;
     * CREATE TABLE `authorities` (
     * `username` varchar(50) NOT NULL,
     * `authority` varchar(50) NOT NULL,
     * UNIQUE KEY `ix_auth_username` (`username`,`authority`),
     * CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     * <p>
     * -- ----------------------------
     * -- Records of authorities
     * -- ----------------------------
     * INSERT INTO `authorities` VALUES ('admin', 'ROLE_ADMIN');
     * INSERT INTO `authorities` VALUES ('admin', 'ROLE_USER');
     * INSERT INTO `authorities` VALUES ('user', 'ROLE_USER');
     * <p>
     * -- ----------------------------
     * -- Table structure for users
     * -- ----------------------------
     * DROP TABLE IF EXISTS `users`;
     * CREATE TABLE `users` (
     * `username` varchar(50) NOT NULL,
     * `password` varchar(500) NOT NULL,
     * `enabled` tinyint(1) NOT NULL,
     * PRIMARY KEY (`username`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     * <p>
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
                .passwordEncoder(passwordEncoder())//启用密码加密功能
                .dataSource(dataSource);
    }

    /**
     * 密码加密算法
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取默认创建的UserDetailsService，开启分组功能，关闭用户直接授权功能，并发布为Spring Bean
     *
     * @param auth
     * @return
     */
    @Bean
    @Autowired
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) {
        UserDetailsService userDetailsService = auth.getDefaultUserDetailsService();
        if (JdbcUserDetailsManager.class.isInstance(userDetailsService)) {
            JdbcUserDetailsManager jdbcUserDetailsManager = (JdbcUserDetailsManager) userDetailsService;
            jdbcUserDetailsManager.setEnableGroups(true);//开启分组功能
            jdbcUserDetailsManager.setEnableAuthorities(false);//关闭用户直接获取权限功能
        }
        return userDetailsService;
    }


    /**
     * 加密密码
     * shengcheng
     *
     * @param args
     */
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        System.out.println(encodedPassword);
    }


    /**
     * \
     * 配置具体的ip限制策略
     *
     * @return
     */
    private Filter ipFilter() {
        List<String> ipAddresses = new ArrayList<>();
        ipAddresses.add("0:0:0:0:0:0:0:1");//localhost
        IpFilter ipFilter = new IpFilter();
        ipFilter.setTargetRole("ROLE_ADMIN");
        ipFilter.setTargetRole("ROLE_USER");
        ipFilter.setAuthorizedIpAddresses(ipAddresses);
        return ipFilter;
    }

}