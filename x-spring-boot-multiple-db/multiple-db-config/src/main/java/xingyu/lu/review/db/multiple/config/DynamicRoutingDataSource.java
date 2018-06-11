package xingyu.lu.review.db.multiple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * (╯‵□′)╯︵┻━┻
 * 该类继承自 AbstractRoutingDataSource 类，
 * 在访问数据库时会调用该类的 determineCurrentLookupKey()
 * 方法获取数据库实例的 key
 *
 * @author xingyu.lu
 * @date 18/6/11 14:36
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Set dynamic DataSource to Application Context
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("Current DataSource is [{}]", DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }
}
