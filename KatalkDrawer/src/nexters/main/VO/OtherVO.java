package nexters.main.VO;

public class OtherVO {

	private int id;
	private String other_name;
	private String other_image;
	private int first_date;
	private int last_date;
	private int save_date;
	
	public OtherVO(int id, String other_name, String other_image,
			int first_date, int last_date, int save_date) {
		super();
		this.id = id;
		this.other_name = other_name;
		this.other_image = other_image;
		this.first_date = first_date;
		this.last_date = last_date;
		this.save_date = save_date;
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
	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}
	public String getOther_image() {
		return other_image;
	}
	public void setOther_image(String other_image) {
		this.other_image = other_image;
	}
	public int getFirst_date() {
		return first_date;
	}
	public void setFirst_date(int first_date) {
		this.first_date = first_date;
	}
	public int getLast_date() {
		return last_date;
	}
	public void setLast_date(int last_date) {
		this.last_date = last_date;
	}
	public int getSave_date() {
		return save_date;
	}
	public void setSave_date(int save_date) {
		this.save_date = save_date;
	}
	
	
}
