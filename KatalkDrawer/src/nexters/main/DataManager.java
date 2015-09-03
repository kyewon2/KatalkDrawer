package nexters.main;

import java.util.ArrayList;

import nexters.main.VO.DescVO;
import nexters.main.VO.OtherVO;
import nexters.main.VO.TalkVO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataManager {

	private final String TAG = "DataManager";
	private Context context = null;

	OtherVO otherVO;
	TalkVO talkVO;

	ArrayList<DescVO> descList = new ArrayList<DescVO>();
	private ArrayList<OtherVO> otherList = new ArrayList<OtherVO>();
	private ArrayList<TalkVO> talkList = new ArrayList<TalkVO>();
	DBHelper helper;

	public DataManager(Context context) {
		this.context = context;
		helper = new DBHelper(this.context);
		Log.i("TAG", "디비 생성자 호출 테이블 만듬");
	}

	// String dateTime = 디비에서 꺼내온 날짜 값
	// SimpleDateFormat dateFormat = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date date = new Date();
	// date = dateFormat.parse(dateTime);
	// db.execSQL("create table talk(other_name text, talk_name text, date text, content text)");

	public void insertOtherTable(String otherName, int fDate, int lDate,
			int sDate) {
		Log.i(TAG, "in insertOther");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into other values(null, '" + otherName + "',"
				+ "' '," + fDate + "," + lDate + "," + sDate + ");";
		db.execSQL(sql);
		helper.close();
	}

	public void insertTalk(String otherName, String talkName, int date,
			int time, String content) {

		Log.i(TAG, "in insertTalk");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into talk values(null, '" + otherName + "', '"
				+ talkName + "'," + date + "," + time + ",'" + content + "');";
		db.execSQL(sql);
		helper.close();
	}

	// ////////////////////////////////////////////////////////////////////

	public void updateother(int id, String other_name, String other_image,
			int first_date, int last_date, int save_date) {
		SQLiteDatabase db = helper.getWritableDatabase();

		String sql = "UPDATE other SET id = '" + id + "', other_name = '"
				+ other_name + "', other_image = '" + other_image
				+ "', first_date = '" + first_date + "',last_date= '"
				+ last_date + "',save_date = '" + save_date + "' WHERE id = '"
				+ id + "';";
		Log.i("DataManager", sql);
		db.execSQL(sql);

		helper.close();

	}

	public ArrayList<OtherVO> getotherList() {
		Log.i(TAG, "getOtherList");
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from other";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			Log.i(TAG, "in while");
			int result1 = cursor.getInt(0);
			String result2 = cursor.getString(1);
			String result3 = cursor.getString(2);
			int result4 = cursor.getInt(3);
			int result5 = cursor.getInt(4);
			int result6 = cursor.getInt(5);

			// if(result3.length()<5){
			// 고정 이미지 넣기

			// }
			if (result3 == null) {
				result3 = " ";

			}
			Log.i(TAG, "output ===>" + result1 + " " + result2 + result3
					+ result4 + " " + result5 + " " + result6);

			otherVO = new OtherVO(result1, result2, result3, result4, result5,
					result6);
			otherList.add(otherVO);
			Log.i(TAG, "size = " + otherList.size());
		}
		helper.close();
		cursor.close();
		return otherList;
	}

	public ArrayList<TalkVO> getTalkList(String otherName, int date) {
		talkList = new ArrayList<TalkVO>();
		Log.i(TAG, "getTalkList");
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from talk where other_name = '" + otherName
				+ "' and date =" + date;

		// 숫자 like안되면 201402060000 보다 크고 201402070000보다 작은것 찾는 식으로 해보기
		// where 되면 order by date 하기

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			Log.i(TAG, "in while");
			int result0 = cursor.getInt(0);
			String result1 = cursor.getString(1);
			String result2 = cursor.getString(2);
			int result3 = cursor.getInt(3);
			int result4 = cursor.getInt(4);
			String result5 = cursor.getString(5);

			Log.i(TAG, "output ===>" + result1 + " " + result2 + result3
					+ result4 + result5);

			talkVO = new TalkVO(result0, result1, result2, result3, result4,
					result5);
			talkList.add(talkVO);
			Log.i(TAG, "size = " + talkList.size());
		}
		helper.close();
		cursor.close();

		return talkList;
	}

	public void insertDesc(DescVO desc) {
		Log.i(TAG, "in insertDesc");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into desc values('" + desc.getOther_name() + "',"
				+ desc.getDate() + ", '" + desc.getDesc() + "')";

		db.execSQL(sql);
		helper.close();
	}

	public ArrayList<DescVO> getDescList(String otherName) {

		Log.i(TAG, "getDescList");
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from desc where other_name = '" + otherName
				+ "';";

		// 숫자 like안되면 201402060000 보다 크고 201402070000보다 작은것 찾는 식으로 해보기
		// where 되면 order by date 하기

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			String result1 = cursor.getString(0);
			int result2 = cursor.getInt(1);
			String result3 = cursor.getString(2);

			Log.i(TAG, "output ===>" + result1 + " " + result2 + result3);

			DescVO descVO = new DescVO(result1, result2, result3);
			descList.add(descVO);

		}
		helper.close();
		cursor.close();
		Log.i(TAG, "size = " + descList.size());
		return descList;
	}

	public ArrayList<TalkVO> getTalk(String otherName, int talkId1, int talkId2) {
		Log.i(TAG, "in getTalk");
		talkList = new ArrayList<TalkVO>();

		Log.i(TAG, "getTalk");
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from talk where other_name = '" + otherName
				+ "' and id <= " + talkId1 + " and id >=" + talkId2 + " ;";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			int result0 = cursor.getInt(0);
			String result1 = cursor.getString(1);
			String result2 = cursor.getString(2);
			int result3 = cursor.getInt(3);
			int result4 = cursor.getInt(4);
			String result5 = cursor.getString(5);

			talkVO = new TalkVO(result0, result1, result2, result3, result4,
					result5);
			talkList.add(talkVO);

		}
		helper.close();
		cursor.close();
		Log.i(TAG, "size = " + talkList.size());

		return talkList;
	}

	public void insertSaveTalk(TalkVO talk, String fileName) {
		Log.i(TAG, "in insertSaveTalk");
		SQLiteDatabase db = helper.getWritableDatabase();

		Log.i(TAG, talk.getOther_name() + " " + talk.getTalk_name() + " "
				+ talk.getDate() + " " + talk.getTime() + talk.getContent());
		// db.execSQL("create table save_talk(other_name text, talk_name text, date integer, time integer, content text, file_name text)");
		String sql = "insert into save_talk(other_name, talk_name, date, time, content, file_name) values('"
				+ talk.getOther_name()
				+ "', '"
				+ talk.getTalk_name()
				+ "',"
				+ talk.getDate()
				+ ","
				+ talk.getTime()
				+ ",'"
				+ talk.getContent() + "', '" + fileName + "');";
		db.execSQL(sql);

		helper.close();

	}

	public ArrayList<String> getSaveTalkList(String otherName) {

		ArrayList<String> saveList = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from save_talk where other_name = '" + otherName
				+ "'";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			String result = cursor.getString(5);

			Log.i(TAG, "get Save output ===>" + result);

			saveList.add(result);
			Log.i(TAG, "size = " + saveList.size());
		}
		helper.close();
		cursor.close();

		return saveList;
	}

	public ArrayList<OtherVO> getOtherInfo(String otherName) {
		Log.i(TAG, "in getOtherInfo");
		otherList = new ArrayList<OtherVO>();
		OtherVO other;
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from other where other_name = '" + otherName
				+ "'";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			other = new OtherVO(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
					cursor.getInt(5));
			otherList.add(other);
			Log.i(TAG, "size = " + otherList.size());
		}
		helper.close();
		cursor.close();

		return otherList;

	}

	public void deleteOther(String otherName) {
		Log.i(TAG, "in deleteOther");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from other where other_name = '" + otherName
				+ "';";
		db.execSQL(sql);
		helper.close();
	}

	public void deleteTalk(String otherName) {
		Log.i(TAG, "in deleteTalk");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from talk where other_name = '" + otherName + "';";
		db.execSQL(sql);
		helper.close();
	}

	public void deleteSaveTalk(String otherName) {
		Log.i(TAG, "in deleteSaveTalk");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from save_talk where other_name = '" + otherName
				+ "';";
		db.execSQL(sql);
		helper.close();
	}

	public void deleteDesc(String otherName) {
		Log.i(TAG, "in deleteDesc");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from desc where other_name = '" + otherName + "';";
		db.execSQL(sql);
		helper.close();
	}

	public ArrayList<TalkVO> getSaveTalkListByFileName(String otherName,
			String fileName) {
		Log.i(TAG, "getSaveTalkListByFileName");
		talkList = new ArrayList<TalkVO>();

		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from save_talk where other_name = '" + otherName
				+ "' and file_name = '" + fileName + "'";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			String result1 = cursor.getString(0);
			String result2 = cursor.getString(1);
			int result3 = cursor.getInt(2);
			int result4 = cursor.getInt(3);
			String result5 = cursor.getString(4);

			Log.i(TAG, " " + result1 + " " + result2 + " " + result3 + " "
					+ result4 + " " + result5);
			talkVO = new TalkVO(0, result1, result2, result3, result4, result5);

			talkList.add(talkVO);

		}
		helper.close();
		cursor.close();

		return talkList;
	}

	public ArrayList<TalkVO> getSearchTalk(String otherName, String text) {
		Log.i(TAG, "getSearchTalk  " + otherName + " " + text);
		talkList = new ArrayList<TalkVO>();

		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from talk where other_name = '" + otherName
				+ "' and content like '%" + text
				+ "%'order by date asc, time asc;";

		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			int result1 = cursor.getInt(0);
			String result2 = cursor.getString(1);
			String result3 = cursor.getString(2);
			int result4 = cursor.getInt(3);
			int result5 = cursor.getInt(4);
			String result6 = cursor.getString(5);

			Log.i(TAG, " " + result1 + " " + result2 + " " + result3 + " "
					+ result4 + " " + result5);
			talkVO = new TalkVO(result1, result2, result3, result4, result5,
					result6);

			talkList.add(talkVO);

		}
		helper.close();
		cursor.close();

		return talkList;
	}

	public void deleteSaveTalkByFile(String otherName, String fileName) {
		Log.i(TAG, "in deleteSaveTalk by name");
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "delete from save_talk where other_name = '" + otherName
				+ "' and file_name = '" + fileName + "';";
		db.execSQL(sql);
		helper.close();
	}

}