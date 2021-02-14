package com.kh.simdo.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.user.model.vo.User;

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 3.
 * @work :
 */
public class UserDao {
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public User userAuthenticate(Connection conn, String userEmail, String userPw) {
		
		User user = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select * from \"USER\" where user_email = ? and user_pw = ? and is_leave = 0";
			
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userEmail);
			pstm.setString(2, userPw);
			
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				
				user = new User();
				user.setUserNo(rset.getInt("user_no"));
				user.setUserEmail(rset.getString("user_email"));
				user.setUserPw(rset.getString("user_pw"));
				user.setUserTel(rset.getString("user_tel"));
				user.setUserNm(rset.getString("user_nm"));
				user.setUserGender(rset.getString("user_gender"));
				user.setUserBirth(rset.getDate("user_birth"));
				user.setIsLeave(rset.getInt("is_leave"));
				user.setUserProfile(rset.getString("user_profile"));
				user.setAdmin(rset.getString("admin"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);

		}
		
		return user;
		
	}
	
	public User selectUserByEmail(Connection conn, String userEmail) {
		
		User user = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select * from \"USER\" where user_email = ? and is_leave = 0";
			pstm = conn.prepareStatement(query); //연결을 통해 쿼리를 실행, 쿼리를 보냄
			pstm.setString(1, userEmail);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				
				user = new User();
				user.setUserNo(rset.getInt("user_no"));
				user.setUserEmail(rset.getString("user_email"));
				user.setUserPw(rset.getString("user_pw"));
				user.setUserTel(rset.getString("user_tel"));
				user.setUserNm(rset.getString("user_nm"));
				user.setUserGender(rset.getString("user_gender"));
				user.setUserBirth(rset.getDate("user_birth"));
				user.setIsLeave(rset.getInt("is_leave"));
				user.setUserProfile(rset.getString("user_profile"));
				
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return user;
		
	}
	
	public int insertUser(Connection conn, User user) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			String query = "insert into \"USER\"(user_no, user_email, user_pw, user_tel) values(sc_user_no.nextval, ?, ?, ?)";
			
			pstm = conn.prepareStatement(query);
			pstm.setString(1, user.getUserEmail());
			pstm.setString(2, user.getUserPw());
			pstm.setString(3, user.getUserTel());
			
			res = pstm.executeUpdate();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.IU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int insertKakaoUser(Connection conn, User user) {
		
		int res = 0;
		PreparedStatement pstm = null;
		String tempPw = UUID.randomUUID().toString();
		tempPw = tempPw.substring(0, 30);
		
		try {
			
			String query = "insert into \"USER\"(user_no, user_email, user_pw) values(sc_user_no.nextval, ?, ?)";
			
			pstm = conn.prepareStatement(query);
			pstm.setString(1, user.getUserEmail());
			pstm.setString(2, tempPw);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.IU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int insertNaverUser(Connection conn, User user) {
		
		int res = 0;
		PreparedStatement pstm = null;
		String tempPw = UUID.randomUUID().toString();
		tempPw = tempPw.substring(0, 30);
		
		try {
			
			String query = "insert into \"USER\"(user_no, user_email, user_pw) values(sc_user_no.nextval, ?, ?)";
			
			pstm = conn.prepareStatement(query);
			pstm.setString(1, user.getUserEmail());
			pstm.setString(2, tempPw);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.IU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int updateUser(Connection conn, User userBefore, User userAfter) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			String query = "update \"USER\" set user_profile = ?, user_nm = ?, user_pw = ?, user_tel = ?, user_gender = ?, user_birth = ?"
					+ " where user_email = ? and is_leave = 0";
			
			pstm = conn.prepareStatement(query);
			
			if(userAfter.getUserProfile() != null) {
				pstm.setString(1, userAfter.getUserProfile());
			}else {
				pstm.setString(1, userBefore.getUserProfile());
			}
			
			if(userAfter.getUserNm() != "") {
				pstm.setString(2, userAfter.getUserNm());
			}else {
				pstm.setString(2, userBefore.getUserNm());
			}
			
			if(userAfter.getUserPw() != "") {
				pstm.setString(3, userAfter.getUserPw());
			}else {
				pstm.setString(3, userBefore.getUserPw());
			}
			
			if(userAfter.getUserTel() != "") {
				pstm.setString(4, userAfter.getUserTel());
			}else {
				pstm.setString(4, userBefore.getUserTel());
			}
			
			if(userAfter.getUserGender() != "") {
				pstm.setString(5, userAfter.getUserGender());
			}else {
				pstm.setString(5, userBefore.getUserGender());
			}
			
			if(userAfter.getUserBirth() != null) {
				pstm.setDate(6, userAfter.getUserBirth());
			}else if(userBefore.getUserBirth() != null) {
				pstm.setDate(6, userBefore.getUserBirth());
			}else {
				pstm.setDate(6, null);
			}
			
			pstm.setString(7, userBefore.getUserEmail());
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.UU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int deleteUser(Connection conn, String userEmail) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			//update로 삭제
			String query = "update \"USER\" set is_leave = 1 where user_email = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userEmail);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.DU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
}
