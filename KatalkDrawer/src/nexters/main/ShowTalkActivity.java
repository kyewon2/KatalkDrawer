package nexters.main;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nexters.example.nextersproject1.R;
import nexters.main.VO.TalkVO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ShowTalkActivity extends Activity {
	private final String TAG = "ShowTalkActivity";
	static TalkAdapter otherAdapter = null;
	static ArrayList<TalkVO> talkList = null;
	ListView listView = null;
	DataManager dataManager = new DataManager(this);
	String otherName = null;

	int count = 0;
	int talkId1 = 0;
	int talkId2 = 0;
	EditText saveName;
	long posID1 = 0, posID2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_talk);
		final ToggleButton cut = (ToggleButton) findViewById(R.id.cutImg);

		Log.i(TAG, "start showTalk");
		// 인텐트 ==> 이름, 연도, 월, 일
		Intent intent = getIntent();
		otherName = TabHostActivity.otherName;
		int date = intent.getIntExtra("talkDate", 0);

		talkList = new ArrayList<TalkVO>();

		// talkList에서 DB값 받아오기
		// int d = Integer.parseInt(date);
		talkList = dataManager.getTalkList(otherName, date);

		if (talkList.size() == 0) {
			Log.i(TAG, "no talkList");
			finish();
		}

		otherAdapter = new TalkAdapter(ShowTalkActivity.this,
				R.layout.icontext, talkList);
		listView = (ListView) findViewById(R.id.talkList);
		listView.setAdapter(otherAdapter);
		listView.setSelector(R.drawable.list_selector);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (cut.isChecked()) {
					if (count < 2) {
						v.setBackgroundColor(0xFFFFFFFF);

						if (count == 0) {
							posID1 = id;
							talkId1 = talkList.get(position).getId();
						} else {
							posID1 = id;
							talkId2 = talkList.get(position).getId();
						}
						count++;
					} else {

					}

				}
			}

		});

		cut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (cut.isChecked()) {

					Toast.makeText(ShowTalkActivity.this,
							"잘라서 보관할 처음과 끝을 선택하세요", Toast.LENGTH_SHORT).show();
					cut.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_end));

				} else {
					if (count == 2) {
						cut.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.crop_icon));

						LayoutInflater inflater = (LayoutInflater) ShowTalkActivity.this
								.getSystemService(LAYOUT_INFLATER_SERVICE);
						View layout = inflater.inflate(
								R.layout.save_talk_dialog,
								(ViewGroup) findViewById(R.id.save_talk_name));

						saveName = (EditText) layout
								.findViewById(R.id.save_talk_name);
						saveName.requestFocus();
						
						// InputMethodManager imm = (InputMethodManager)
						// getSystemService(Context.INPUT_METHOD_SERVICE);
						// imm.showSoftInput(saveName, 0);

						new AlertDialog.Builder(ShowTalkActivity.this)
								.setTitle("보관함 저장")
								.setView(layout)
								.setNeutralButton("저장",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												String sName = saveName
														.getText().toString();
												Log.i(TAG, sName);
												// db에 저장

												saveTalk();

												// 리스트뷰 갱신
												// listView.invalidateViews();
												// listView.setSelector(R.drawable.list_selector);
												cut.setChecked(false);

											}
										}).show();

					}

				}

			}
		});

	}

	public void saveTalk() {
		ArrayList<TalkVO> saveTalk = new ArrayList<TalkVO>();

		if (talkId1 > talkId2) {
			saveTalk = dataManager.getTalk(otherName, talkId1, talkId2);
		} else {
			saveTalk = dataManager.getTalk(otherName, talkId2, talkId1);
		}

		//
		for (int i = 0; i < saveTalk.size(); i++) {
			dataManager.insertSaveTalk(saveTalk.get(i), saveName.getText()
					.toString());
		}

		for (int i = 0; i < saveTalk.size(); i++) {
			Log.i(TAG, "" + saveTalk.get(i).getContent());
		}

	}
}

class TalkAdapter extends BaseAdapter {

	Context mainContext;
	LayoutInflater Inflater;
	ArrayList<TalkVO> list;
	int layout;
	

	public TalkAdapter(Context context, int pLayout, ArrayList<TalkVO> pList) {
		mainContext = context;
		Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = pList;
		layout = pLayout;
	}

	public int getCount() {
		return list.size();
	}

	public TalkVO getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	// 각 항목의 뷰 생성
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if (convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}

		 convertView.setBackgroundResource(R.drawable.list_selector);
//		if (getItemId(pos) == posID1) {
//
//		}

		TextView other_name = (TextView) convertView
				.findViewById(R.id.talk_name);
		TextView content = (TextView) convertView.findViewById(R.id.content);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		ImageView iv = (ImageView) convertView.findViewById(R.id.other_image);

		content.setText(list.get(position).getContent().trim());
		String ti = Integer.toString(list.get(position).getTime());
		int bb = Integer.parseInt(ti.substring(0, 2));

		String aa = ti.substring(0, 2) + ":" + ti.substring(2, 4);
		if (bb > 12) {
			bb = bb - 12;
			aa = "오후  " + Integer.toString(bb) + ":" + ti.substring(2, 4);

		} else {
			aa = "오전  " + aa;
		}

		time.setText(aa);
		if (list.get(position).getTalk_name().equals("회원님")) {
			other_name.setText(list.get(position).getTalk_name());
			Log.i("  ", "dd" + list.get(position).getTalk_name() + "dd");
			content.setGravity(Gravity.RIGHT);
			iv.setBackgroundResource(0);
		} else {
			other_name.setText(list.get(position).getTalk_name());
			content.setGravity(Gravity.LEFT);
			iv.setBackgroundResource(R.drawable.icon);
		}
		// convertView는 원본의 개수만큼이 아니라 화면에 보여지는 개수만큼 만들어짐
		return convertView;
	}

}