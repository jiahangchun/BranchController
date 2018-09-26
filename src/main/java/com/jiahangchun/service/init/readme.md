* 效果  
  InitializingBean 在 SmartInitializingSingleton 初始化之前被调用  
 
* 注释  
    Callback interface triggered at the end of the singleton pre-instantiation phase
 during {@link BeanFactory} bootstrap. This interface can be implemented by
 singleton beans in order to perform some initialization after the regular
 singleton instantiation algorithm, avoiding side effects with accidental early
 initialization (e.g. from {@link ListableBeanFactory#getBeansOfType} calls).
 In that sense, it is an alternative to {@link InitializingBean} which gets
 triggered right at the end of a bean's local construction phase.
 
* InitializingBean 的初始化过程
直接在abstractAutowireCapableBeanFactory上调用invokeInitMethods方法进行触发bean的bean.afterPropertiesSet()方法。这个方法看注释是为了invoke a necessary callback.
只要这个类是实现了InitialingBean接口，那么一定会被触发。 
其实这个类的触发主要是在AbstractApplicationContext的refresh方法中的registerBeanPostProcessors被触发 


* SmartInitializingSingleton 的初始化过程
这个方法主要是在DefaultListableBeanFactory的preInstantiateSingletons方法里面被触发，触发方式其实和InitializingBean的触发方式非常相似：查看是否实现了SmartInitializingSingleton接口，如果实现了就调用相关的方法。  
至于具体的触发，其实是在AbstractApplicationContext的finishBeanFactoryInitialization的方法触发，具体的感觉好像涉及到了那个Application的refresh方法

* InitializingBean所有的相关的初始化类
所有要触发的类其实都是在PostProcessorRegistrationDelegate类的registerBeanPostProcessors方法中被优先处理掉，然后判断有没有实现相关接口而已。