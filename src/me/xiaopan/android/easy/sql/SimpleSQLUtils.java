package me.xiaopan.android.easy.sql;

import android.database.sqlite.SQLiteDatabase;

public class SimpleSQLUtils {
	public boolean update(SQLiteDatabase writableDatabase, Object object){
		int result = 0;
//		try {
//			DomainParser domainParser = new DomainParser(object);
//			result = writableDatabase.update(domainParser.getTable(), SQLUtils.getContentValues(object), "id=?", new String[]{message.getId()});
//		} catch (NotFoundTableAnnotationException e) {
//			e.printStackTrace();
//		}
		writableDatabase.close();
		return result > 0;
	}
}
