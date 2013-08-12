/*
 * Copyright 2013 Peng fei Pan
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
package me.xiaopan.easyandroid.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyjava.util.ClassUtils;
import me.xiaopan.easyjava.util.StringUtils;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * SQLite工具箱
 * @author xiaopan
 *
 */
public class SQLUtils {
	/**
	 * 获取创建表的SQL
	 * @param clas
	 * @return
	 * @throws NotFoundTableAnnotationException
	 */
	public static String getCreateTableSQL(Class<?> clas) throws NotFoundTableAnnotationException{
		//处理表注解
		Table table = clas.getAnnotation(Table.class);
		if(table != null && StringUtils.isNotNullAndEmpty(table.value())){
			StringBuffer sql = new StringBuffer();
			sql.append("create table ");
			sql.append(table.value() + "(");
			
			//循环遍历所有字段
			boolean addSeparator = false;
			for(Field field : ClassUtils.getFields(clas, true, true, true)){
				//处理列注解
				Column column = field.getAnnotation(Column.class);
				if(column != null && StringUtils.isNotNullAndEmpty(column.value())){
					//处理数据类型注解
					DataType dataType = field.getAnnotation(DataType.class);
					if(dataType != null && StringUtils.isNotNullAndEmpty(dataType.value())){
						//如果需要添加分隔符
						if(addSeparator){
							sql.append(", ");
							addSeparator = false;
						}
						
						//追加当前字段的名字与类型
						sql.append(column.value() +" " + dataType.value());
						
						//如果存在非空注解，就添加非空标识，否则处理默认值注解
						if(field.getAnnotation(NotNull.class) != null){
							sql.append(" not null");
						}else{
							//处理默认值注解
							Default defultValue = field.getAnnotation(Default.class);
							if(defultValue != null && StringUtils.isNotNullAndEmpty(defultValue.value())){
								if(dataType.value().startsWith("text") || dataType.value().startsWith("varchar") || dataType.value().startsWith("varchar2") || dataType.value().startsWith("char")){
									sql.append(" defult '"+defultValue.value()+"'");
								}else{
									sql.append(" defult "+defultValue.value());
								}
							}
						}
						
						//如果存在主键注解，就添加主键标识
						if(field.getAnnotation(PrimaryKey.class) != null){
							sql.append(" primary key");
						}
						
						//下次循环时添加分隔符
						addSeparator = true;
					}
				}
			}
			
			//添加字段结束符
			sql.append(")");
			
			return sql.toString();
		}else{
			throw new NotFoundTableAnnotationException();
		}
	}
	
	/**
	 * 获取ContentValues
	 * @param object domain对象
	 * @return ContentValues
	 * @throws NotFoundTableAnnotationException
	 */
	public static ContentValues getContentValues(Object object) throws NotFoundTableAnnotationException{
		Class<?> clas = object.getClass();
		ContentValues contentValues = new ContentValues();
		for(Field field : ClassUtils.getFields(clas, true, true, true)){
			//处理列注解
			Column column = field.getAnnotation(Column.class);
			if(column != null && StringUtils.isNotNullAndEmpty(column.value())){
				//处理数据类型注解
				DataType dataType = field.getAnnotation(DataType.class);
				if(dataType != null && StringUtils.isNotNullAndEmpty(dataType.value())){
					try {
						field.setAccessible(true);
						Object result = field.get(object);
						if(result != null){
							contentValues.put(column.value(), result.toString());
						}else{
							contentValues.putNull(column.value());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return contentValues;
	}
	
	/**
	 * 将给定的Cursor转换为给定的Class列表，请注意：此方法结束不会关闭Cursor
	 * @param cursor 给定的Cursor
	 * @param clas 给定的Class
	 * @return 给定的Class列表
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> cursorToObject(Cursor cursor, Class<T> clas) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException{
		List<T> objectList = new ArrayList<T>();
		while(cursor.moveToNext()){
			//根据class创建新的实例
			Object newInstance = clas.newInstance();
			//遍历所有字段
			for(Field field : ClassUtils.getFields(clas, true, true, true)){
				//如果不是静态的
				if(!Modifier.isStatic(field.getModifiers())){
					//获取列注解
					Column column = field.getAnnotation(Column.class);
					if(column != null && StringUtils.isNotNullAndEmpty(column.value())){
						field.setAccessible(true);
						if(field.getType() == byte.class){
							field.set(newInstance, Byte.valueOf(cursor.getString(cursor.getColumnIndex(column.value()))));
						}else if(field.getType() == short.class){
							field.set(newInstance, cursor.getShort(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == int.class){
							field.set(newInstance, cursor.getInt(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == long.class){
							field.set(newInstance, cursor.getLong(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == float.class){
							field.set(newInstance, cursor.getFloat(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == double.class){
							field.set(newInstance, cursor.getDouble(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == char.class){
							field.set(newInstance, cursor.getString(cursor.getColumnIndex(column.value())).charAt(0));
						}else if(field.getType() == boolean.class){
							field.set(newInstance, cursor.getBlob(cursor.getColumnIndex(column.value())));
						}else if(field.getType() == String.class){
							field.set(newInstance, cursor.getString(cursor.getColumnIndex(column.value())));
						}else{
							field.set(newInstance, ClassUtils.getValueOfMethod(field.getType(), new Class<?>[]{String.class}).invoke(null, new Object[]{cursor.getString(cursor.getColumnIndex(column.value()))}));
						}
					}
				}
			}
			if(newInstance != null){
				objectList.add((T) newInstance);
			}
		}
		return objectList;
	}
	
	/**
	 * 没有找到表注解异常
	 */
	public static class NotFoundTableAnnotationException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
