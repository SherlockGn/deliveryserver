package com.wsn.courier.db;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wsn.courier.db.DBUser.User;
import com.wsn.courier.user.userVo;

public class DBHelper {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "example.db";
	public static final String USER_TABLE_NAME = "user_table";
	public static final String[] USER_COLS = { User.USERNAME, User.PASSWORD,
			User.ISSAVED };
	
	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;

	public DBHelper(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
		establishDb();
	}
	private void establishDb() {
		if (this.db == null) {
			this.db = this.dbOpenHelper.getWritableDatabase();
		}
	}
	
	/**
	 * 插入一条记录
	 * @param map 要插入的记录
	 * @param tableName 表名
	 * @return 插入记录的id -1表示插入不成功
	 */
	public long insertOrUpdate(userVo user) {
		boolean isUpdate = false;
		List<userVo> list = queryAllUser();
		if(list == null){
			System.out.println("list == null");
		}else {
			System.out.println("list:"+list.size());
			for(userVo vo :list){
				if(user.getLoginName().equals(vo.getLoginName())){
					isUpdate = true;
					break;
				}
			}
		}
		long id = -1;
		if (isUpdate) {
			id = update(user);
		} else {
			if (db != null) {
				ContentValues values = new ContentValues();
				values.put(User.USERNAME, user.getLoginName());
				values.put(User.PASSWORD, user.getPassword());
				values.put(User.ISSAVED, user.getIssaved());
				id = db.insert(USER_TABLE_NAME, null, values);
			}
		}
		return id;
	}
	
	/**
	 * 删除一条记录
	 * @param userName 用户名
	 * @param tableName 表名
	 * @return 删除记录的id -1表示删除不成功
	 */
	public long delete(userVo user) {
		long id = db.delete(USER_TABLE_NAME, User.USERNAME + " = '" + user.getLoginName()
				+ "'", null);
		return id;
	}
	
	/**
	 * 更新一条记录
	 * @param 表名
	 * @return 更新过后记录的id -1表示更新不成功
	 */
	public long update(userVo user) {
		ContentValues values = new ContentValues();
		values.put(User.USERNAME, user.getLoginName());
		values.put(User.PASSWORD, user.getPassword());
		values.put(User.ISSAVED, user.getIssaved());
		long id = db.update(USER_TABLE_NAME, values, User.USERNAME + " = '"
				+ user.getLoginName() + "'", null);
		return id;
	}
	/**
	 * 根据用户名查询密码
	 * @param username 用户名
	 * @return Hashmap 查询的记录
	 */
	public String queryPasswordByName(String username) {
		String sql = "select * from " + USER_TABLE_NAME + " where "
				+ User.USERNAME + " = '" + username + "'";
		Cursor cursor = db.rawQuery(sql, null);
		String password = "";
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			password = cursor.getString(cursor.getColumnIndex(User.PASSWORD));
		}

		return password;
	}
	
	/**
	 * 记住密码选项框是否被选中
	 */
	public int queryIsSavedByName(String username) {
		String sql = "select * from " + USER_TABLE_NAME + " where "
				+ User.USERNAME + " = '" + username + "'";
		Cursor cursor = db.rawQuery(sql, null);
		int isSaved = 0;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			isSaved = cursor.getInt(cursor.getColumnIndex(User.ISSAVED));
		}
		return isSaved;
	}
	
	/**
	 * 查询所有用户名
	 * @param tableName 表名
	 * @return 所有记录的list集合
	 */
	public List<userVo> queryAllUser() {
		List<userVo> list = new ArrayList<userVo>();
		if (db != null) {
			Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null, null, null);
			if(cursor == null || (cursor.getCount()) < 1){
				if(cursor != null) cursor.close();
				return null;
			}
			while (cursor.moveToNext()) {
				userVo vo = new userVo();
				vo.setLoginName( cursor.getString(cursor.getColumnIndex(User.USERNAME)));
				vo.setPassword(cursor.getString(cursor.getColumnIndex(User.PASSWORD)));
				vo.setIssaved(cursor.getInt(cursor.getColumnIndex(User.ISSAVED)));
				list.add(vo);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 关闭数据库
	 */
	public void cleanup() {
		if (this.db != null) {
			this.db.close();
			this.db = null;
		}
	}
	
	/**
	 * 数据库辅助类
	 */
	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table " + USER_TABLE_NAME + " (" + User._ID
					+ " integer primary key," + User.USERNAME + " text, "
					+ User.PASSWORD + " text, " + User.ISSAVED + " INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
			onCreate(db);
		}

	}


}
