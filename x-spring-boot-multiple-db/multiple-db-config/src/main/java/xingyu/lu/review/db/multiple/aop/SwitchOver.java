package xingyu.lu.review.db.multiple.aop;

import xingyu.lu.review.db.multiple.config.DataSourceContextHolder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 用于在编码时指定方法使用哪个数据源
 *
 * @author xingyu.lu
 * @create 2018-06-08 11:58
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface SwitchOver {
    String db() default DataSourceContextHolder.MASTER_DATASOURCE;
}
