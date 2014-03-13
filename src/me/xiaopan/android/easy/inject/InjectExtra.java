/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.easy.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入扩展参数
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectExtra {
	/**
	 * 参数名称
	 * @return
	 */
	public String value();
	
	/**
	 * bytebyte类型参数的默认值
	 * @return
	 */
	public byte byteDefaultValue() default 0;
	
	/**
	 * short类型参数的默认值
	 * @return
	 */
	public short shortDefaultValue() default 0;
	
	/**
	 * int类型参数的默认值
	 * @return
	 */
	public int intDefaultValue() default 0;
	
	/**
	 * long类型参数的默认值
	 * @return
	 */
	public long longDefaultValue() default 0;
	
	/**
	 * char类型参数的默认值
	 * @return
	 */
	public char charDefaultValue() default 0;
	
	/**
	 * float类型参数的默认值
	 * @return
	 */
	public float floatDefaultValue() default 0;
	
	/**
	 * double类型参数的默认值
	 * @return
	 */
	public double doubleDefaultValue() default 0;
	
	/**
	 * boolean类型参数的默认值
	 * @return
	 */
	public boolean booleanDefaultValue() default false;
	
	/**
	 * String类型参数的默认值
	 * @return
	 */
	public String stringDefaultValue() default "";
	
	/**
	 * CharSequence类型参数的默认值
	 * @return
	 */
	public String charSequenceDefaultValue() default "";
}
