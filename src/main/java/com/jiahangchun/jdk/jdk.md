## 提纲
* [面试整理](https://www.jianshu.com/p/35b74796448b?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
    * jvm 内存区域
        * 线程共享
            * heap 堆只是保存了对象的实例而不是方法，运行时的数据区域，方法是存放在栈帧里面的 
            * method area 加载类的类定义数据，存储一些常量，静态变量等信息
        * 非线程共享
            * stack 栈帧用于存放方法执行的内容
            * native method area 本地方法区
            * 程序计数器
    * java运行时内存变化
        * 永久代 在jdk1.8之后被取代为元数据区，这个元数据空间则时直接使用本地内存
        * 新生代
        * 两个幸存区 
        * 老年代
    * 对象在内存中时如何初始化的
        * 虚拟器用类加载器Class Loader加载java程序类文件到方法区
        * 主线程运行static main（）时在虚拟机栈中建栈帧，压栈
        * 执行到new Object（）时，在堆heap里创建对象
        * 当虚拟机执行到new指令时，它先在常量池中查找“Dog”，看能否定位到Dog类的符号引用；如果能，说明这个类已经被加载到方法区了，则继续执行。如果没有，就让Class Loader先执行类的加载
        * 会根据dog这个变量是实例变量、局部变量或静态变量的不同将引用放在不同的地方：
            * 如果dog局部变量，dog变量在栈帧的局部变量表，这个对象的引用就放在栈帧。
            * 如果dog是实例变量，dog变量在堆中，对象的引用就放在堆。
            * 如果dog是静态变量，dog变量在方法区，对象的引用就放在方法区。
         * 
* [设计模式](https://www.cnblogs.com/geek6/p/3951677.html)
    * [代理实现方式](https://blog.csdn.net/nimqbiyq/article/details/81166893) cglib jdb javassist,asm
* [常见算法]()
* [常见数据结构]()
* [框架]()
    * [mybatis]()
        * mybatis 是怎样被注入的
            * 换句话说，我想知道我的Mapper是哪里开始被注入的？
                * 其实spring在实例化的bean的时候，会determineAutowireCandidate，也就是寻找@Autowire所标注的类。我们可以看到这个动作正是在DefaultListableBeanFactory的doResolveDependency方法。
                * 追踪上去可以发现是 当所有的Bean都被正确地初始化之后才被finishBeanFactoryInitialization执行，也就是形成真正的Bean.之前的只是生成BeanDefinition，通过BeanDefinitionRegister来进行校验操作。
            * Mapper又是如何注入的？
                * 当我们自己的类被注入之后，也就是生成了一个MapperProxy之后，就要将当前类所依赖的依次放入DefaultSingletonBeanRegistry的dependentBeans当中
        * 那么Spring已经找到了自己的依赖，他又是如何使用的呢？？
            * 当mybatis被真正调用某个方法时，其实早就将一个信息整合成MapperMethod对象。至于sqlSession我倒是觉得他只是一个顶级的mybatis处理接口。
            * 之后就是通过MapperProxy里面的请求方法当作一个id，关联之前早就已经从xml里面配置好的语句 ，即MapperStatement。
        * 那么这个xml里面的语句又是如何注册上去的呢？
            * 在mybatis-spring 当中，存在一个实现了InitializingBean方法的DaoSupport,所以可想而知是在spring初始化的afterPropertiesSet的时候进行addMapper的。
            * 解析注解了mybatis的接口,然后将这个注解上的信息添加到Configuration的mappedStatements中。
        * 那么当spring和mybatis的语句参数关联在一起后，程序又是如何再次执行的呢？？
            * 参数替换，生成BoundSql
    * [spring]()
        * spring的自动配置时如何实现的
        * [BeanFactory 和 FactoryBean](https://www.cnblogs.com/aspirant/p/9082858.html)
        * Spring IOC 的理解，其初始化过程
        * Spring Bean 的生命周期，如何被管理的
        * Spring Bean 的加载过程是怎样的
        * 如果要你实现Spring AOP，请问怎么实现
    * [dubbo]()
        * dubbo的自动配置又是如何实现的
* 常见的jdk分析
    * [volatile](https://www.cnblogs.com/java-jun-world2099/p/9268013.html)
    * [AIO BIO NIO](https://blog.csdn.net/ty497122758/article/details/78979302) 
    * [偏向锁、轻量级锁和重量级锁](https://www.cnblogs.com/paddix/p/5405678.html)
    * ReentrantLock 
    * CountDownLock
    * CAS 的实现原理 AbstractQueuedSynchronizer
        * 那么reentrantLock的队列又是如何实现的呢？？
            * 其实我发现是直接在acquireQueued方法里面自行构建node节点链
         * CAS 的常见问题
            * ABA 问题
          * ABA 解决问题的方法
            * AtomicStampedReference
    * ConcurrentHashMap 
* mysql 
    * 
# 详细文章
* 