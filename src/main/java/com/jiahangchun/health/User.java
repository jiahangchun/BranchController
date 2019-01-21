package com.jiahangchun.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author chunchun
 * @descritpion 第一种实现方式
 * @date Created at 2019/1/17 上午10:09
 **/
@Component
public class User implements HealthIndicator {
    /**
     * user监控 访问: http://localhost:8088/actour/health
     *
     * @return 自定义Health监控
     */
    @Override
    public Health health() {
        return new Health.Builder().withDetail("usercount", 10).withDetail("userstatus", "up").up().build();
    }
}