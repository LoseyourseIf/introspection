package xingyu.lu.review.db.multiple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义一个ContextHolder, 用于保存当前线程使用的数据源名
 *
 * @author xingyu.lu
 * @create 2018-06-08 11:41
 **/
public class DataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final String MASTER_DATASOURCE = "master";
    public static final String CLUSTER_DATASOURCE = "cluster";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 设置数据源名
    public static void setDB(String dbName) {
        log.debug("Swich To Datasource ----> {}", dbName);
        contextHolder.set(dbName);
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
