/**
 * 
 */
package me.xiaopan.android.easy.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关闭注入功能
 */
@Target(ElementType.TYPE)
 @Retention(RetentionPolicy.RUNTIME)
 @Inherited
public @interface DisableInject {

}