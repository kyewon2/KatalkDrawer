package nexters.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import nexters.example.nextersproject1.R;
import nexters.main.VO.OtherVO;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends Activity {
	private final static String TAG = "CalendarActivity";

	ArrayList<OtherVO> otherList = new ArrayList<OtherVO>();
	int firstDate = 0, lastDate = 0;
	public Calendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
	// marker.
	public ArrayList<String> items; // container to store calendar items which

	// needs showing the event marker
	String otherName = null;
	DataManager dataManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "start CalendarActivity");

		dataManager = new DataManager(this);

		otherName = TabHostActivity.otherName;
		Log.i(TAG, "other " + otherName);
		setContentView(R.layout.activity_calendar);

		month = Calendar.getInstance();
		itemmonth = (Calendar) month.clone();

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this, month, otherName);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		ImageButton previous = (ImageButton) findViewById(R.id.pre);

		previous.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		previous.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				otherList = dataManager.getOtherInfo(otherName);
				firstDate = otherList.get(0).getFirst_date();

				setCustomDate(firstDate);

				return false;
			}
		});

		ImageButton next = (ImageButton) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		next.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				otherList = dataManager.getOtherInfo(otherName);

				lastDate = otherList.get(0).getLast_date();

				setCustomDate(lastDate);
				refreshCalendar();

				return false;
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				// showToast(selectedGridDate);

				String aa = selectedGridDate.substring(0, 4)
						+ selectedGridDate.substring(5, 7)
						+ selectedGridDate.substring(8, 10);
				int date = Integer.parseInt(aa);

				Intent i = new Intent(CalendarActivity.this,
						ShowTalkActivity.class);
				i.putExtra("otherName", otherName);
				i.putExtra("talkDate", date);
				startActivity(i);

			}
		});
	}

	protected void setNextMonth() {
		if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
			month.set((month.get(Calendar.YEAR) + 1),
					month.getActualMinimum(Calendar.MONTH), 1);
		} else {
			month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
			month.set((month.get(Calendar.YEAR) - 1),
					month.getActualMaximum(Calendar.MONTH), 1);
		} else {
			month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
		}

	}

	public void setCustomDate(int date) {
		int y = 0, m = 0, d = 0;

		String day = Integer.toString(date);
		y = Integer.parseInt(day.substring(0, 4));
		m = Integer.parseInt(day.substring(4, 6));
		Log.i(TAG, "돌아가려는 연도 날짜" + y + "  " + m);

		month.set(Calendar.YEAR, y);
		month.set(Calendar.MONTH, m);

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String itemvalue;
			for (int i = 0; i < 7; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(Calendar.DATE, 1);
				items.add("2012-09-12");
				items.add("2012-10-07");
				items.add("2012-10-15");
				items.add("2012-10-20");
				items.add("2012-11-30");
				items.add("2012-11-28");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
}
