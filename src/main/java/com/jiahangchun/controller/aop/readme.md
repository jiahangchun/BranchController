It's an interceptor, so we just invoke it: The pointcut will have been evaluated statically before this object was constructed.

事件的源头好像是从对Controller方法的调用开始的：getBridgedMethod().invoke(getBean(), args);->
上面这段代码是触发了Controller的相关方法，但是当被调用时不知道为什么又被触发了CglibAopProxy的CglibMethodInvocation.process()的代码：换句话说是执行了拦截器的操作。->



具体的代理类：
new CglibMethodInvocation
List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);



# 20190212 [指南](https://mp.weixin.qq.com/s/kD5nPNzkt68ZvQT5nCFmaA)
* AOP模块
    * 