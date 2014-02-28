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

package me.xiaopan.android.easy.util.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.content.Context;

/**
 * 注入Preference
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InjectPreference {
	/**
	 * 参数名称
	 * @return
	 */
	public String value();
	
	/**
	 * SharedPreferences名称
	 * @return
	 */
	public String sharedPreferencesName() default "";
	
	/**
	 * ShardPreferences的模式
	 * @return
	 */
	public int mode() default Context.MODE_PRIVATE;
	
	/**
	 * boolean类型参数的默认值
	 * @return
	 */
	public boolean booleanDefaultValue() default false;
	
	/**
	 * float类型参数的默认值
	 * @return
	 */
	public float floatDefaultValue() default 0;
	
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
	 * String类型参数的默认值
	 * @return
	 */
	public String stringDefaultValue() default "";
}