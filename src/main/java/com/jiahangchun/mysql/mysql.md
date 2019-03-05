# 之前
Mysql 阅读《深入浅出MYSQL》
* 选择合适的数据类型
    * char & varchar
        * varchar 相较于 char 是一个可变类型的字节存储单元结构，能动态改变所占用的内存大小。
        * char 会自动切除尾末的空格的  
            `
            例子：
            -- create table vc(v varchar(100),c char(100));
            insert into vc values('ab.      ','ab.      ');
            select concat(v,' +'),concat (c,' +') from vc;
            `
            
* 索引注意点        
    * 搜索的索引列， 不一定是所要选择的列。 换句话说， 最适合索引的列是出现在WHERE 子 句中的列， 或连接子句中指定的列， 而不是出现S在ELECT 关键字后的选择列表中的列
    * 使用惟一索引。 
    * 使用短索引。 如果对串列进行索引， 应该指定一个前缀长度， 只要有可能就应该这做。样 例如，如果有一个 CHAR(200) 列，如果在前 10 个或 20 个字符内，多数值是惟一的， 那么就不要对整个列进行索引。对前10 个或 20 个字符进行索引能够节省大量索引空 间，也可能会使查询更快。较小的索引涉及的磁盘I/O 较少，较短的值比较起来更快。 更为重要的是， 对于较短的键值， 索引高速缓存中的块能容纳更多的键值， 因M此yS，QL 也可以在内存中容纳更多的值。这增加 了找到行而不用读取索引中较多块的可能性。
      (当然， 应该利用一些常识。 如仅用列值的一第个字符进行索引是不可能有多大好处的， 因为这个索引中不会有许多不 同的值。 )
    * 利用最左前缀。在创建 一个 n 列的索引时，实际是创建了 MySQL 可利用的 n 个索引。 多列索引可起几个索引的作用， 因为可利用索引中最左边的列集来匹配行。 这样的集列 称为最左前缀。 (这与索引一个列的前缀不同，索引一个列的前缀是利用该的n前个字 符作为索引值。 )
    * 不要过度索引。
    * 考虑在列上进行的比较类型。索引可用于“ <”、“ < = ”、“ = ”、“ > =”、“ > ”和 BETWEEN 运算。在模式具有一个直接量前缀时，索引也用于LIKE 运算。如果只
      将某个列用于其他类型的运算时(如STRCMP( )) ，对其进行索引没有价值。

* 存储过程例子  
    `
    drop procedure if exists correct_try_buy;  
    delimiter //   
    create procedure correct_try_buy(in my_id varchar(500),IN dateMsg varchar(500))  
    BEGIN  
    declare distributor_id varchar(500);  
    declare parent_distributor_id int default 0;  
    if parent_distributor_id != 0  
    then
    ...
    END if;
    END;    
    `
    
* 常见索引
    * INDEX
    * PRIMARY
    * UNIQUE
    * [FULLTEXT](https://www.cnblogs.com/parryyang/p/5900926.html) 

* 事务锁的安全点 
    * savepoint
    
* mysql常用方法函数
    * MIN([DISTINCT] expr), MAX([DISTINCT] expr)
    * rand()/rand(n)提取随机行  
        `
        SELECT * FROM table1, table2 WHERE a=b AND c<d
        ORDER BY RAND() LIMIT 1000;
        ` 
    * group by 的 with rollup 子句做统计   
    * 用 bit group functions 做统计
    
* sql优化
    * SHOW STATUS
        * 主要是看 
            * Connections 试图连接 Mysql 服务器的次数 
            * Uptime 服务器工作时间
            * Slow_queries 慢查询的次数
    * 定位执行效率较低的 SQL 语句
        * 可以通过慢查询日志定位那些执行效率较低的sql 语句，用--log-slow- queries[=file_name]选项启动时，mysqld 写一个包含所有执行时间超过 long_query_time 秒的 SQL 语句的日志文件  
        * 慢查询日志在查询结束以后才纪录， 所以在应用反映执行效率出现问题的时候询查 慢查询日志并不能定位问题，可以使用show processlist 命令查看当前 MySQL 在 进行的线程，包括线程的状态，是否锁表等等，可以实时的查看SQL 执行情况，同 时对一些锁表操作进行优化。  
    * 通过 EXPLAIN 分析低效 SQL 的执行计划
        * 主要适用于查看索引的使用情况
    * 使用join避免使用临时表 
    
    
* 锁
    * 表锁
    * 行锁
    
## 20190213
* 在程序一直运行的情况下（不断插入数据），表现为查询超时。但是当程序停止运行的时候又表现正常，在500万数据的情况下，耗时4秒。  
``SELECT count(1) FROM md5;``

* 这三种查询方式其实差别不大  
``
SELECT count(1) FROM jiahangchun.md5;
SELECT count(*) FROM jiahangchun.md5;
SELECT count(id) FROM jiahangchun.md5;
``


    
文章
* [最左前缀原则](https://blog.csdn.net/wx145/article/details/82839419)
* [别人总结的条目](https://blog.csdn.net/chivydrs/article/details/81670475)
        