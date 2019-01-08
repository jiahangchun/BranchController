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