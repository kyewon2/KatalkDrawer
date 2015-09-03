package nexters.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nexters.example.nextersproject1.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveTalkActivity extends Activity {
	private final String TAG = "SaveTalkActivity";
	DataManager dataManager;
	ArrayList<String> saveFileList;
	ArrayList<String> realSaveFileList;
	SaveAdapter saveAdapter;
	String otherName = "";
	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_talk);
		Log.i(TAG, "in SaveTalkActivity");
		otherName = TabHostActivity.otherName;
		Log.i(TAG, "othername " + otherName);

		dataManager = new DataManager(this);
		saveFileList = new ArrayList<String>();
		realSaveFileList = new ArrayList<String>();
		gridView = (GridView) findViewById(R.id.saveGrid);

		saveFileList = dataManager.getSaveTalkList(otherName);

		realSaveFileList = new ArrayList<String>(new HashSet<String>(
				saveFileList));

		saveAdapter = new SaveAdapter(this, R.layout.icontext3,
				realSaveFileList);

		Log.i(TAG, " " + realSaveFileList.size());

		gridView.setAdapter(saveAdapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		saveFileList = new ArrayList<String>();
		saveFileList = dataManager.getSaveTalkList(otherName);

		realSaveFileList = new ArrayList<String>(new HashSet<String>(
				saveFileList));

		saveAdapter = new SaveAdapter(this, R.layout.icontext3,
				realSaveFileList);
		gridView.setAdapter(saveAdapter);

	}

}

class SaveAdapter extends BaseAdapter {

	Context mainContext;
	LayoutInflater Inflater;
	List<String> list;
	int layout;
	DataManager dataManager;

	public SaveAdapter(Context context, int pLayout, ArrayList<String> saveList) {
		mainContext = context;
		Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = saveList;
		layout = pLayout;
		dataManager = new DataManager(context);
	}

	public int getCount() {
		return list.size();
	}

	public String getItem(int position) {
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

		TextView name = (TextView) convertView.findViewById(R.id.main_name2);
		ImageView im = (ImageView) convertView.findViewById(R.id.imageBtn2);

		name.setText(list.get(pos));
		name.setTextColor(Color.BLACK);
		im.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = list.get(pos);

				Intent intent = new Intent(mainContext,
						ShowSaveTalkActivity.class);
				intent.putExtra("fileName", name);

				mainContext.startActivity(intent);
			}
		});

		im.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				final Dialog characDialog = new Dialog(mainContext);
				characDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				characDialog.setContentView(R.layout.delete_save_dialog);
				characDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				characDialog.show();
				ImageView dialogButton = (ImageView) characDialog
						.findViewById(R.id.delete_btn);
				dialogButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dataManager.deleteSaveTalkByFile(
								TabHostActivity.otherName, list.get(pos));
						list.remove(list.get(pos));
						notifyDataSetChanged();
						characDialog.dismiss();
					}
				});
				characDialog.show();

				return false;
			}
		});

		// convertView는 원본의 개수만큼이 아니라 화면에 보여지는 개수만큼 만들어짐
		return convertView;
	}

}
