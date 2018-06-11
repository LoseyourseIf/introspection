package xingyu.lu.review.db.multiple.aop;

import org.springframework.core.annotation.Order;
import xingyu.lu.review.db.multiple.config.DataSourceConfigurer;

import java.lang.annotation.*;

/**
 * (╯‵□′)╯︵┻━┻
 * 数据源注解，用于设置数据源的 key，指定使用哪个数据源
 *
 * @author xingyu.lu
 * @date 18/6/11 14:37
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Order(-1)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwitchOver {
    String db() default DataSourceConfigurer.MASTER_DB_NAME;
}
