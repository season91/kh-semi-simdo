package com.kh.simdo.mypage.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;
import com.kh.simdo.mypage.model.vo.Wish;

public class UserReviewDao {
	
	public UserReviewDao() {
		// TODO Auto-generated constructor stub
	}
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	//달력눌렸을때 DB 인설트메서드
	//달력눌렀을때 DB 업데이트
	
	/**
	 * 
	 * @author 조민희
	 */
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
			throw new DataAccessException(ErrorCode.SRV01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return reviewList;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public Map<String, Object> selectReviewByReviewNo(Connection conn, int reviewNo) {
		
		Map<String, Object> reviewContent = new HashMap<>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select u.review_no, u.user_no, u.score, u.rv_reg_date, u.rv_content, u.watch_date, "
					+ "u.mv_no, u.mv_title, u.thumbnail, m.poster "
					+ "from user_review u inner join mv_basic_info m on(u.mv_no = m.mv_no) "
					+ "where review_no = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, reviewNo);
			
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				
				UserReview userReview = new UserReview();
				String poster = null;
				
				userReview.setReviewNo(rset.getInt("review_no"));
				userReview.setUserNo(rset.getInt("user_no"));
				userReview.setScore(rset.getDouble("score"));
				userReview.setRvRegDate(rset.getDate("rv_reg_date"));
				userReview.setRvContent(rset.getString("rv_content"));
				userReview.setWatchDate(rset.getDate("watch_date"));
				userReview.setMvNo(rset.getString("mv_no"));
				userReview.setMvTitle(rset.getString("mv_title"));
				userReview.setThumbnail(rset.getString("thumbnail"));
				poster = rset.getString("poster");
				
				reviewContent.put("userReview", userReview);
				reviewContent.put("poster", poster);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.SRV01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return reviewContent;
		
	}
	

	/**
	 * 
	 * @author 조민희
	 */
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
			throw new DataAccessException(ErrorCode.SFL01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return fmslineList;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public Map<String, Object> selectFmslineByFmslineNo(Connection conn, int fmslineNo) {

		Map<String, Object> fmslineContent = new HashMap<>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String query = "select u.fmsline_no, u.user_no, u.fml_content, u.mv_no, u.mv_title, u.thumbnail, m.poster "
					+ "from user_fmsline u inner join mv_basic_info m on(u.mv_no = m.mv_no) "
					+ "where u.fmsline_no = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, fmslineNo);
			
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				
				UserFmsline userFmsline = new UserFmsline();
				String poster = null;
				
				userFmsline.setFmslineNo(rset.getInt("fmsline_no"));
				userFmsline.setUserNo(rset.getInt("user_no"));
				userFmsline.setFmlContent(rset.getString("fml_content"));
				userFmsline.setMvNo(rset.getString("mv_no"));
				userFmsline.setMvTitle(rset.getString("mv_title"));
				userFmsline.setThumbnail(rset.getString("thumbnail"));
				poster = rset.getString("poster");
				
				fmslineContent.put("userFmsline", userFmsline);
				fmslineContent.put("poster", poster);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.SFL01, e);
		} finally {
			jdt.close(rset, pstm);
		}
		
		return fmslineContent;
		
	}
	
