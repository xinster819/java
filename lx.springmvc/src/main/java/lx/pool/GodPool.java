package lx.pool;

import java.util.concurrent.atomic.AtomicInteger;

import lx.vo.God;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GodPool implements PooledObjectFactory<God> {

    AtomicInteger count = new AtomicInteger();

    @Override
    public PooledObject<God> makeObject() throws Exception {
        return new DefaultPooledObject<God>(new God(count.addAndGet(1)));
    }

    @Override
    public void destroyObject(PooledObject<God> p) throws Exception {
    }

    @Override
    public boolean validateObject(PooledObject<God> p) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<God> p) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void passivateObject(PooledObject<God> p) throws Exception {
        // TODO Auto-generated method stub

    }

}
