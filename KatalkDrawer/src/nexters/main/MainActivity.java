package nexters.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nexters.example.nextersproject1.R;
import nexters.main.VO.DescVO;
import nexters.main.VO.OtherVO;
import nexters.main.VO.TalkVO;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	MainAdapter fileList;
	private List<String> mFileNames;
	private final String TAG = "MainActivity";
	DataManager dataManager;
	int max = 0;
	int min = 99999999;
	String otherName = null;
	boolean delFlag = false;
	HashMap<Integer, Integer> talkCountMap = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> sorryCountMap = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> sorryPerMap = new HashMap<Integer, Integer>();

	HashMap<Integer, Integer> loveCountMap = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> lovePerMap = new HashMap<Integer, Integer>();

	String talkDate;
	int sorryCount, totalCount, talkCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataManager = new DataManager(this);
		mFileNames = new ArrayList<String>();
		// startActivity(new Intent(this, TabHostActivity.class));
		// 스플레시 띄우기

		// 핸드폰내에 카톡 메세지 저장 위치
		// /KakaoTalk/Chats 에 폴더 별로 저장됨
		ImageView setting = (ImageView) findViewById(R.id.setting);
		GridView gridView = (GridView) findViewById(R.id.grid);
		gridView.setSelector(R.drawable.list_selector);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/KakaoTalk/Chats";
		String folderName = null;

		String filePath = null;

		File talkFiles = new File(path);
		File chatFiles = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/KakaoTalk");
		// File files = new File(FILE_PATH);

		fileList = new MainAdapter(this, R.layout.icontext2, mFileNames);

		Pattern pattern1, pattern2, pattern3, pattern4;
		Matcher matcher1, matcher2, matcher3, matcher4;

		// sd카드에 아무것도 없을때

		Log.i(TAG, "in No SDCard");
		delFlag = false;

		ArrayList<OtherVO> other = new ArrayList<OtherVO>();
		other = dataManager.getotherList();
		if (other.size() != 0) {
			for (int i = 0; i < other.size(); i++) {
				mFileNames.add(other.get(i).getOther_name());
			}
		}

		setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		if (chatFiles.listFiles().length != 3) {
			Log.i(TAG, "in if");
			return;
		} else if (talkFiles.listFiles().length > 0) {// sd카드에 텍스트가 있을 때

			String fDate = null;
			String lDate = null;
			for (File file : talkFiles.listFiles()) {
				folderName = file.getName();
				String tmp = file.getName();
				String saveYear = tmp.substring(16, 20);
				String saveMonth = tmp.substring(21, 23);
				String saveDay = tmp.substring(24, 26);
				filePath = path + "/" + folderName;

				File txtFile = new File(filePath);
				for (File f : txtFile.listFiles()) {

					if (f.getName().equals("KakaoTalkChats.txt")) {
						Log.i(TAG, "if kakaoTalkChats.txt");

						FileInputStream fis;
						try {
							fis = new FileInputStream(filePath + "/"
									+ "KakaoTalkChats.txt");
							BufferedReader bufferReader = new BufferedReader(
									new InputStreamReader(fis));
							String str = null;

							while ((str = bufferReader.readLine()) != null) {
								// Log.i(TAG, "in while");

								// 이름과 날짜 패턴 find
								pattern1 = Pattern.compile("님과 카카오톡 대화$");
								matcher1 = pattern1.matcher(str);

								pattern2 = Pattern
										.compile("^[0-9][0-9][0-9][0-9]년 ([0-9][0-9]|[0-9])월 ([0-9][0-9]|[0-9])일 (오전|오후) ([0-9][0-9]|[0-9]):[0-9][0-9]$");

								matcher2 = pattern2.matcher(str);

								if (matcher1.find()) {// return boolean
									Log.i(TAG, "in find()");
									int i = str.indexOf("님과 카카오톡 대화") - 1;
									String name = str.substring(0, i);
									Log.i(TAG, "name :   " + name);

									if (name.contains(",")) {
										break;// 이름에 ,포함했으면 while문 끝내기
									} else {
										// db에 저장
										// && 갠톡만 리스트에 보이기

										otherName = name;
										mFileNames.add(otherName);
										Log.i(TAG, "추가 되는 곳!!");
										fileList.notifyDataSetChanged();
										delFlag = true;
									}
								}// end 이름찾기

								if (matcher2.find()) {
									// Log.i(TAG, "in matcher2   "+str);
									int i = str.indexOf("년");
									String talkYear = str.substring(0, i);
									int j = str.indexOf("월");
									String talkMonth = str.substring(i + 2, j);
									int x = str.indexOf("일");
									String talkDay = str.substring(j + 2, x);
									int y = str.indexOf("오후");
									String talkAmPm = null;

									while ((str = bufferReader.readLine()) != null) {

										String tmpMonth = talkMonth;
										String tmpDay = talkDay;

										Log.i("str 출력", str);
										pattern3 = Pattern.compile("^"
												+ talkYear + "년 " + talkMonth
												+ "월 " + talkDay + "일");
										matcher3 = pattern3.matcher(str);

										// Log.i(TAG, "matcher3   " + talkYear
										// + "-" + talkMonth + "-"
										// + talkDay + "-" + talkAmPm
										// + "-" + talkHour + ":"
										// + talkMinute);

										if (matcher3.find()) {
											if (y == -1) {
												talkAmPm = "오전";
												y = str.indexOf("오전");
											} else {
												talkAmPm = "오후";
											}

											int z = str.indexOf(":");
											String talkHour = str.substring(
													y + 3, z);
											String talkMinute = str.substring(
													z + 1, z + 3);

											// 오후일떄 +12
											if (talkAmPm.equals("오후")) {
												int in = Integer
														.parseInt(talkHour);
												if (in == 12) {
													in = 0;
												}
												talkHour = Integer
														.toString(Integer
																.parseInt(talkHour) + 12);

											}

											Log.i(TAG, "substring   "
													+ talkYear + "-"
													+ talkMonth + "-" + talkDay
													+ "-" + talkAmPm + "-"
													+ talkHour + ":"
													+ talkMinute);

											int beforecomma = str.indexOf(",");
											int aftername = str
													.indexOf(":", 20);// 2번째 :

											String talkName = str.substring(
													beforecomma + 2,
													aftername - 1);
											String talk = str
													.substring(aftername + 2);
											// Log.i(TAG,
											// "talking name->"+talkName+"--"+talk);

											// 숫자개수 맞춰주기

											if (tmpMonth.length() == 1) {
												tmpMonth = "0" + talkMonth;
											}
											if (tmpDay.length() == 1) {
												tmpDay = "0" + talkDay;
											}
											if (talkHour.length() == 1) {
												talkHour = "0" + talkHour;
											}
											if (talkMinute.length() == 1) {
												talkMinute = "0" + talkMinute;
											}

											talkDate = talkYear + tmpMonth
													+ tmpDay;
											int temp = Integer
													.parseInt(talkDate);

											// 마지막대화날짜
											if (temp > max) {
												max = temp;
											}
											// 처음대화날짜
											if (temp < min) {
												min = temp;
											}
											Log.i(TAG, "max = " + max
													+ "   min = " + min);
											fDate = Integer.toString(min);
											lDate = Integer.toString(max);

											Log.i(TAG, "talkName =" + talkName
													+ "date = " + talkDate
													+ "talkHour  ===>"
													+ talkHour
													+ "  talkMinute   =>"
													+ talkMinute + "talk==>"
													+ talk);
											talkDate = Integer.toString(temp);
											String talkTime = talkHour
													+ talkMinute;
											Log.i(TAG, "talktime  ===>"
													+ talkTime);
											if (talk.contains("'")) {
												talk = talk.replace("'", "''");
											}

											dataManager.insertTalk(otherName,
													talkName,
													Integer.parseInt(talkDate),
													Integer.parseInt(talkTime),
													talk);
											talkCount++;
											totalCount++;
											delFlag = true;

										} else
											break;

										pattern4 = Pattern.compile("미안");
										matcher4 = pattern4.matcher(str);

										if (matcher4.find()) {
											sorryCount++;
										}
									}// end while
									Log.i(TAG, "after mini while");

									Log.i(TAG, "date:  " + talkDate
											+ " count : " + talkCount + " "
											+ sorryCount);
									talkCountMap.put(
											Integer.parseInt(talkDate),
											talkCount);
									sorryCountMap.put(
											Integer.parseInt(talkDate),
											sorryCount);

									talkCount = 0;
									sorryCount = 0;
								}
								Log.i(TAG, "after cal");
							}// while 문
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Log.i(TAG, "end while totalCount : " + totalCount);

						if (saveMonth.length() == 1) {
							saveMonth = "0" + saveMonth;
						}
						if (saveDay.length() == 1) {
							saveDay = "0" + saveDay;
						}

						String sDate = saveYear + saveMonth + saveDay;
						dataManager.insertOtherTable(otherName,
								Integer.parseInt(fDate),
								Integer.parseInt(lDate),
								Integer.parseInt(sDate));

						// percentage계산
						Iterator<Integer> iterator = talkCountMap.keySet()
								.iterator();
						int i = 0, totalPer = 0;
						Double average = 0.0;

						while (iterator.hasNext()) {
							i++;
							int key = (int) iterator.next();
							int sorry = sorryCountMap.get(key);
							int total = talkCountMap.get(key);
							sorryPerMap.put(key, sorry / total * 100);

							totalPer = sorry / total * 100;
							Log.i(TAG, "key : " + key + " value" + totalPer);
						}
						average = (double) (totalPer / i);

						iterator = sorryCountMap.keySet().iterator();
						while (iterator.hasNext()) {
							int key = iterator.next();
							int sorry = sorryCountMap.get(key);
							if (sorry > average) {
								Log.i(TAG, "in sorry othername: " + otherName
										+ " key : " + key + " value"
										+ sorryPerMap.get(key));
								DescVO desc = new DescVO(otherName, key,
										"sorry");
								dataManager.insertDesc(desc);
							}

						}
					}
				}// for문
			}

		}// end big if else
		Log.i(TAG, "after if");
		fileList.notifyDataSetChanged();
		gridView.setAdapter(fileList);

		Log.i(TAG, "afterj setadapter  " + mFileNames.size());
		if (delFlag == true) {
			String sdpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/KakaoTalk/Chats/";
			File dir = new File(sdpath);
			deleteDirectory(getApplicationContext(), dir);
			Log.i(TAG, " delete the sdcard");
		}
		Log.i(TAG, "after second if");

		ImageView tutorial = (ImageView) findViewById(R.id.tutorial);
		if (mFileNames.size() == 0) {
			gridView.setVisibility(View.GONE);
			tutorial.setVisibility(View.VISIBLE);
		} else {
			gridView.setVisibility(View.VISIBLE);
			tutorial.setVisibility(View.GONE);
		}

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		fileList.notifyDataSetChanged();
		super.onResume();
	}

	public static void deleteDirectory(Context context, File dir) {

		if (dir.exists()) {
			File[] files = dir.listFiles(); // listFiles()메소드로 디렉토리의 서브 디렉토리와
											// 파일목록을 얻어옴
			if (files == null) {
				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(context, files[i]);
				} else {
					files[i].delete();
				}
			}
		}

		if (dir.toString().indexOf("Chats/") != -1) {
			dir.delete();
			Log.i("Dfd", "no chats : " + dir);
		}

	}

}

