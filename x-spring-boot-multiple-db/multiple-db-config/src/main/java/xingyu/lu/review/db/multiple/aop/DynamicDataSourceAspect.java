package xingyu.lu.review.db.multiple.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xingyu.lu.review.db.multiple.config.DataSourceContextHolder;

import java.lang.reflect.Method;

/**
 * @author xingyu.lu
 * @create 2018-06-08 11:59
 **/
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(SwitchOver)")
    public void beforeSwitchDS(JoinPoint point) {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.MASTER_DATASOURCE;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@SwitchOver注解
            if (method.isAnnotationPresent(SwitchOver.class)) {
                SwitchOver annotation = method.getAnnotation(SwitchOver.class);
                // 取出注解中的数据源名
                dataSource = annotation.db();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);

    }


    @After("@annotation(SwitchOver)")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDB();
    }
}
