package com.jiahangchun.config;

import com.jiahangchun.tool.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/3 下午8:39
 **/
@Service
@Slf4j
public class WebSecurity {

    public boolean check(Authentication authentication, HttpServletRequest request) {
        List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) authentication.getAuthorities();
        String servletPath = request.getServletPath();
        List<String> grantedAuthorities = grantedAuthorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        log.info("need authority :{}", genPermission(servletPath));
        log.info("has authority:{} ", JsonUtil.toJson(grantedAuthorities));

        int index = grantedAuthorities.indexOf(genPermission(servletPath));
        return index >= 0;
    }

    /**
     * 根据请求路径生成对应的权限名称
     *
     * @return
     */
    public static String genPermission(String abstractServletPath) {
        return abstractServletPath;
    }
}
