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

package me.xiaopan.java.easy.util;

/**
 * 提供一些常用的符号
 * @author xiaopan
 *
 */
public class General {
	/**
	 * 冒号
	 */
	public static final char COLON = ':';
	/**
	 * 分号
	 */
	public static final char SEMICOLON = ';';
	/**
	 * 逗号
	 */
	public static final char COMMA = ',';
	/**
	 * 引号
	 */
	public static final char QUOTES = '"';
	
	/**
	 * 计算斐波那契数列第N个元素的值
	 * @param number 第N个元素
	 * @return 第几个元素的值
	 */
	public static long countFBNQ(long number){
		long one = 0;
		long two = 1;
		long three = -1;
		for(long w = 2; w < number; w++){
			three = one + two;
			one = two;
			two = three;
		}
		return one + two;
	}
}