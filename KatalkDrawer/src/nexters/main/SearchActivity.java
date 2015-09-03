package nexters.main;

import java.util.ArrayList;

import nexters.example.nextersproject1.R;
import nexters.main.VO.TalkVO;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends Activity {
	private final static String TAG = "SearchActivity";
	EditText searchText;
	ImageView searchBtn;
	String search = "";
	DataManager dataManager;
	ListView searchListView;
	ArrayList<TalkVO> searchResultList;
	ListView resultView;
	SearchTalkAdapter searchAdapter;
	TalkAdapter talkAdatper;
	ArrayList<TalkVO> resultList;
	ImageView cut, fold;

	String otherName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		otherName = TabHostActivity.otherName;

		searchText = (EditText) findViewById(R.id.search_text);
		searchBtn = (ImageView) findViewById(R.id.search_btn);
		cut = (ImageView) findViewById(R.id.cut);
		fold = (ImageView) findViewById(R.id.fold);

		dataManager = new DataManager(this);
		searchListView = (ListView) findViewById(R.id.search_list);
		resultView = (ListView) findViewById(R.id.resutl_list);

		searchResultList = new ArrayList<TalkVO>();

		searchBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchListView.setVisibility(View.VISIBLE);

				search = searchText.getText().toString();
				searchResultList = dataManager.getSearchTalk(otherName, search);
				Log.i(TAG, searchResultList.size() + "");
				searchAdapter = new SearchTalkAdapter(SearchActivity.this,
						R.layout.search_talk_list, searchResultList);
				searchListView.setAdapter(searchAdapter);
				
				if(searchResultList.size()!=0){
					fold.setImageResource(R.drawable.down_icon);
				}
				

			}
		});

		searchListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				searchListView.setVisibility(View.GONE);
				resultList = dataManager.getTalkList(otherName,
						searchResultList.get(position).getDate());
				talkAdatper = new TalkAdapter(SearchActivity.this,
						R.layout.icontext, resultList);
				resultView.setAdapter(talkAdatper);

			}

		});

		fold.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (searchResultList.size() != 0) {
					if (searchListView.isShown()) {
						fold.setImageResource(R.drawable.up_icon);
						searchListView.setVisibility(View.GONE);
					} else {
						fold.setImageResource(R.drawable.down_icon);
						searchListView.setVisibility(View.VISIBLE);
					}
				}
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

}

class SearchTalkAdapter extends BaseAdapter {

	Context mainContext;
	LayoutInflater Inflater;
	ArrayList<TalkVO> list;
	int layout;

	public SearchTalkAdapter(Context context, int pLayout,
			ArrayList<TalkVO> pList) {
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

		// if (getItemId(pos) == posID1) {
		//
		// }

		TextView talkPerson = (TextView) convertView
				.findViewById(R.id.talk_person);
		TextView date = (TextView) convertView.findViewById(R.id.date);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		TextView content = (TextView) convertView.findViewById(R.id.content);

		String talkName = list.get(pos).getTalk_name();
		String talkDate = Integer.toString(list.get(pos).getDate());

		String talkContent = list.get(pos).getContent();

		talkPerson.setText("  from " + talkName);
		date.setText(talkDate.substring(0, 4) + "." + talkDate.substring(4, 6)
				+ "." + talkDate.substring(6, 8));

		content.setText(talkContent);

		// convertView는 원본의 개수만큼이 아니라 화면에 보여지는 개수만큼 만들어짐
		return convertView;
	}
}
