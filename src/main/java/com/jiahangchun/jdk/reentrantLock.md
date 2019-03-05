* [指南](https://www.cnblogs.com/xrq730/p/4979021.html)
* [volatile 指南](https://www.cnblogs.com/chenssy/p/6379280.html)

## 之前
* [大数据查询](https://mp.weixin.qq.com/s/O3Hl5bPwWr7KftKWNsbmsA)
    * 感觉这个guava的BloomFilter实现方式，关于数学的部分运用得很好：根据理想最大容量和容错率计算实际的容量。
    * 至于这个hash碰撞，为hashMap之类的提供了其他方式。但是感觉好像也是写死的进行碰撞，所以一直是允许有误差的存在。
* 打印GC情况 -XX:+PrintHeapAtGC
* ConcurrentHashMap的锁机制
    * JDK1.7好像是使用Segment进行锁控制，一共有16个桶；而JDK1.8更加高级，直接是对Hash的table里面的某个index进行synchronized
    * JDK1.7实现方式存在一定的缺陷：弱一致性；[ConcurrentHashMap的弱一致性主要是为了提升效率，是一致性与效率之间的一种权衡。要成为强一致性，就得到处使用锁，甚至是全局锁，这就与Hashtable和同步的HashMap一样了](https://my.oschina.net/hosee/blog/675423)
    * 红黑树 当桶成为了红黑树之后才会真正地在put方法时调用红黑树方法，进行平衡操作，为了使数据结构清晰。
    * 计算hash值 
        * `(h ^ (h >>> 16)) & HASH_BITS` 负数转正数
    * CAS重试机制 
        * 直接就是for循环不断重试
    * 直接操控内存
        * U.getObjectVolatile （U = sun.misc.Unsafe.getUnsafe()） 
    * synchronized 链表头之后就能遍历查寻添加Node
    * 如果添加之后，判断链表的个数是否大于 `TREEIFY_THRESHOLD = 8` ,如果是，进行红黑数的转换
        * 感觉这个要等Hash冲突到达很频繁的情况下才会发生的。毕竟每个table头下的链表长度要到达8，才能触发treeifyBin操作

## 20190226
* 问题
    * reentrantLock 是如何释放锁后，非公平地启动某个线程地？？
        * 虽然我看代码是知道，这个lock地unlock只是将持有地state变成0 & 持有地线程容器变成0 ，然后针对最后一个node进行Unsafe.unpack.但是程序是如何感知到那个被启用地线程地呢？？
  
  
## 20190228 ThreadPoolExecutor
* 参考了[文章](https://www.cnblogs.com/trust-freedom/p/6681948.html)感觉他说的很有道理，特别是前三位就能代表threadPoolExecutor的运行状态
* 位运算
* ThreadPoolExecutor的submit & executor [参考文章](https://blog.csdn.net/abountwinter/article/details/78123422)
* 这里面又重新出现了ReentrantLock,作为双重校验的存在
* 至于为什么只要放置到线程池里面程序就能正常执行，是由于这个executor是在成功加入到HashSet<Work>时主动被调用的；如果人家启动失败，那么会被调用到failedQueue
* 关于阻塞队列问题：
    * 如果是少于coreSize ,那么直接就是addWorker
    * 如果大于coreSize,加入队列，如果不能加入就直接reject
    ``* Proceed in 3 steps:
             *
             * 1. If fewer than corePoolSize threads are running, try to
             * start a new thread with the given command as its first
             * task.  The call to addWorker atomically checks runState and
             * workerCount, and so prevents false alarms that would add
             * threads when it shouldn't, by returning false.
             *
             * 2. If a task can be successfully queued, then we still need
             * to double-check whether we should have added a thread
             * (because existing ones died since last checking) or that
             * the pool shut down since entry into this method. So we
             * recheck state and if necessary roll back the enqueuing if
             * stopped, or start a new thread if there are none.
             *
             * 3. If we cannot queue task, then we try to add a new
             * thread.  If it fails, we know we are shut down or saturated
             * and so reject the task.
    ``
    * 但是这里我有一个疑问：这个ThreadPoolExecutor是如何处理超过核心线程数，又在最大线程数目的线程处理？？？？
    * 关于BlockingQueue
        * add VS offer；其实add是offer的加强版本，add当插入失败的时候直接就报错了，而offer只是加一个true/false的判断
        * take VS poll； poll只是简单地拉数据，拿不到就直接返回null;相对于take,人家加了一个notEmpty的Condition,一直保持着等待的状态。
    * 关于Condition
        * condition为什么需要和共享锁或者互斥锁一起使用？？object.notify()为什么要和synchronized一起使用呢？？
    * 关于ReentrantLock的lock.lockInterruptibly()