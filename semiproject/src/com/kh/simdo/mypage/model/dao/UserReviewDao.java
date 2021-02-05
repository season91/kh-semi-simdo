package com.kh.simdo.mypage.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;

public class UserReviewDao {
	
	public UserReviewDao() {
		// TODO Auto-generated constructor stub
	}
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public List<UserReview> selectReviewByUserNo(Connection conn, int userNo) {
		
		List<UserReview> reviewList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select u.review_no, u.user_no, u.score, u.rv_reg_date, u.rv_content, u.watch_date, u.mv_no, u.mv_title, u.thumbnail "
					+ "from user_review u inner join \"USER\" us on(u.user_no = us.user_no) "
					+ "where u.user_no = ? and us.is_leave = 0"
					+ "order by u.rv_reg_date desc";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userNo);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
				UserReview userReview = new UserReview();
				userReview.setReviewNo(rset.getInt("review_no"));
				userReview.setUserNo(rset.getInt("user_no"));
				userReview.setScore(rset.getDouble("score"));
				userReview.setRvRegDate(rset.getDate("rv_reg_date"));
				userReview.setRvContent(rset.getString("rv_content"));
				userReview.setWatchDate(rset.getDate("watch_date"));
				userReview.setMvNo(rset.getString("mv_no"));
				userReview.setMvTitle(rset.getString("mv_title"));
				userReview.setThumbnail(rset.getString("thumbnail"));
				reviewList.add(userReview);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return reviewList;
		
	}
	
	public List<UserFmsline> selectFmslineByUserNo(Connection conn, int userNo) {
		
		List<UserFmsline> fmslineList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select u.fmsline_no, u.user_no, u.fml_content, u.mv_no, u.mv_title, u.thumbnail "
					+ "from user_fmsline u inner join \"USER\" us on(u.user_no = us.user_no) "
					+ "where u.user_no = ? and us.is_leave = 0";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userNo);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
				UserFmsline userFmsline = new UserFmsline();
				userFmsline.setFmslineNo(rset.getInt("fmsline_no"));
				userFmsline.setUserNo(rset.getInt("user_no"));
				userFmsline.setFmlContent(rset.getString("fml_content"));
				userFmsline.setMvNo(rset.getString("mv_no"));
				userFmsline.setMvTitle(rset.getString("mv_title"));
				userFmsline.setThumbnail(rset.getString("thumbnail"));
				fmslineList.add(userFmsline);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return fmslineList;
		
	}
	
	public int deleteReview(Connection conn, int reviewNo) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			String query = "delete from user_review where review_no = ?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, reviewNo);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.DU01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int deleteFmsline(Connection conn, int fmslineNo) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			String query = "delete from user_fmsline where fmsline_no = ?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, fmslineNo);
			
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
