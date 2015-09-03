package nexters.main.VO;

public class DescVO {

	
	private String other_name;
	private int date;
	private String desc;
	
	public DescVO(String other_name, int date, String desc) {
		super();
		
		this.other_name = other_name;
		this.date = date;
		this.desc = desc;
	}

	public String getOther_name() {
		return other_name;
	}

	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
