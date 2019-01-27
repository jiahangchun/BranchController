## 教程的问题
* 这个eureka的界面是怎么处理的
* server注册？当zk来用？
* 我真没明白各个依赖包之间的关系
* 为什么我不能同时注册client和server?我就是想这样

# eureka注册流程

# 对于server请求 这个就是eureka的界面形成的过程
* 本来还没头绪，结果发现log里面存在EurekaController。顺便看了下，发现这个就是eureka界面的请求地址
* 好像真的只是简简单单地使用ModelAndView.只是为modelAndView填充信息。
* 算了，接下去看看如何渲染ModelAndView的实现方式把
    * 自定义的方法被触发成功后，也就是那个invoke方法
    * 寻找对应的Handler,通过15个handler的选择过滤，handlerReturnValue通过遍历isSupport方法，找到了ViewNameMethodReturnValueHandler处理器。其实好像也没做什么特殊处理，只是setViewName()
    * 然后再去找ModelAndView.对了，这里不是找而是自己new一个出来，只是参数都是用default的
    * 在dispatcherServlet中resolveViewName,去获取对应的View
        * 遍历六中ViewResolver，直到能正常地解析出对应地View.其实也就是附加些参数
    * processDispatchResult 这个是DispatcherServlet中关于view的最后处理了，应该是在这个方法里面执行了把
    * view.render 这里面应该真正是把View转换成Model了。
        * 参数merge
        * 通过名称在freemark的cache中找到对应的模版
        * 将之前准备好的model参数全部存入template模版当中
        * 最后放入response里面
        
        
# server注册了什么东西？到底干了些什么
* 没有什么头绪，只能先看比较明显的注解@EnableEurekaServer。发现两个入口EurekaServerMarkerConfiguration & EurekaServerAutoConfiguration
* 感觉EurekaServerMarkerConfiguration这个方法好简单呐，估计是被用来@ConditionOnBean方式注入其他Bean，因为人家注解上就是这么说的：  
    ``
     Annotation to activate Eureka Server related configuration {@link EurekaServerAutoConfiguration}
    ``
* 现在好像只能去看 EurekaServerAutoConfiguration 
    * EurekaServerInitializerConfiguration
        * 先是在配置一些信息 environment
        * 怎么感觉好像是为了发出event?是通知eureka启动服务么？但是我找了上下文还是不知道这个Event到底被谁消费了，所以感觉进入了死胡同。
    * EurekaServerContext
        * 怎么感觉这个和EurekaServerBootstrap一样只是配置了上下文，用于信息的聚集？
    * EurekaServerBootstrap
* 在上面的方法进入死胡同，需要寻求帮助。
    * 我只能再看[文章](https://www.jianshu.com/p/724ca24cfbd6),找到了好像关于注册服务的接口ApplicationResource.通过接口addInstance，又找到了PeerReplicationResource的batchReplication，估计真的是注册。
        * 然后无聊的时候还看了些文章，感觉这篇[文章](https://segmentfault.com/a/1190000011668299)写地真好。说明了ApplicationResource的addInstance真的是注册接口。
    * 我在log里面还找到一个非常明显的日志过程  
        ``
        EurekaServiceRegistry        : Registering application unknown with eureka with status UP
        ``
* 新的源头 ApplicationResource.addInstance
    * 首先是确认前来注册的dataCenter，进行一系列参数校验
    * 这个时候，突然进入具体的注册方法类 InstanceRegistry 中，真地发现了一系列的操作：注册，取消，新身份认证。。。
    * 处理持续时间，这里感觉有点重复了,同一个方法里面被连续调用两次
        ``
         if (info.getLeaseInfo() != null && info.getLeaseInfo().getDurationInSecs() > 0) {
                    leaseDuration = info.getLeaseInfo().getDurationInSecs();
                }
        ``
    * 实际的注册逻辑发现全部是在AbstractInstanceRegistry的register方法中 怎么感觉这个是在更新注册时间 & 维护内存表？？
    * replicateInstanceActionsToPeers 复制注册的实例到其他节点？？

    
# 在看server如何注册方面我突然发现自己好像对Spring的流程并不是很清楚(Lifecycle)，所以上面的先hold
* Application.run
    * 注册Listener,主要还是从配置文件中获取的Listener实例化而已
    * 配置ConfigurableEnvironment 这个环境是干什么的？？？
    * Banner
    * 配置ApplicationContext 这个其实是通过硬编码的方式触发执行的，感觉写的有点low
    * 配置异常处理机制 SpringBootExceptionReporter
    * 怎么感觉非常像将之前的数据进行整合，外加一个初始化操作？prepareContext
    * refresh的操作非常多
        * Prepare this context for refreshing.
        * Tell the subclass to refresh the internal bean factory.
        * Prepare the bean factory for use in this context. 好像是在为factory准备参数
        * Allows post-processing of the bean factory in context subclasses.
        * Invoke factory processors registered as beans in the context.Instantiate and invoke all registered BeanFactoryPostProcessor beans,respecting explicit order if given.
        * Register bean processors that intercept bean creation. 这里就是简单地使用了ApplicationContext.getType进行全盘地实例子化进行处理的
        * Initialize the MessageSource. 国际化？？没用过ResourceBundle
        * Initialize event multicaster for this context.这个是不是就是application.multi...()的底层方法
        * Initialize other special beans in specific context subclasses.
        * Check for listener beans and register them.
        * Instantiate all remaining (non-lazy-init) singletons.
        * Last step: publish corresponding event.