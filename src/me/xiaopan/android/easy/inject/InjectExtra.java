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
	public String stringDefaultValue() default "";
	public String charSequenceDefaultValue() default "";
}
