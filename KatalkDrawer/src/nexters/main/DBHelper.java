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
		//SD카드에서 불러온 대화 테이블
		db.execSQL("create table other(id integer primary key autoincrement, other_name text," +
				"other_image text," +
				"first_date integer," +
				"last_date integer," +
				"save_date integer)");
				//여기 date는 날짜만

		
		//대화내용 테이블
		db.execSQL("create table talk(id integer primary key autoincrement, other_name text, talk_name text, date integer, time integer, content text)");
		
		//사용자 테이블
		db.execSQL("create table user(name text, image text)");
		
		//잘라낸부분 테이블
		db.execSQL("create table save_talk(other_name text, talk_name text, date integer, time integer, content text, file_name text)");
		
		//날짜별 description
		db.execSQL("create table desc(other_name text, date integer, desc text)");
		//desc 값은 sorry, love, 
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
