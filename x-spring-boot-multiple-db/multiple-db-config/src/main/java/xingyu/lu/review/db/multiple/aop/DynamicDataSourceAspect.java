package xingyu.lu.review.db.multiple.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xingyu.lu.review.db.multiple.config.DynamicDataSourceContextHolder;

/**
 * (╯‵□′)╯︵┻━┻
 * 动态数据源切换的切面，切 @TargetDataSource 注解，实现数据源切换
 *
 * @author xingyu.lu
 * @date 18/6/11 14:38
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * Switch DataSource
     *
     * @param point
     * @param switchOver
     */
    @Before("@annotation(switchOver))")
    public void switchDataSource(JoinPoint point, SwitchOver switchOver) {
        if (!DynamicDataSourceContextHolder.containDataSourceKey(switchOver.db())) {
            logger.error("DataSource [{}] doesn't exist, use default DataSource [{}]", switchOver.db());
        } else {
            DynamicDataSourceContextHolder.setDataSourceKey(switchOver.db());
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }
    }

    /**
     * Restore DataSource
     *
     * @param point
     * @param switchOver
     */
    @After("@annotation(switchOver))")
    public void restoreDataSource(JoinPoint point, SwitchOver switchOver) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

}
