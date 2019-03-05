## common-pool2线程池
* 配置factory 就是生产对象的容器
* 配置config 一些设计调度策略 
    * 空闲的资源列表 LinkedBlockingDeque
* pool.borrowObject 获取资源
    * 先从空闲的队列里面pop一个，如果找不到就自己create一个
    * 如果程序不允许create一个新的对象，只能在LinkedBlockingDeque的idle队列里面死等   
        ``
         if (p == null) {
                            if (borrowMaxWaitMillis < 0) {
                                p = idleObjects.takeFirst();
                            } else {
                                p = idleObjects.pollFirst(borrowMaxWaitMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                        }
        ``
    * 话说上面的实现方式真的没有问题么？不会造成线程无限创建？？导致系统崩溃
        * 其实上面的问题是有前提的：创建线程的最大个数....[参考文章](https://blog.csdn.net/caomiao2006/article/details/51337798)，我是压根还不能创建足够多的线程的地步。
        * 换句话说，这个问题不太可能出现而已。
    