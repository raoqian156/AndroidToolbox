package me.xiaopan.android.easy.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 去掉标题栏，继承Easy系列Activity并在Activity上加上此注解，就会自动设置去掉标题栏
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoTitle {

}
