package reservation;

public class ReviewDTO {
	// 제목, 평점, 방이름, 유저이메일, 내용, 작성시간
	private String title;
	private int score;
	private String roomName;
	private String userNickname;
	private String content;
	private String createDate;
	
	public ReviewDTO() {}

	public ReviewDTO(String title, int score, String roomName, String userNickname, String content, String createDate) {
		super();
		this.title = title;
		this.score = score;
		this.roomName = roomName;
		this.userNickname = userNickname;
		this.content = content;
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
	

}
