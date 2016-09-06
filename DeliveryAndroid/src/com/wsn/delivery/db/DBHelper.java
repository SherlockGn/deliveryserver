package com.wsn.delivery.db;

import java.util.ArrayList;
import java.util.List;

import com.wsn.delivery.courier.CourierVo;
import com.wsn.delivery.db.DBUser.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
	 * ����һ����¼
	 * @param map Ҫ����ļ�¼
	 * @param tableName ����
	 * @return �����¼��id -1��ʾ���벻�ɹ�
	 */
	public long insertOrUpdate(CourierVo user) {
		boolean isUpdate = false;
		List<CourierVo> list = queryAllUser();
		if(list == null){
			System.out.println("list == null");
		}else {
			System.out.println("list:"+list.size());
			for(CourierVo vo :list){
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
	 * ɾ��һ����¼
	 * @param userName �û���
	 * @param tableName ����
	 * @return ɾ����¼��id -1��ʾɾ�����ɹ�
	 */
	public long delete(CourierVo user) {
		long id = db.delete(USER_TABLE_NAME, User.USERNAME + " = '" + user.getLoginName()
				+ "'", null);
		return id;
	}
	
	/**
	 * ����һ����¼
	 * @param ����
	 * @return ���¹����¼��id -1��ʾ���²��ɹ�
	 */
	public long update(CourierVo user) {
		ContentValues values = new ContentValues();
		values.put(User.USERNAME, user.getLoginName());
		values.put(User.PASSWORD, user.getPassword());
		values.put(User.ISSAVED, user.getIssaved());
		long id = db.update(USER_TABLE_NAME, values, User.USERNAME + " = '"
				+ user.getLoginName() + "'", null);
		return id;
	}
	/**
	 * �����û�����ѯ����
	 * @param username �û���
	 * @return Hashmap ��ѯ�ļ�¼
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
	 * ��ס����ѡ����Ƿ�ѡ��
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
	 * ��ѯ�����û���
	 * @param tableName ����
	 * @return ���м�¼��list����
	 */
	public List<CourierVo> queryAllUser() {
		List<CourierVo> list = new ArrayList<CourierVo>();
		if (db != null) {
			Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null, null, null);
			if(cursor == null || (cursor.getCount()) < 1){
				if(cursor != null) cursor.close();
				return null;
			}
			while (cursor.moveToNext()) {
				CourierVo vo = new CourierVo();
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
	 * �ر����ݿ�
	 */
	public void cleanup() {
		if (this.db != null) {
			this.db.close();
			this.db = null;
		}
	}
	
	/**
	 * ���ݿ⸨����
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
