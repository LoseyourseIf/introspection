package xingyu.lu.lab.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-04-01 10:32
 **/
public class SimpleObjPool<T> {

    private Class<T> clazz;
    private List<T> sObjList = new ArrayList<>();
    private List<Boolean> inUseFlag = new ArrayList<>();
    private int require;

    public SimpleObjPool(Class<T> clazz, int require) {
        this.clazz = clazz;
        this.require = require;
    }

    public static SimpleObjPool init(Class type, int size) {
        SimpleObjPool pool = new SimpleObjPool(type, size);
        pool.sObjList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pool.addObj();
        }
        return pool;
    }

    private void addObj() {
        T obj;
        try {
            obj = clazz.newInstance();
            this.sObjList.add(obj);
            this.inUseFlag.add(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getOjb() {
        for (int index = 0; index < this.sObjList.size(); index++)
            if (null != this.sObjList.get(index)) {
                this.inUseFlag.set(index, true);
                return sObjList.get(index);
            }
        return null;
    }

    public boolean releaseOjb(T x) {
        int index = this.sObjList.indexOf(x);
        if (index == -1)
            return false;
        if (null != this.sObjList.get(index)) {
            this.inUseFlag.set(index, false);
            return true;
        }
        return false;
    }
}
