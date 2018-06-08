package xingyu.lu.review.db.multiple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义一个javax.sql.DataSource接口的实现，
 * 继承Spring预先实现好的父类AbstractRoutingDataSource
 *
 * @author xingyu.lu
 * @create 2018-06-08 11:43
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("Current DataSource Is ----> {}", DataSourceContextHolder.getDB());

        return DataSourceContextHolder.getDB();
    }
}
