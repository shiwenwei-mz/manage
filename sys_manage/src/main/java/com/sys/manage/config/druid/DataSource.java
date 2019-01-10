package com.sys.manage.config.druid;

import java.lang.annotation.*;

/**
 *
 * 多数据源标识
 * Created by oycx
 * Date: 2018-4-17
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {

	String name() default "";
}
