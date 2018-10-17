HandlerMapping被初始化的过程
HttpServletBean.init()->这个是由tomcat自动执行的，而在SpringBoot中Tomcat使用的是EmbedTomcat。同时如果是由XML方式启动的，则WebApplicationContext是交给web.xml中配置的ContextLoaderListener完成的；而SpringBoot方式启动的AnnotationConfigServletApplicationContext是通过SpringApplication.run方法直接初始化掉了，包括BeanDefinition等配置信息的初始化。
FrameworkServlet.initWebApplication();->
DispatcherServlet.initStrategies();针对了HandlerMapping的初始化->
DispatcherServlet.initHandlerMappings;其实只是this.handlerMappings=BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);


HandlerMapping的被使用过程
DispatcherServlet.doService进行方法的分发处理->
DispatcherServlet.getHandler (HandlerExecutionChain handler=hm.getHandler(request)). 而这些Handler由之前组合起来的SimpleUrlHandlerMapping &  RequestMappingHandlerMapping & BeanNameUrlHandlerMapping@8834 & SimpleUrlHandlerMapping & WebMvcConfigurationSupport$EmptyHandlerMapping@8836 & WebMvcConfigurationSupport & WelcomePageHandlerMapping 依次进行获取。发现最后选择的是->
AbstractHandlerMapping.getHandler 返回的将是一个包含了interceptor_list & handler 的对象，为以后做准备->
DispatcherServlet.doDispatch 在做最后的处理：获取到MethodHandler & interceptor 后，执行Controller对应的方法： invoke->
RequestMappingHandlerAdapter.handleInternal 通过具体执行的HandlerMethod来获取ModelAndView
                                                                                                                           
