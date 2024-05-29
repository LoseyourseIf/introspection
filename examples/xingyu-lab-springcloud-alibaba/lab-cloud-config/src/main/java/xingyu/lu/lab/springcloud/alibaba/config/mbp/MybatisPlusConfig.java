package xingyu.lu.lab.springcloud.alibaba.config.mbp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Mybatis-Plus 配置类
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-28  11:19
 */
@Configuration
@MapperScan("xingyu.lu.lab.springcloud.alibaba.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //[分页插件] 如果是不同类型的库，请不要指定DbType，其会自动判断。
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //[非法SQL拦截插件] 用于拦截和检查非法SQL语句。如全表更新、删除操作，以及对索引的检查等。
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return interceptor;
    }
}
