package xingyu.lu.review.db.multiple.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * (╯‵□′)╯︵┻━┻
 * 数据源配置类，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 *
 * @author xingyu.lu
 * @return
 * @date 18/6/11 14:35
 */
@Configuration
@MapperScan(basePackages = "xingyu.lu.review.db.multiple.dao.*")
public class DataSourceConfigurer {

    public static final String MASTER_DB_NAME = "master";
    public static final String CLUSTER_DB_NAME = "cluster";

    /**
     * master DataSource
     *
     * @return javax.sql.DataSource;
     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有三个 DataSource Bean，该注解可用于 master
     * 或 slave DataSource Bean, 但不能用于 dynamicDataSource Bean, 否则会产生循环调用
     * @ConfigurationProperties 注解用于从 application.properties 文件中读取配置
     */
    @Bean("masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * cluster DataSource
     *
     * @return javax.sql.DataSource
     */
    @Bean("clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
    public DataSource clusterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * Dynamic data source.
     *
     * @return javax.sql.DataSource
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("clusterDataSource") DataSource cluster
    ) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(MASTER_DB_NAME, master);
        dataSourceMap.put(CLUSTER_DB_NAME, cluster);

        // 将 master 数据源作为默认指定的数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(master);
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
        return dynamicRoutingDataSource;
    }

    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return SqlSessionFactoryBean
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource
                                                               dynamicDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean;
    }

    /**
     * 配置事务管理，如果使用到事务需要注入该 Bean，否则事务不会生效
     * 在需要的地方加上 @Transactional(rollbackFor = Exception.class)注解即可
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource
                                                                 dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}

