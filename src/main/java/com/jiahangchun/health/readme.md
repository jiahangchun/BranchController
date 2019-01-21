# 访问
* ttp://localhost:9999/actuator/health

# 实现原理
* 自己先简单弄了一个实现，在上面断点，然后一步步向上跟踪，发现触发health()方法由JMX触发。
* 至于heath()触发的方法是在ReflectiveOperationInvoker被设置
* 其实主要的封装，寻找工作都是在EndPointDiscoverer类中被实现的，如何发现EndPointBean，如何转换成EndPointMethod
* 而这一系列的操作都是由创建JmxMBeanExporter时被触发
* 而创建JmxMBeanExporter的创建是由DefaultListableBeanFactory的preInstantiateSingletons方法实例化时被统一触发。一起被单例化的一共有392个，this.beanDefinitionNames


# 20190121
* 所有的需要被执行health()方法的类封装是由HealthEndpointConfiguration方法执行，就是生成一个对应的healthIndicator。其实就是简单地使用了@Bean注解，生成实例而已。
* 至于如何去找 还是交给了HealthIndicatorBeansComposite进行处理。其实它找HealthIndicator也是非常简单的
    * applicationContext.getBean(HealthAggregator.class); 这个其实也只放放在类里面，没实际执行掉的。
    * applicationContext.getBeansOfType(HealthIndicator.class);
* 以上的所有关于health的类被找齐全了，剩下的只有触发执行了。
    * EndpointBean.invoke
    * DefaultMBeanServerInterceptor.invoke
    * 上面触发的方式好像都只剩下JMX的触发了

# 接下去想看看db的health检测是如何执行的，我看H2_DB调用了db_script两次
* 执行具体的数据库健康检测
    * 上面CompositeHealthIndicator类的health()方法被触发执行后。。。。循环调用每个Indicator的health方法
    * DataSourceHealthIndicator的
* 它的jdbcTemplate和dataSource是如何注入进去的呢？
    * DataSourceHealthIndicatorAutoConfiguration里面会通过@Bean直接生成dbHealthIndicator对象的
    * 而DataSourceHealthIndicatorAutoConfiguration完完全全只是DefaultListableFactory自己实例化的结果。
    * 至于上面这一步是怎么完成的，完全是Spring自己注册加载机制在起作用：getBeanByType().然后实例化。

    