package xingyu.lu.review.db.multiple.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2018-06-08 11:47
 **/
@Configuration
public class DynamicDs {

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Bean(name = "dynamicDatasource")
    public DataSource dataSource(@Qualifier("clusterDataSource") DataSource clusterDataSource,
                                 @Qualifier("masterDataSource") DataSource masterDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);

        // 配置多数据源
        Map<Object,Object> dsMap = new HashMap<>(5);
        dsMap.put("cluster", clusterDataSource);
        dsMap.put("master", masterDataSource);

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }
}
