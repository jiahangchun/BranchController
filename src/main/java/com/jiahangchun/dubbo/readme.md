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