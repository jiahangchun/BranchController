It's an interceptor, so we just invoke it: The pointcut will have been evaluated statically before this object was constructed.

事件的源头好像是从对Controller方法的调用开始的：getBridgedMethod().invoke(getBean(), args);->
上面这段代码是触发了Controller的相关方法，但是当被调用时不知道为什么又被触发了CglibAopProxy的CglibMethodInvocation.process()的代码：换句话说是执行了拦截器的操作。->



具体的代理类：
new CglibMethodInvocation
List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);



# 20190213[指南](https://mp.weixin.qq.com/s/kD5nPNzkt68ZvQT5nCFmaA)
* AOP模块
    * invokerForRequest后查询自动被触发到interceptor()方法，进行填充advisors 包括了ExposeInvocationInterceptor InstantiationModelAwarePointcutAdvisor  
        但是看了advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice 中获取对应拦截器的方法是从Advised中获取来的 。这个DefaultAdvisorChainFactory方法好像只是用于Add it conditionally 换句话说是有条件地筛选之前就准备好地拦截器。
    * 通过断点知道了 advisor 是在  
         ``
         	public void addAdvisors(Advisor... advisors) {
         		addAdvisors(Arrays.asList(advisors));
         	}
         ``  
         方法中被注入地，然后在被使用地。
    * 我们继续往上追踪，渐渐的发现addAdvisorChainFactory是在AbstractAutoProxyCreator中被注入的。但是为什么会是这个奇怪的名字？
    * 梳理了下，发现Spring是这样去做的：在创建AopController的BeanDefinition的时候，主动wrapIfNecessary.
        * 找到所有的可用的Advisor
        * 通过类名（AopController）筛选出能被这个类使用的Advisor,然后直接封装进这个Advisor中。
        * 当然之后就是使用，在方法被代理调用的时候，直接执行intercept方法，在里面被封装的chain链循环调用。