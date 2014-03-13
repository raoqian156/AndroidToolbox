package me.xiaopan.android.easy.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全屏，继承Easy系列Activity并在Activity上加上此注解，就会自动设置Activity全屏
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullScreen {

}