package nexters.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import nexters.example.nextersproject1.R;
import nexters.main.VO.DescVO;
import nexters.main.VO.TalkVO;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;

	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;
	DataManager dataManager;
	String otherName;
	ArrayList<DescVO> list;
	TextView dayView;

	String aa = "";

	boolean currentMonth = false;

	public CalendarAdapter(Context c, Calendar monthCalendar, String otherName) {
		Log.i("", "start CalendarAdapter");
		dataManager = new DataManager(c);
		this.otherName = otherName;

		// 이제 나중되면 매개변수 추가해서 미안, 사랑, 그런거 표시하기 if문으로
		list = dataManager.getDescList(otherName);
		Log.i("", "after getDescList");

		CalendarAdapter.dayString = new ArrayList<String>();
		month = monthCalendar;
		Log.i("Tag", "monthi  dsjjf======>   " + month);
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		curentDateString = df.format(selectedDate.getTime());
		refreshDays();
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		// if (convertView == null) { // if it's not recycled, initialize some
		// attributes
		LayoutInflater vi = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.item_calendar, null);

		// }
		dayView = (TextView) v.findViewById(R.id.date);
		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		// checking whether the day is in current month or not.
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			currentMonth = true;
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			currentMonth = true;
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
		}

		if (dayString.get(position).equals(curentDateString)) {
			setSelected(v);
			previousView = v;
		} else {
			// v.setBackgroundResource(R.drawable.list_item_background);
		}
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = dayString.get(position);

		aa = date.substring(0, 4) + date.substring(5, 7)
				+ date.substring(8, 10);
		int dat = Integer.parseInt(aa);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDate() == dat) {
				if (!currentMonth) {
					v.setBackgroundResource(R.drawable.icon_2);
				}
			}
		}

		setGray(aa);

		if (date.length() == 1) {
			date = "0" + date;
		}

		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array

		ImageView iw = (ImageView) v.findViewById(R.id.action_settings);// date_icon

		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			// iw.setVisibility(View.INVISIBLE);
		}
		return v;

	}

	public void setGray(String aa) {

		ArrayList<TalkVO> talkList = new ArrayList<TalkVO>();

		talkList = dataManager.getTalkList(otherName, Integer.parseInt(aa));
		Log.i("ddd", talkList.size() + "");
		if (currentMonth) {
			dayView.setVisibility(View.INVISIBLE);
			// dayView.setBackgroundColor(Color.TRANSPARENT);
			// dayView.setTextColor(Color.WHITE);
		} else {
			if (talkList.size() == 0) {
				dayView.setTextColor(Color.GRAY);
			} else {
				dayView.setTextColor(Color.BLACK);
			}
		}
		currentMonth = false;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			// previousView.setBackgroundResource(R.drawable.list_item_background);
		}
		previousView = view;
		// view.setBackgroundResource(R.drawable.calendar_cel_selectl);
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);

			// aa = itemvalue.substring(0, 4) + itemvalue.substring(5, 7)
			// + itemvalue.substring(8, 10);
			//
			// setGray(aa);

		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {

			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

}
