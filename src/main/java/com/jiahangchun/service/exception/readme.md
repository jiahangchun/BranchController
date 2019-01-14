## 20180910
	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
@SuppressWarnings("deprecation")  // on JDK 9
public static void makeAccessible(Method method) {
	if ((!Modifier.isPublic(method.getModifiers()) ||
			!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
		method.setAccessible(true);
	}
}
子类和父类的反射方法必须是public的么？
InvocableHandlerMethod是每一个类都是必须的么？


## 20180926
其实被调用ControllerAdvice的真正的地方就是在resolveHandlerException   
我到觉得这里有几个点需要特别关注下：1.反射方式地调用某个bean的方法，2.Annotation的收集解析 3.spring的异常处理机制  

关于spring的异常处理机制  
1.其实这可以从tomcat的servlet的分发事件说起了：
HttpServlet.service-->
FrameworkServlet.processResult->
DispatcherServlet.doService(doDispatch)-> 
DispatcherServlet.processDispatchResult->这个类是在所有处理结束的时候会被调用，区别只是在于异常情况会传入一个dispatchException
DispatcherServlet.processHandlerException-> 最终的目的是调用handlerExceptionResolves进行异常情况的处理(DefaultErrorAttributes & HandlerExceptionResolverComposite[组合模式](https://www.cnblogs.com/snaildev/p/7647190.html)说白了是某些HandlerExceptionResolver的集合)
HandlerExceptionResolverComposite.resolveException->果然这个HandlerExceptionResolverComposite只是调用了它内部存储的子handler进行处理而已
ExceptionHandlerExceptionResolver.resolveException（doResolveHandler,doResolverHandlerMethodException）->里面组合的处理器实际上是有其中特定的ExceptionHandlerExceptionResolver进行处理的
ExceptionHandlerExceptionResolver.doResolveHandlerMethodException->
        1/获取异常处理的异常处理方法 getExceptionHandlerMethod()。方法里面其实是将exceptionHandlerAdvice转换成一个exceptionHandlerMethodResolver.当然最终是获取了个ServletInvocableHandlerMethod(其实在这个时候已经将我们的CustomExceptionResolver已经设置到这个Method里面了)
        2/HandlerMethodArgumentResolverComposite
        3/HandlerMethodReturnValueResolverComposite
        4/前置条件都准备好了之后就是invokeAndHandler
        5/然后就是顺理成章地进行代理调用我们写好地ControllerAdvice


## 20180928
反射地调用某个方法
    		Spring中主要应该是InvocableHandlerMethod方法进行的
    		    doInvoke(Object... args)->
    		    里面其实主要是getBridgedMethod().invoke(getBean(),args) 换句话说其实Spring在调用这一块的代码只是简单使用了[MethodAccess](https://www.cnblogs.com/onlywujun/p/3519037.html)
    		    
    		    
## 20180929
对注解的解析和注册，我是通过观察DefaultListableBeanFactory实现的BeanDefinitionRegistry.registryBeanDefinition来找到对应的Bean注册的方法。
    1/DefaultListableBeanFactory.registryBeanDefinition 这个其实就是我观察的入口了
    2/BeanDefinitionReadUtils.registerBeanDefinition的通用方法
    3/AnnotatedBeanDefinitionReader.doRegisterBean
    4/BeanDefinitionLoader.load()会调用相关的this.annotatedReader.registry(source)
    5/SpringApplication.sources属性相当于要注册的类，到现在我发现我找错地方了：这里只是注册SpringBoot的启动类
其实说到底上面的只是SpringBoot启动类的Bean注册，虽然也是注册。但是和其他Spring的注册其实是差不多的。
    针对Controller & Service 的注册相关的注解类
    1/DefaultListableBeanFactory.registryBeanDefinition 这个其实就是我观察的入口了
    2/BeanDefinitionReadUtils.registerBeanDefinition的通用方法
    3/ClassPathBeanDefinitionScanner.registryBeanDefinition
    4/ClassPathBeanDefinitionScanner.doScan 这个好像就是为了扫描某个包下面的所有Component,然后完成注册
    5/ComponentScanAnnotationParser.parse()
    6/ConfigurationClassParser.doProcessConfigurationClass 这里面有一句话 Process Any @ComponentScan annotations
    7/ConfigurationClassPostProcessor.processConfigBeanDefinitions 这里有句解释：Build and validate a configuration model based on the registry of Config
    8/ConfigurationClassPostProcessor.processConfigBeanDefinitionRegistry
    9/PostProcessRegistrationDelegate.invokeBeanFactoryPostProcessors
    10/AbstractApplicationContext.invokeBeanFactoryPostProcessors
    11/AbstractApplicationContext.refresh();
以上就是Controller & Service (Any @ComponentScan) 被实例化进入IOC池当中的所有过程

## 20190109
其实感觉这些有点像业务逻辑，只是在特定的时候执行。在初始化的时候收集信息，在需要使用的时候而已，我更加希望看到的是设计。


参考文章：
[RequestMappingHandlerMapping](https://blog.csdn.net/sunsiwenvip/article/details/80230050)







    		    


