# 注解方式的Dubbo
* 加载进Dubbo,还是使用的是Spring的创建Bean的生命周期InstantiationAwareBeanFactoryAdapter
* 生成Dubbo内部独有的key 
* 生成对应的实例 AbstractAnnotationConfigBeanBuilder.build()
    * checkDependencies
    * doBuild
        * 扩展点 ExtensionLoader.getExtensionLoader
        * appendAnnotation 将配置信息封装整合
        * 
    * configureBean
    
    
## 20190122
* dubbo是如何执行方法的
    * 由curl到具体的方法，这是Spring的一般处理模式：getHandler(processedRequest)获取最后的class后，直接proxy代理生成
    * 这里又有一处是invoke的代理
    * 所有关于该方法的所有参数全部封装成RpcInvocation
    * 检测是否使用mock
    * 配置远程信息 AbstractClusterInvoke的信息。
        * 从之前的配置注册生成的dubbo里面判断是否定义的方法含有当前被调用的方法，顺便将实际的ip地址之类的配置获取过来。
        * LoadBalance 选举出remote_invoke中最合适的调用端的方法
    * FailoverClusterInvoker开始真正地打算执行下去了
        * 通过LoadBalance方法选举出合适地invokeMethod，让他们去远程调用
        * Result result = invoker.invoke(invocation);
    * 实际调用
        * 一系列的filter
        * DubboInvoker的doInvoke方法
        * Netty channel sendMsg
        * 至于消息回收完全是靠 netty接收请求
* dubbo是如何加载配置的
    * 引入的操作自然是在ListableBeanFactory的doCreateBean里面被引入的。主要是在创建ApplicationConfig的操作中被执行的。
    * 上面的步骤其实归根到底还是Dubbo-Spring实现了InitalizingBean方法，执行afterProperties方法后，使用BeanFactoryUtils.beansOfTypeIncludingAncestors去加载Dubbo的ApplicatioConfig类才被触发的。
    * 上面的触发其实都是在 ReferenceBean 执行的
    * 但是ReferenceBean的getByType方法却是dubbo手动触发的，所以并不是执行的根源。实际发现是ReferenceAnnotationBeanPostProcessor继承了InstantiationAwareBeanPostProcessorAdapter才被执行的。
    * 如果还要问 ReferenceAnnotationBeanPostProcessor 是在那里触发被执行的，我只想说自然是在Spring初始化完成时的registerBeanPostProcessors里面完成的，人家直接factory.getBeanByType(...,BeanPost.class);
    * 那dubbo参数如何引入的呢？比如收集到remote信息，将信息封装的？
        * RegistryDirectory 是在实例化对象的时候被填充的.这里面是个listener，但是。。。
        * 额，上面的listener的refreshInvoker处理的就是远程调用的信息
        * 至于远程调用信息的维护，最后跟踪上去完全是Zk在管理，人家只是设置了参数的回调 ZkclientZookeeperClient 的 createTargetChildListener 方法进行监听。
        * 到Zookeeper上注册当前的调用。ZookeeperRegistry.doSubscribe
        * 在doSubScribe将请求的地址注册上Zk时，直接将可用的url收集到child上，然后根据当前的请求路径match可用的真实远程地址，然后加入到registryDirectory中。
* dubbo的netty....(hold)
* dubbo的常用配置
    * copy 自@Reference  