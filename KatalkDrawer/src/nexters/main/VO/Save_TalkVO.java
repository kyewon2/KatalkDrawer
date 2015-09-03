package nexters.main.VO;

public class Save_TalkVO {

	private String other_name;
	private String talk_name;
	private int date;
	private String content;
	private String file_name;
	public Save_TalkVO(String other_name, String talk_name, int date,
			String content, String file_name) {
		super();
		this.other_name = other_name;
		this.talk_name = talk_name;
		this.date = date;
		this.content = content;
		this.file_name = file_name;
	}
	public String getOther_name() {
		return other_name;
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
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	
}
