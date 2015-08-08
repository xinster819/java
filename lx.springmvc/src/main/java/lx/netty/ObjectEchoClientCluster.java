package lx.netty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;

public class ObjectEchoClientCluster implements PooledObjectFactory<ObjectEchoClient> {

    List<Host> host = new ArrayList<Host>();

    @Override
    public PooledObject<ObjectEchoClient> makeObject() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void destroyObject(PooledObject<ObjectEchoClient> p) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validateObject(PooledObject<ObjectEchoClient> p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void activateObject(PooledObject<ObjectEchoClient> p) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void passivateObject(PooledObject<ObjectEchoClient> p) throws Exception {
        // TODO Auto-generated method stub

    }

}
