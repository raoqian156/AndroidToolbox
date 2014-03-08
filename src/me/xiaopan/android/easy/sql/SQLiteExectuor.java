package me.xiaopan.android.easy.sql;

import java.util.List;

import me.xiaopan.java.easy.util.StringUtils;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteExectuor {
	private SQLiteOpenHelper sqLiteOpenHelper;
	
	public SQLiteExectuor(SQLiteOpenHelper sqLiteOpenHelper) {
		this.sqLiteOpenHelper = sqLiteOpenHelper;
	}
	
	/**
	 * 插入数据
	 * @param table
	 * @param nullColumnHack
	 * @param values
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long insert(String table, String nullColumnHack, ContentValues values){
		SQLiteDatabase writableDatabase = sqLiteOpenHelper.getWritableDatabase();
		long result = writableDatabase.insert(table, nullColumnHack, values);
		writableDatabase.close();
		return result;
	}
	
	/**
	 * 插入数据
	 * @param writableDatabase
	 * @param object
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long insert(Object object){
		DomainParser domainParser = new DomainParser(object);
		return insert(domainParser.getTableName(), null, domainParser.getContentValues());
	}
	
	/**
	 * 删除数据
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long delete(String table, String whereClause, String[] whereArgs){
		SQLiteDatabase writableDatabase = sqLiteOpenHelper.getWritableDatabase();
		int result = writableDatabase.delete(table, whereClause, whereArgs);
		writableDatabase.close();
		return result;
	}
	
	/**
	 * 删除数据
	 * @param object
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long delete(Class<?> classs, String whereClause, String[] whereArgs){
		String table = DomainParser.parseTable(classs);
		if(StringUtils.isEmpty(table)){
			throw new IllegalArgumentException("没有找到Table注解");
		}
		return delete(table, whereClause, whereArgs);
	}
	
	/**
	 * 根据主键删除数据
	 * @param object
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long deleteByPrimaryKey(Object object){
		DomainParser domainParser = new DomainParser(object);
		return delete(domainParser.getTableName(), new StringBuffer(domainParser.getPrimaryKeyName()).append("=?").toString(), new String[]{domainParser.getPrimaryKeyValue()});
	}

	/**
	 * 更新数据
	 * @param object
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return the number of rows affected
	 */
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		SQLiteDatabase writableDatabase = sqLiteOpenHelper.getWritableDatabase();
		int result = writableDatabase.update(table, values, whereClause, whereArgs);
		writableDatabase.close();
		return result;
	}

	/**
	 * 根据主键更新数据
	 * @param object
	 * @return true：更新成功；false：更新失败
	 */
	public boolean updateByPrimaryKey(Object object){
		int result = 0;
		DomainParser domainParser = new DomainParser(object);
		SQLiteDatabase writableDatabase = sqLiteOpenHelper.getWritableDatabase();
		result = writableDatabase.update(domainParser.getTableName(), domainParser.getContentValues(), new StringBuffer(domainParser.getPrimaryKeyName()).append("=?").toString(), new String[]{domainParser.getPrimaryKeyValue()});
		writableDatabase.close();
		return result > 0;
	}
	
	/**
	 * 获取所有数据
	 * @param classs
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> query(Class<T> classs, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws InstantiationException, IllegalAccessException{
		String table = DomainParser.parseTable(classs);
		if(StringUtils.isEmpty(table)){
			throw new IllegalArgumentException("没有找到Table注解");
		}
		SQLiteDatabase writableDatabase = sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = writableDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		List<T> objectList = null;
		try {
			objectList = DomainParser.cursorToObject(cursor, classs);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw e;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw e;
		}finally{
			cursor.close();
			writableDatabase.close();
		}
		return objectList;
	}
	
	/**
	 * 获取所有数据
	 * @param classs
	 * @param columns
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> query(Class<T> classs, String[] columns) throws InstantiationException, IllegalAccessException{
		return query(classs, columns, null, null, null, null, null);
	}
	
	/**
	 * 获取所有数据
	 * @param classs
	 * @param selection
	 * @param selectionArgs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> query(Class<T> classs, String selection, String[] selectionArgs) throws InstantiationException, IllegalAccessException{
		return query(classs, null, selection, selectionArgs, null, null, null);
	}
	
	/**
	 * 获取所有数据
	 * @param classs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> query(Class<T> classs, String groupBy, String having, String orderBy) throws InstantiationException, IllegalAccessException{
		return query(classs, null, null, null, groupBy, having, orderBy);
	}
	
	/**
	 * 获取所有数据
	 * @param classs
	 * @param orderBy
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> query(Class<T> classs, String orderBy) throws InstantiationException, IllegalAccessException{
		return query(classs, null, null, null, null, null, orderBy);
	}
}
