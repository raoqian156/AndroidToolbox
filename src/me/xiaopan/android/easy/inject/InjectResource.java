package me.xiaopan.android.easy.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入资源，支持以下类型的资源：
 * <br>boolean
 * <br>String
 * <br>String[]
 * <br>Integer
 * <br>Integer[]
 * <br>Drawable
 * <br>ColorStateList
 * <br>Animation
 * <br>Movie
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InjectResource {
	public int value();
}
