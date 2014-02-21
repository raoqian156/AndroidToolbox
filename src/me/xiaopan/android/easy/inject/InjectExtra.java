package me.xiaopan.android.easy.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入扩展参数
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InjectExtra {
	public String value();
	public byte byteDefaultValue() default 0;
	public short shortDefaultValue() default 0;
	public int intDefaultValue() default 0;
	public long longDefaultValue() default 0;
	public char charDefaultValue() default 0;
	public float floatDefaultValue() default 0;
	public double doubleDefaultValue() default 0;
	public boolean booleanDefaultValue() default false;
}
