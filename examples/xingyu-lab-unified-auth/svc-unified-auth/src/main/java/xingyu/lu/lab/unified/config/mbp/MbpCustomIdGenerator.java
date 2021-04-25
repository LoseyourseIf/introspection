package xingyu.lu.lab.unified.config.mbp;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;

/**
 * @author xingyu.lu
 * @create 2021-04-23 14:22
 **/

public class MbpCustomIdGenerator extends TwitterSnowflakeIdWorker implements IdentifierGenerator {
    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public MbpCustomIdGenerator(long workerId, long dataCenterId) {
        super(workerId, dataCenterId);
    }

    @Override
    public Number nextId(Object entity) {
        return super.nextId();
    }
}
