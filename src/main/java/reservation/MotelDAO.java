package reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MotelDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public MotelDAO() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "oracle";
		String upw = "oracle";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			System.out.println("Connect");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exit() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public DetailDTO selectMotel(String accomNum) {
		DetailDTO dto = new DetailDTO();
		ArrayList<String> themes = dto.getThemes();
		ArrayList<ReviewDTO> reviews = dto.getReviews();
		ArrayList<RoomDTO> rooms = dto.getRooms();
		
		// 숙소 정보 : 모텔 숫자, 모텔 이름, 모텔 주소, 모텔 상세이미지
		String accomQuery = "select accomm_num, name, address, detail_image from accommodation where accomm_num=?";
		
		// 테마정보 
		String themeQuery = "select c.name"
				+ " from accomm_condition m"
				+ " join condition c on c.num=m.condi_num"
				+ " join accommodation a on a.accomm_num=m.accomm_num"
				+ " where a.accomm_num=?";
		
		// 리뷰정보 : 제목, 평점, 방이름, 유저이메일, 내용, 작성시간
		String reviewQuery = "select r.title, r.score, r.room_name, r.email, r.content, r.create_date"
				+ " from review r join accommodation a on r.num=a.accomm_num"
				+ " where a.accomm_num=?";
		
		// 방정보: 싹다
		String roomQuery = "select r.* from room r join accommodation a on r.accomm_num=a.accomm_num where r.accomm_num=?";
		
		try {
			ps = conn.prepareStatement(accomQuery);
			ps.setString(1, accomNum);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto.setAccommNum(rs.getInt("accomm_num"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setDetailImage(rs.getString("detail_image"));
			}
			
			ps = conn.prepareStatement(themeQuery);
			ps.setString(1, accomNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto.addThemes(rs.getString("name"));
			}
			
			ps = conn.prepareStatement(reviewQuery);
			ps.setString(1, accomNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				// r.title, r.score, r.room_name, r.email, r.content, r.create_date
				ReviewDTO review = new ReviewDTO(rs.getString("title"), rs.getInt("score"), rs.getString("room_name"), 
												 rs.getString("email"), rs.getString("content"), rs.getString("create_date"));
				dto.addReviews(review);
			}
			
			ps = conn.prepareStatement(roomQuery);
			ps.setString(1, accomNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				RoomDTO room = new RoomDTO(rs.getInt("r_num"), rs.getString("name"), rs.getString("image"), rs.getInt("s_price"),
										   rs.getString("check_in"), rs.getString("check_out"), rs.getInt("d_price"), rs.getString("end_time"),
										   rs.getString("use_time"));
				dto.addRooms(room);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			exit();
		}
		
		return dto;
		
	}
}
