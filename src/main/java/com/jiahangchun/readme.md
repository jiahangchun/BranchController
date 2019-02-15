## 20180929
* 对注解的解析和注册，我是通过观察DefaultListableBeanFactory实现的BeanDefinitionRegistry.registryBeanDefinition来找到对应的Bean注册的方法。
    * DefaultListableBeanFactory.registryBeanDefinition 这个其实就是我观察的入口了
    * BeanDefinitionReadUtils.registerBeanDefinition的通用方法
    * AnnotatedBeanDefinitionReader.doRegisterBean
    * BeanDefinitionLoader.load()会调用相关的this.annotatedReader.registry(source)
    * SpringApplication.sources属性相当于要注册的类，到现在我发现我找错地方了：这里只是注册SpringBoot的启动类
* 其实说到底上面的只是SpringBoot启动类的Bean注册，虽然也是注册。但是和其他Spring的注册其实是差不多的。
    * 针对Controller & Service 的注册相关的注解类
    * DefaultListableBeanFactory.registryBeanDefinition 这个其实就是我观察的入口了
    * BeanDefinitionReadUtils.registerBeanDefinition的通用方法
    * ClassPathBeanDefinitionScanner.registryBeanDefinition
    * ClassPathBeanDefinitionScanner.doScan 这个好像就是为了扫描某个包下面的所有Component,然后完成注册
    * ComponentScanAnnotationParser.parse()
    * ConfigurationClassParser.doProcessConfigurationClass 这里面有一句话 Process Any @ComponentScan annotations
    * ConfigurationClassPostProcessor.processConfigBeanDefinitions 这里有句解释：Build and validate a configuration model based on the registry of Config
    * ConfigurationClassPostProcessor.processConfigBeanDefinitionRegistry
    * PostProcessRegistrationDelegate.invokeBeanFactoryPostProcessors
    * AbstractApplicationContext.invokeBeanFactoryPostProcessors
    * AbstractApplicationContext.refresh();
以上就是Controller & Service (Any @ComponentScan) 被实例化进入IOC池当中的所有过程


#20190213 补充
* BeanFactory 下面的过程其实是我反向推的：我之前有点印象我们的默认实现是DefaultListableBeanFactory,所以直接找到实例化方法，断点，然后反向推理的
    * SpringBoot 在创建过程中 会有一个创建上下文档的过程 createApplicationContext() ；
    * 创建过程也是通过type指定对应的类型，然后BeanUtils.instantiateClass(Class.forName(....直接写死的,名字叫AnnotationConfigApplicationContext))   
      ``话说我能不能也像上面那样写，不用@Bean之类的？？``
    * 然后看下去，发现``AnnotatedBeanDefinitionReader 和 ClassPathBeanDefinitionScanner``明显就是在处理注解了，当然作为父类的GenericApplicationContext也为了它创建了 DefaultListableBeanFactory 对象
    * 但是之后在AnnotatedBeanDefinitionReader 和 DefaultListableBeanFactory上设置断点，发现完全没有Service踪影。后来干脆在AnnotationBeanNameGenerator的generateBeanName设置断点，专门找我要的那些Service（其实这个也是看了些博客才尝试的）。然后就能继续反推了。
    * 少数流程：[所以这边可以通过BeanDefinition批量配置Bean](https://blog.csdn.net/liyantianmin/article/details/81047373)
        * Application.refresh
        * invokeBeanFactoryPostProcessors
        * invokeBeanDefinitionRegistryPostProcessors
        * ConfigurationClassParser.parse // Parse each @Configuration class
        * scanner.doScan(StringUtils.toStringArray(basePackages));
        * Set<BeanDefinition> candidates = findCandidateComponents(basePackage); //这里就是找到所有的类型，然后将其注册到BeanDefinitionRegister上
            * 那么这个basePackage是那里获取的呢？？发现还是``fqClassName.lastIndexOf(PACKAGE_SEPARATOR)``也就是根据启动的App位置启动的。所以为什么一般将App.class放在最外面
        * scanCandidateComponents(basePackage); //这里就是寻找当前包下的所有可用的被注解的类
        * Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath)； //找到所有的服了条件的class "classpath*:com/jiahangchun/**/*.class"
        * 判断这个class 包不包括 @Component。。。 注解。 如果符合条件，那么生成自己的Bean(ScannedGenericBeanDefinition)为后面的注解注册提供条件。
        * 好了，解析的大致过程就是这样。
     * 在创建了专门的Definition后，就是注册到Register了。这里面还涉及了alias<->beanName的对应匹配。
     * ``那么问题又来了：如何注入@Autowired 的其他bean 呢？？ ``
        * 既然是注入，那么先找找如何生成Bean的把。应该在DefaultListableBeanFactory左右
        * 一直以为是在DefaultListableBeanFactory 后来才明白是在AbstractBeanFactory上的doGetBean 人家有一句话 Guarantee initialization of beans that the current bean depends on.
        * 这里其实是先AutowiredAnnotationBeanPostProcessor类的buildAutowiringMetadata方法 ，解析出一个具体的Bean,针对每一个属性解析出是否需要依赖注入。如果需要就放置到当前BeanDefinition的某个依赖列表里面。
     * ``那么问题来了：那些@Value又是如何处理的呢？？``
        * 其实在处理@Autowired的时候，spring已经处理掉了。运用相同的方法（反射注入的），针对特定的BeanDefinition,找到需要处理的Property(MutablePropertyValues).然后反射处理
     * 这个时候我觉得所有的疑问已经差不多解决了，那么 BeanFactory 可以概括为 //This interface is implemented by objects that hold a number of bean definitions, each uniquely identified by a String name
        * 简单地来说是用于处理BeanDefinitionRegister的集合，提供了操作Bean的集合接口
            
        
        