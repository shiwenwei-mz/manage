package com.sys.manage.config.druid;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.*;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect implements Ordered {
    @Pointcut(value = "@annotation(com.sys.manage.config.druid.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        DataSource datasource = currentMethod.getAnnotation(DataSource.class);
        if (datasource != null && StringUtils.hasText(datasource.name())) {
            DynamicDataSourceContextHolder.setDataSourceLookupKey(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        } else {
            DynamicDataSourceContextHolder.setDataSourceLookupKey("master");
            log.debug("设置数据源为：dataSourceCurrent");
        }
        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DynamicDataSourceContextHolder.clearDataSourceLookupKey();
        }
    }

    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
