package com.jiahangchun.examples.src.RunTime;

import com.jiahangchun.examples.src.ClassUtils.ClassUtilsTest;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/26 上午11:24
 **/
public class RunTimeTest {
    public static void main(String[] args) throws ClassNotFoundException {
//        Class testClassClass = new ClassUtilsTest().getTargetClass();

        //这里倒是只有一个启动类:当前启动
//        new RunTimeTest().runtimeStackTrace();

        new RunTimeTest().methodA();
    }

    private void methodA(){
        System.out.println("------进入methodA----------");
        methodB();
    }

    private void methodB(){
        System.out.println("------进入methodB----------");
        StackTraceElement elements[] = Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            StackTraceElement stackTraceElement=elements[i];
            String className=stackTraceElement.getClassName();
            String methodName=stackTraceElement.getMethodName();
            String fileName=stackTraceElement.getFileName();
            int lineNumber=stackTraceElement.getLineNumber();
            System.out.println("StackTraceElement数组下标 i="+i+",fileName="
                    +fileName+",className="+className+",methodName="+methodName+",lineNumber="+lineNumber);
        }
    }

    /**
     * 这个类的作用好像就是为了记录调用过程的
     * @throws ClassNotFoundException
     */
    public void runtimeStackTrace() throws ClassNotFoundException {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                Class className= Class.forName(stackTraceElement.getClassName());
            }
        }
    }

    /**
     * 参考文档
     * https://blog.csdn.net/hp910315/article/details/52702199
     *
     * 用途
     * 1。封装一个日志库
     * 2。如果我们写了一个SDK，希望某个方法在固定的位置被调用，我们也可以在这个方法被调用的时候，进行检查，看这个方法的调用位置是否正确
     *
     */
}
