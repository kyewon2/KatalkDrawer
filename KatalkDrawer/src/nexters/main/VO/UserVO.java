package nexters.main.VO;

public class UserVO {

	private String name;
	private String image;
	public UserVO(String name, String image) {
		super();
		this.name = name;
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
