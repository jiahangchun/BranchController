package com.jiahangchun.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/1 下午2:40
 **/
public class TestPoolFactory implements PooledObjectFactory<TestObject> {
    @Override
    public PooledObject<TestObject> makeObject() throws Exception {
        TestObject testObject = new TestObject();
        return new DefaultPooledObject<TestObject>(testObject);
    }

    @Override
    public void destroyObject(PooledObject<TestObject> p) throws Exception {
        TestObject object = p.getObject();
        object = null;
    }

    @Override
    public boolean validateObject(PooledObject<TestObject> p) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<TestObject> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<TestObject> p) throws Exception {

    }
}
