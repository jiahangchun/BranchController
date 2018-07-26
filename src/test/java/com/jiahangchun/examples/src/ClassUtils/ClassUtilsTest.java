package com.jiahangchun.examples.src.ClassUtils;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/26 上午11:03
 **/
public class ClassUtilsTest {
    /**
     * 参考文章 https://blog.csdn.net/kaiwii/article/details/7405761
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Class testClassClass = new ClassUtilsTest().getTargetClass();
        //重复执行没有效果的，只是执行一次
        //参考文章 https://docs.oracle.com/javase/7/docs/api/java/lang/Class.html#forName
        //Class testClassClass2 = new ClassUtilsTest().getTargetClass();
        //这句话才执行了初始化的方法，和spring获取初始化类后初始化方法一致（这种方式可以加参数而已）
        testClassClass.newInstance();

//        spring的加载方法
//        Assert.isAssignable(type, instanceClass);
//        Constructor<?> constructor = instanceClass
//                .getDeclaredConstructor(parameterTypes);
//        T instance = (T) BeanUtils.instantiateClass(constructor, args);
//        instances.add(instance);
    }

    public Class getTargetClass() throws ClassNotFoundException {
        //这句话只是执行了static语句块
        Class testClassClass = Class.forName("com.jiahangchun.examples.src.ClassUtils.TestClass");
        return testClassClass;
    }


    /**
     * 为什么要使用 ClasUtils.isPresent()?
     *
     *      Replacement for {@code Class.forName()} that also returns Class instances
     * 	 * for primitives (e.g. "int") and array class names (e.g. "String[]").
     * 	 * Furthermore, it is also capable of resolving inner class names in Java source
     * 	 * style (e.g. "java.lang.Thread.State" instead of "java.lang.Thread$State").
     */
}
