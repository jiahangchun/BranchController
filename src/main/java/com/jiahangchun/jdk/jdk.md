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
    *   
     
            
* [设计模式]()
    * 代理实现方式 cglib jdb javassist,asm
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
    * [dubbo]()

# 详细文章
* 