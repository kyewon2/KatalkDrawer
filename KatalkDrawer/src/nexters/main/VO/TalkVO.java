package nexters.main.VO;

public class TalkVO {

	private int id;
	private String other_name;
	private String talk_name;
	private int date;
	private int time;
	private String content;

	public TalkVO(int id, String other_name, String talk_name, int date,
			int time, String content) {
		super();
		this.id = id;
		this.other_name = other_name;
		this.talk_name = talk_name;
		this.date = date;
		this.time = time;
		this.content = content;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOther_name() {
		return other_name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}

	public String getTalk_name() {
		return talk_name;
	}

	public void setTalk_name(String talk_name) {
		this.talk_name = talk_name;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
