# spring 大致处理流程
* spring的主要流程其实都在Dispatcher类里面,最终反馈到RequestMappingHandlerAdapter的handleInternal方法中
    * 解析参数 主要是通过handleType找到对应的具体执行业务逻辑的代码创建数据绑定对象ServletRequestDataBinderFactory
    * 初始化ModelAndViewContainer
    * 判读请求是否是异步请求，如果是 转换成AsyncWebRequest
    * 正式执行 invocableMethod.invokeAndHandle ,通过反射调用具体的方法
    * 填充生成ModelAndView
    * 扫尾处理 interceptions 的 postHandle & afterCompletion
    * publishRequestHandledEvent
    
    
# spring中关于数据库的大致处理流程
* mybatis被调用执行
    * 当Spring请求被invoke到具体的方法内，触发springboot-mybatis的invoke方法，开始调用mybatis
    * 当被spring调用时，被触发MapperProxy类，进行MapperMethod方法的调用
    * 选择具体的执行方法器 select,update,or....
    * 之后通过类名+方法名（key）的方式找到之前定义好的sql方法.这个时候其实已经将参数 sql 校验操作 都已经准备地差不多了
    * 真正地核心类defaultSqlSession方法执行了具体地操作
        * BoundSql 是为了聚集数据，包括cacheKey之类的也是全放在这里面聚集的
        * 先查询一级缓存
        * BaseExecutor的queryFromDatabase
        * 获取到另一个第三方的连接库信息 Hikari
        * 然后才是获取到H2数据库的具体操作
    * sqlSession commit
    * 处理ModelAndView
    
* mybatis的其他特点
    * 一级缓存 & 二级缓存
        * 在同一个sqlSession中 通过cacheKey的方式来生成缓存对象
        * 这个需要单独设置的，当然也可以用第三方库
        
        
# 20190225
[mybatis系列文章](https://blog.csdn.net/luanlouis/article/details/40422941)

* mybatis的主要处理器 
    * SqlSession            作为MyBatis工作的主要顶层API，表示和数据库交互的会话，完成必要数据库增删改查功能  
    * Executor              MyBatis执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护  
    * StatementHandler   封装了JDBC Statement操作，负责对JDBC statement 的操作，如设置参数、将Statement结果集转换成List集合。  
    * ParameterHandler   负责对用户传递的参数转换成JDBC Statement 所需要的参数，  
    * ResultSetHandler    负责将JDBC返回的ResultSet结果集对象转换成List类型的集合；  
    * TypeHandler          负责java数据类型和jdbc数据类型之间的映射和转换  
    * MappedStatement   MappedStatement维护了一条<select|update|delete|insert>节点的封装，   
    * SqlSource            负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象中，并返回  
    * BoundSql             表示动态生成的SQL语句以及相应的参数信息  
    * Configuration        MyBatis所有的配置信息都维持在Configuration对象之中。  

* 问题
    * mybatis是如何构筑动态sql的
    * 事务管理机制
        * 使用JDBC的事务管理机制
        * 使用MANAGED的事务管理机制
    * 连接池管理机制
        * POOLED 看了上面相关的文章，这个连接池很像一个缓存。
        * UNPOOLED 
    * 缓存机制
    * SQL语句的配置方式
    * 引导层？？？

# 20190228 HandlerMapping的处理实现
