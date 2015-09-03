package nexters.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	private final String TAG = "DBHelperclass";
	public DBHelper(Context context) {
		super(context, "nexters.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//SDī�忡�� �ҷ��� ��ȭ ���̺�
		db.execSQL("create table other(id integer primary key autoincrement, other_name text," +
				"other_image text," +
				"first_date integer," +
				"last_date integer," +
				"save_date integer)");
				//���� date�� ��¥��

		
		//��ȭ���� ���̺�
		db.execSQL("create table talk(id integer primary key autoincrement, other_name text, talk_name text, date integer, time integer, content text)");
		
		//����� ���̺�
		db.execSQL("create table user(name text, image text)");
		
		//�߶󳽺κ� ���̺�
		db.execSQL("create table save_talk(other_name text, talk_name text, date integer, time integer, content text, file_name text)");
		
		//��¥�� description
		db.execSQL("create table desc(other_name text, date integer, desc text)");
		//desc ���� sorry, love, 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS other");
		db.execSQL("DROP TABLE IF EXISTS talk");
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS save_talk");
		onCreate(db);
	}

}
