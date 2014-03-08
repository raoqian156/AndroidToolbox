package me.xiaopan.android.easy.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.StringUtils;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Domain解析器
 */
public class DomainParser {
	private Object object;
	private String tableName;
	private Field parimaryKeyField;

	public DomainParser(Object object) {
		this.object = object;
		this.tableName = parseTable(object.getClass());
		this.parimaryKeyField = parsePrimarkKeyField(object.getClass());
		if(tableName == null){
			throw new IllegalArgumentException("没有找到Table注解");
		}
	}
	
	/**
	 * 获取表名
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 获取主键名称
	 * @return
	 */
	public String getPrimaryKeyName(){
		return parseColumnName(parimaryKeyField);
	}
	
	/**
	 * 获取主键值
	 * @param defaultValue
	 * @return
	 */
	public String getPrimaryKeyValue(String defaultValue){
		return parseColumnValue(parimaryKeyField, object, defaultValue);
	}
	
	/**
	 * 获取主键值
	 * @param defaultValue
	 * @return
	 */
	public String getPrimaryKeyValue(){
		return parseColumnValue(parimaryKeyField, object);
	}
	
	/**
	 * 获取ContentValues
	 * @return
	 */
	public ContentValues getContentValues(){
		return parseContentValues(object);
	}

	/**
	 * 解析主键
	 * @param classs
	 * @return
	 */
	public static String parseTable(Class<?> classs){
		Table table = classs.getAnnotation(Table.class);
		return table != null?table.value():null;
	}
	
	/**
	 * 解析主键字段
	 * @param classs
	 * @return
	 */
	public static final Field parsePrimarkKeyField(Class<?> classs){
		Field primaryKeyField = null;
		while(classs != null){
			for(Field field : classs.getDeclaredFields()){
				if(field.isAnnotationPresent(PrimaryKey.class)){
					primaryKeyField = field;
					break;
				}
			}
			if(primaryKeyField != null){
				break;
			}else{
				classs = classs.getSuperclass();
			}
		}
		return primaryKeyField;
	}
	
	/**
	 * 解析列名
	 * @param field
	 * @return
	 */
	public static final String parseColumnName(Field field){
		Column column =  field.getAnnotation(Column.class);
		if(column != null){
			if(StringUtils.isNotEmpty(column.value())){
				return column.value();
			}else{
				return field.getName();
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 解析列值
	 * @param field
	 * @param object
	 * @param defaultValue
	 * @return
	 */
	public static final String parseColumnValue(Field field, Object object, String defaultValue){
		try {
			field.setAccessible(true);
			Object result = field.get(object);
			if(result != null){
				defaultValue = result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	
	/**
	 * 解析列值
	 * @param field
	 * @param object
	 * @return
	 */
	public static final String parseColumnValue(Field field, Object object){
		return parseColumnValue(field, object, null);
	}
	
	/**
	 * 解析创建表的SQL
	 * @param clas
	 * @return
	 */
	public static String parseCreateTableSQL(Class<?> clas){
		String table = parseTable(clas);
		
		if(StringUtils.isEmpty(table)){
			throw new IllegalArgumentException("没有找到Table注解");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("create table ");
		sql.append(table + "(");
		
		boolean addSeparator = false;
		for(Field field : ReflectUtils.getFields(clas, true, true, true, true)){
			if(field.isAnnotationPresent(Column.class)){
				DataType dataType = field.getAnnotation(DataType.class);
				if(dataType != null && StringUtils.isNotEmpty(dataType.value())){
					if(addSeparator){//如果需要添加分隔符
						sql.append(", ");
						addSeparator = false;
					}
					
					sql.append(parseColumnName(field) +" " + dataType.value());//追加当前字段的名字与类型
					
					//如果存在非空注解，就添加非空标识，否则处理默认值注解
					if(field.getAnnotation(NotNull.class) != null){
						sql.append(" not null");
					}else{
						//处理默认值注解
						Default defultValue = field.getAnnotation(Default.class);
						if(defultValue != null && StringUtils.isNotEmpty(defultValue.value())){
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
	}
	
	/**
	 * 解析ContentValues
	 * @param object domain对象
	 * @return ContentValues
	 */
	public static final ContentValues parseContentValues(Object object){
		Class<?> clas = object.getClass();
		ContentValues contentValues = new ContentValues();
		for(Field field : ReflectUtils.getFields(clas, true, true, true, true)){
			if(field.isAnnotationPresent(Column.class)){
				//处理数据类型注解
				DataType dataType = field.getAnnotation(DataType.class);
				if(dataType != null && StringUtils.isNotEmpty(dataType.value())){
					try {
						field.setAccessible(true);
						Object result = field.get(object);
						if(result != null){
							contentValues.put(parseColumnName(field), result.toString());
						}else{
							contentValues.putNull(parseColumnName(field));
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> cursorToObject(Cursor cursor, Class<T> clas) throws InstantiationException, IllegalAccessException{
		List<T> objectList = new ArrayList<T>();
		while(cursor.moveToNext()){
			//根据class创建新的实例
			Object newInstance = clas.newInstance();
			//遍历所有字段
			for(Field field : ReflectUtils.getFields(clas, true, true, true, true)){
				//如果不是静态的
				if(!Modifier.isStatic(field.getModifiers())){
					if(field.isAnnotationPresent(Column.class)){
						field.setAccessible(true);
						try {
							if(field.getType() == byte.class){
								field.set(newInstance, Byte.valueOf(cursor.getString(cursor.getColumnIndex(parseColumnName(field)))));
							}else if(field.getType() == short.class){
								field.set(newInstance, cursor.getShort(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == int.class){
								field.set(newInstance, cursor.getInt(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == long.class){
								field.set(newInstance, cursor.getLong(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == float.class){
								field.set(newInstance, cursor.getFloat(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == double.class){
								field.set(newInstance, cursor.getDouble(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == char.class){
								field.set(newInstance, cursor.getString(cursor.getColumnIndex(parseColumnName(field))).charAt(0));
							}else if(field.getType() == boolean.class){
								field.set(newInstance, cursor.getBlob(cursor.getColumnIndex(parseColumnName(field))));
							}else if(field.getType() == String.class){
								field.set(newInstance, cursor.getString(cursor.getColumnIndex(parseColumnName(field))));
							}else{
								field.set(newInstance, ReflectUtils.getValueOfMethod(field.getType(), new Class<?>[]{String.class}).invoke(null, new Object[]{cursor.getString(cursor.getColumnIndex(parseColumnName(field)))}));
							}
						} catch (Exception e) {
							e.printStackTrace();
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
}