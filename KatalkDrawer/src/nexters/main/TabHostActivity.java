package nexters.main;

import nexters.example.nextersproject1.R;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHostActivity extends TabActivity {
	private final String TAG = "TabHostActivity";
	static String otherName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_host);
		Log.i(TAG, "start tabHost");

		TabHost tabHost = getTabHost();
		Intent intent = getIntent();
		otherName = intent.getStringExtra("otherName");
		Log.i(TAG, "other " + otherName);

		Intent tab1 = new Intent(TabHostActivity.this, CalendarActivity.class);
		Intent tab2 = new Intent(TabHostActivity.this, SaveTalkActivity.class);
		Intent tab3 = new Intent(TabHostActivity.this, SearchActivity.class);

//		tab1.putExtra("otherName", otherName);
//		tab2.putExtra("otherName", otherName);
//		tab3.putExtra("otherName", otherName);

		// setIndicator()에서 탭에 보여질 글자나 이미지 삽입
		
		ImageView image1 = new ImageView(this);
		image1.setImageResource(R.drawable.tab1_selector);
		ImageView image2 = new ImageView(this);
		image2.setImageResource(R.drawable.tab2_selector);
		ImageView image3 = new ImageView(this);
		image3.setImageResource(R.drawable.tab3_selector);
		
		TabSpec spec1 = tabHost.newTabSpec("tab1").setIndicator(image1);
		spec1.setContent(tab1);
		tabHost.addTab(spec1);

		TabSpec spec2 = tabHost.newTabSpec("tab2").setIndicator(image2);
		spec2.setContent(tab2);
		tabHost.addTab(spec2);

		TabSpec spec3 = tabHost.newTabSpec("tab3").setIndicator(image3);
		spec3.setContent(tab3);
		tabHost.addTab(spec3);

		tabHost.setCurrentTab(0);// 처음보여질 탭

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_host, menu);
		return true;
	}

}
