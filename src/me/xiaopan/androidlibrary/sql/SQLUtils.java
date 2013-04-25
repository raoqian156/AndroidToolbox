package me.xiaopan.androidlibrary.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.javalibrary.util.AnnotationUtils;
import me.xiaopan.javalibrary.util.ClassUtils;
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
		String tableValue = (String) AnnotationUtils.getDefaultAttributeValue(clas, Table.class);
		if(tableValue == null ){
			throw new NotFoundTableAnnotationException();
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("create table ");
		sql.append(tableValue + "(");
		
		//循环遍历所有字段
		boolean addSeparator = false;
		for(Field field : ClassUtils.getFileds(clas, true, true, true)){
			//处理列注解
			String columnValue = (String) AnnotationUtils.getDefaultAttributeValue(field, Column.class);
			if(columnValue != null){
				//处理数据类型注解
				String typeValue = (String) AnnotationUtils.getDefaultAttributeValue(field, DataType.class);
				if(typeValue != null){
					//如果需要添加分隔符
					if(addSeparator){
						sql.append(", ");
						addSeparator = false;
					}
					
					//追加当前字段的名字与类型
					sql.append(columnValue +" " + typeValue);
					
					//如果存在非空注解，就添加非空标识，否则处理默认值注解
					if(AnnotationUtils.contain(field, NotNull.class)){
						sql.append(" not null");
					}else{
						//处理默认值注解
						String defultValue = (String) AnnotationUtils.getDefaultAttributeValue(field, Default.class);
						if(defultValue != null){
							if(typeValue.startsWith("text") || typeValue.startsWith("varchar") || typeValue.startsWith("varchar2") || typeValue.startsWith("char")){
								sql.append(" defult '"+defultValue+"'");
							}else{
								sql.append(" defult "+defultValue);
							}
						}
					}
					
					//如果存在主键注解，就添加主键标识
					if(AnnotationUtils.contain(field, PrimaryKey.class)){
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
		for(Field field : ClassUtils.getFileds(clas, true, true, true)){
			//处理列注解
			String columnValue = (String) AnnotationUtils.getDefaultAttributeValue(field, Column.class);
			if(columnValue != null){
				//处理数据类型注解
				if((String) AnnotationUtils.getDefaultAttributeValue(field, DataType.class) != null){
					try {
						field.setAccessible(true);
						Object result = field.get(object);
						if(result != null){
							contentValues.put(columnValue, result.toString());
						}else{
							contentValues.putNull(columnValue);
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
			for(Field field : ClassUtils.getFileds(clas, true, true, true)){
				//如果不是静态的
				if(!Modifier.isStatic(field.getModifiers())){
					//获取列注解
					String columnValue = (String) AnnotationUtils.getDefaultAttributeValue(field, Column.class);
					//如果有注解
					if(columnValue != null){
						//获取列的值
						String value = cursor.getString(cursor.getColumnIndex(columnValue));
						//如果值不为null
						if(value != null){
							Object object = null;
							if(field.getType() == byte.class){
								object = Byte.valueOf(value);
							}else if(field.getType() == short.class){
								object = Short.valueOf(value);
							}else if(field.getType() == int.class){
								object = Integer.valueOf(value);
							}else if(field.getType() == long.class){
								object = Long.valueOf(value);
							}else if(field.getType() == float.class){
								object = Float.valueOf(value);
							}else if(field.getType() == double.class){
								object = Double.valueOf(value);
							}else if(field.getType() == char.class){
								object = value.charAt(0);
							}else if(field.getType() == boolean.class){
								object = Boolean.valueOf(value);
							}else if(field.getType() == String.class){
								object = value;
							}else{
								object = ClassUtils.getValueOfMethod(field.getType(), new Class<?>[]{String.class}).invoke(null, new Object[]{value});
							}
							field.setAccessible(true);
							field.set(newInstance, object);
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