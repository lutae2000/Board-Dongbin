package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/*DB로 연결 실패시 */
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";		//getConnection 인자값 세팅을 위한, 변수 만들기
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");					//드라이버 로드  ***********************************
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);	//getConnection으로 연결 ***********************************
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*로그인 기능 메소드(함수)*/
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);				//pstmt = 데이터베이스로 인자값을 전달 
			pstmt.setString(1, userID);						//login함수 호출 시 매개값으로 받은 userID를 데이터베이스로 전달 
			rs=pstmt.executeQuery();						//rs = 쿼리 실행 
			if(rs.next()) {                                       //
				if(rs.getString(1).equals(userPassword))
					return 1; // 로그인 성공
				else
					return 0; // 비밀번호 불일치
			}
			return -1;	//아이디가 없음
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
		}
		return -1;  //데이터베이스 오류
	}
}
