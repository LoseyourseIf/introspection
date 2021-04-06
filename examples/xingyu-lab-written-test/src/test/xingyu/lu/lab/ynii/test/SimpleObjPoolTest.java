package xingyu.lu.lab.ynii.test;

import org.junit.Test;
import xingyu.lu.lab.ynii.PoolObj;
import xingyu.lu.lab.ynii.SimpleObjPool;

/**
 * @author xingyu.lu
 * @create 2021-04-01 10:32
 **/
public class SimpleObjPoolTest {

    @Test
    public void testPool() {
        SimpleObjPool pool = SimpleObjPool.init(PoolObj.class, 10);
        Object ojb = pool.getOjb();
        System.out.println(pool);
        pool.releaseOjb(ojb);
        System.out.println(pool);
    }

}
