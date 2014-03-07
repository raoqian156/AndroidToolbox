package me.xiaopan.android.easy.sql;

import java.lang.reflect.Field;

import me.xiaopan.android.easy.sql.SQLUtils.NotFoundTableAnnotationException;
import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.StringUtils;
import android.content.ContentValues;

/**
 * domain解析器
 */
public class DomainParser {
	private Object object;

	public DomainParser(Object object) {
		this.object = object;
	}
	
	/**
	 * 解析主键
	 * @param classs
	 * @return
	 */
	public String getTable(){
		Table table = object.getClass().getAnnotation(Table.class);
		return table != null?table.value():null;
	}
	
	/**
	 * 解析主键
	 * @param classs
	 * @return
	 */
	public String getPrimaryKey(){
		Table table = object.getClass().getAnnotation(Table.class);
		return table != null?table.value():null;
	}
	
	/**
	 * 获取创建表的SQL
	 * @param clas
	 * @return
	 */
	public String getCreateTableSQL(Class<?> clas){
		//处理表注解
		Table table = clas.getAnnotation(Table.class);
		if(table != null && StringUtils.isNotEmpty(table.value())){
			StringBuffer sql = new StringBuffer();
			sql.append("create table ");
			sql.append(table.value() + "(");
			
			//循环遍历所有字段
			boolean addSeparator = false;
			for(Field field : ReflectUtils.getFields(clas, true, true, true, true)){
				//处理列注解
				Column column = field.getAnnotation(Column.class);
				if(column != null && StringUtils.isNotEmpty(column.value())){
					//处理数据类型注解
					DataType dataType = field.getAnnotation(DataType.class);
					if(dataType != null && StringUtils.isNotEmpty(dataType.value())){
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
		}else{
			return null;
		}
	}
	
	/**
	 * 获取ContentValues
	 * @param object domain对象
	 * @return ContentValues
	 * @throws NotFoundTableAnnotationException
	 */
	public ContentValues getContentValues(Object object) throws NotFoundTableAnnotationException{
		Class<?> clas = object.getClass();
		ContentValues contentValues = new ContentValues();
		for(Field field : ReflectUtils.getFields(clas, true, true, true, true)){
			//处理列注解
			Column column = field.getAnnotation(Column.class);
			if(column != null && StringUtils.isNotEmpty(column.value())){
				//处理数据类型注解
				DataType dataType = field.getAnnotation(DataType.class);
				if(dataType != null && StringUtils.isNotEmpty(dataType.value())){
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
}