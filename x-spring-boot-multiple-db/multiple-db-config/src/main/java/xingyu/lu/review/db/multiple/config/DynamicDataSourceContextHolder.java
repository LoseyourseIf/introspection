package xingyu.lu.review.db.multiple.config;


import java.util.ArrayList;
import java.util.List;

/**
 * (╯‵□′)╯︵┻━┻
 * 该类为数据源上下文配置，用于切换数据源
 *
 * @author xingyu.lu
 * @date 18/6/11 14:35
 */
public class DynamicDataSourceContextHolder {

//    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            // 将 master 数据源的 key 作为默认数据源的 key
            return DataSourceConfigurer.MASTER_DB_NAME;
        }
    };

    /**
     * All DataSource List
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

    /**
     * Check if give DataSource is in current DataSource list
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}