class MainAdapter extends BaseAdapter {

	DataManager dataManager;
	Context mainContext;
	LayoutInflater Inflater;
	List<String> list;
	int layout;

	public MainAdapter(Context context, int pLayout, List<String> mFileNames) {

		mainContext = context;
		Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = mFileNames;
		layout = pLayout;
		dataManager = new DataManager(mainContext);
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

		convertView.setBackgroundResource(R.drawable.list_selector);

		TextView name = (TextView) convertView.findViewById(R.id.main_name);
		ImageView im = (ImageView) convertView.findViewById(R.id.imageBtn);

		name.setText("with " + list.get(pos));
		name.setTextColor(Color.BLACK);

		final View diaLayout = Inflater.inflate(R.layout.save_talk_dialog,
				parent, false);

		im.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = list.get(pos);

				Intent intent = new Intent(mainContext, TabHostActivity.class);
				intent.putExtra("otherName", name);
				mainContext.startActivity(intent);
			}
		});

		im.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				final String name = list.get(pos);

				final Dialog characDialog = new Dialog(mainContext);
				characDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				characDialog.setContentView(R.layout.delete_talk_dialog);
				characDialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(Color.TRANSPARENT));
				ImageView dialogButton = (ImageView) characDialog
						.findViewById(R.id.delete_btn);
				dialogButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						dataManager.deleteOther(name);
						dataManager.deleteTalk(name);
						dataManager.deleteSaveTalk(name);
						dataManager.deleteDesc(name);

						list.remove(pos);
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