	/**
	 * 
	 * @author 조민희
	 */
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
			throw new DataAccessException(ErrorCode.DRV01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @author 조민희
	 */
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
			throw new DataAccessException(ErrorCode.DFL01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public int updateReview(Connection conn, UserReview userReview) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			String query = "update user_review set score = ?, rv_reg_date = sysdate, rv_content = ?, "
					+ "watch_date = ? where review_no = ?";
			pstm = conn.prepareStatement(query);
			pstm.setDouble(1, userReview.getScore());
			pstm.setString(2, userReview.getRvContent());
			pstm.setDate(3, userReview.getWatchDate());
			pstm.setInt(4, userReview.getReviewNo());
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.URV01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public int updateFmsline(Connection conn, UserFmsline userFmsline) {
		
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			
			String query = "update user_fmsline set fml_content = ? "
					+ "where fmsline_no = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userFmsline.getFmlContent());
			pstm.setInt(2, userFmsline.getFmslineNo());
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.UFL01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	

	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 5.
	   @param conn
	   @param mvNo
	   @return 유저닉네임과 명대사객체, 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserFmsline, String)
	   @work :영화상세내용에 보여줄 명대사 리스트
	 */
	
	public List selectFmslineByMvNo(Connection conn, String mvNo){
		UserFmsline userFmsline = null;
		List reviewList = new ArrayList();
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String sql = "select u.fmsline_no, u.user_no, u.fml_content, u.mv_no, u.mv_title, u.thumbnail, us.user_nm from user_fmsline u "
					+"inner join \"USER\" us on(u.user_no = us.user_no) where u.mv_no = ? and us.is_leave = 0";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mvNo);
			rset = pstm.executeQuery();
			
			while(rset.next()) {

				userFmsline = new UserFmsline();
				userFmsline.setFmslineNo(rset.getInt("fmsline_no"));
				userFmsline.setUserNo(rset.getInt("user_no"));
				userFmsline.setFmlContent(rset.getString("fml_content"));
				userFmsline.setMvNo(rset.getString("mv_no"));
				userFmsline.setMvTitle(rset.getString("mv_title"));
				userFmsline.setThumbnail(rset.getString("thumbnail"));
				Map<String, Object> commandMap = new HashMap<String, Object>();
				commandMap.put("fmsline", userFmsline);
				commandMap.put("nick", rset.getString("user_nm"));
				
				reviewList.add(commandMap);
				
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SFL02, e);
		} finally {
			jdt.close(rset, pstm);
		}
		System.out.println(reviewList);
		return reviewList;
	}
	
	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 5.
	   @param conn
	   @param mvNo
	   @return 유저닉네임과 리뷰객체, 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserReview, String)
	   @work :영화상세내용에 보여줄 리뷰 리스트
	 */
	public List selectReviewByMvNo(Connection conn, String mvNo){
		UserReview userReview = null;
		List reviewList = new ArrayList();
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			String sql = "select u.review_no, u.user_no, u.score, u.rv_reg_date, u.rv_content, u.watch_date, u.mv_no, u.mv_title, u.thumbnail, us.user_nm from user_review u "
					+"inner join \"USER\" us on(u.user_no = us.user_no) where u.mv_no = ? and us.is_leave = 0 order by u.rv_reg_date desc";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mvNo);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				userReview = new UserReview();
				Map<String, Object> commandMap = new HashMap<String, Object>();
				
				userReview.setReviewNo(rset.getInt("review_no"));
				userReview.setUserNo(rset.getInt("user_no"));
				userReview.setScore(rset.getDouble("score"));
				userReview.setRvRegDate(rset.getDate("rv_reg_date"));
				userReview.setRvContent(rset.getString("rv_content"));
				userReview.setWatchDate(rset.getDate("watch_date"));
				userReview.setMvNo(rset.getString("mv_no"));
				userReview.setMvTitle(rset.getString("mv_title"));
				userReview.setThumbnail(rset.getString("thumbnail"));
				commandMap.put("review", userReview);
				commandMap.put("nick", rset.getString("user_nm"));
				
				reviewList.add(commandMap);
				
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SU01, e);
		} finally {
			jdt.close(rset, pstm);
		}

		return reviewList;
	}
	
	
	/**
	 * @author 조아영
	 * 상세화면 내에서 찜목록 추가
	 */
	
	public int insertWish(Connection conn, int userNo, Movie movie) {
		int res = 0;
		PreparedStatement pstm = null;
		String sql = "insert into user_whishmv (wish_no, user_no, mv_no, poster) values(sc_wish_no.nextval,?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userNo);
			pstm.setString(2, movie.getMvNo());
			pstm.setString(3, movie.getPoster());
			
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.IW01,e);
		} finally {
			jdt.close( pstm);
		}
	
		return res;
	}
	

	/**
	 * @author 조아영
	 * 영화 상세에서 찜 해지시 db삭제
	 */
	public int deleteWish(Connection conn, int userNo, String mvNo) {
		int res = 0;
		PreparedStatement pstm = null;
		
		try {
			String sql = "delete from user_whishmv where user_no = ? and mv_no = ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userNo);
			pstm.setString(2, mvNo);
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(ErrorCode.DW01, e);
		} finally {
			jdt.close(pstm);
		}
		
		return res;
	}
	
	
	/**
	 * @author 조아영
	 * 영화상세 내에서 찜목록 여부 확인해야 색칠가능
	 */
	
	public Wish selectWish(Connection conn, int userNo, String mvNo){
		Wish wish = null;
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		
		try {
			String sql = "select * from user_whishmv where user_no = ? and mv_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, userNo);
			pstm.setString(2, mvNo);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				wish = new Wish();
				wish.setMvNo(rset.getString("mv_no"));
				
			}
		} catch (SQLException e) {
			throw new DataAccessException(ErrorCode.SW01,e);
		} finally {
			jdt.close(rset, pstm);
		}
		return wish;
	}
	
	
}
