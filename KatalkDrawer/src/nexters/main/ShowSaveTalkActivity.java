package nexters.main;

import java.util.ArrayList;

import nexters.example.nextersproject1.R;
import nexters.main.VO.TalkVO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowSaveTalkActivity extends Activity {
	private final String TAG = "ShowSaveTalkActivity";
	SaveTalkAdapter otherAdapter = null;
	ArrayList<TalkVO> talkList = null;
	ListView listView = null;
	DataManager dataManager = new DataManager(this);
	String otherName = null;
	String fileName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_save_talk);
		otherName = TabHostActivity.otherName;
		
		Intent intent = getIntent();
		fileName = intent.getStringExtra("fileName");
		Log.i(TAG, fileName);
		
		
		talkList = new ArrayList<TalkVO>();
		talkList = dataManager.getSaveTalkListByFileName(otherName, fileName);
		
		otherAdapter = new SaveTalkAdapter(ShowSaveTalkActivity.this,
				R.layout.icontext, talkList);
		listView = (ListView) findViewById(R.id.talkList);
		listView.setAdapter(otherAdapter);
		listView.setSelector(R.drawable.list_selector);
	}

}

class SaveTalkAdapter extends BaseAdapter {

	Context mainContext;
	LayoutInflater Inflater;
	ArrayList<TalkVO> list;
	int layout;

	public SaveTalkAdapter(Context context, int pLayout, ArrayList<TalkVO> pList) {
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
	
		if (convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}

		// convertView.setBackgroundResource(R.drawable.list_selector);

		TextView other_name = (TextView) convertView
				.findViewById(R.id.talk_name);
		TextView content = (TextView) convertView.findViewById(R.id.content);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		ImageView iv = (ImageView) convertView.findViewById(R.id.other_image);

		content.setText(list.get(position).getContent().trim());
		Log.i("", content.getText().toString());
		String ti = Integer.toString(list.get(position).getTime());
		Log.i("티아이값 ", ti);
		int bb = Integer.parseInt(ti.substring(0, 2));

		String aa = ti.substring(0, 2) + ":" + ti.substring(2, 4);
		if (bb > 12) {
			bb = bb - 12;
			aa = " 오후  " + Integer.toString(bb) + ":" + ti.substring(2, 4);

		} else {
			aa = " 오전  " + aa;
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
