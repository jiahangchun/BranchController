# dubbo中的netty调用
* 当真正执行到具体涉及到dubbo调用的接口调用地方时，先将interfaces和params封装成RpcInvocation
* check interfaces是否合法 。 在MockClusterInvoker中判断是否使用mock,如果不实用mock,直接this.invoker.invoke(invocation);
* 决策出选择策略 LoadBalance 这个时候其实只是加载了一个type 压根没有看到实例化
    * 只有一个provider的时候直接选取
    * 两个的时候是轮流选取
    * 三个以上的时候才会根据算法进行loadBalance
* 之后就是filters
* 最后是netty的事件分发
* 原来检测机器是否激活，不止能使用定时任务，zookeeper注册，也能使用timerExecutor.scheduleAtFixedRate(new Runnable()定时检查。估计我们系统的超时也是这样完成的，虽然请求仍旧在执行。

