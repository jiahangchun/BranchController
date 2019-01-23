## 基本策略
* Transaction Coordinator(TC)：维护全局和分支事务的状态，驱动全局事务提交与回滚。
* Transaction Manager(TM)：定义全局事务的范围：开始、提交或回滚全局事务。
* Resource Manager(RM)：管理分支事务处理的资源，与 TC 通信以注册分支事务并报告

## 处理流程(感觉线程用得有点多)
* TMC init
    * 初始化RPC,就是Dubbo常用的那个Netty,当作发射器一样的东西
    * 地址维持 reconnect
* RMC init 
    * 一样的道理，先初始化RPC
    * doBranchCommits 定时处理掉已经提交的记录，undo_log减少。这个表也没看见加索引的操作，估计是要维持比较少的量级才行。
    * 又是一个多线程 进行超时检测
    
### 详细的过程还是需要安排完整的流程的，不能单单只看TestDemo