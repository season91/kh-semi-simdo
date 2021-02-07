package com.kh.simdo.mypage.model.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.kh.simdo.common.exception.DataAccessException;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.jdbc.JDBCTemplate;
import com.kh.simdo.common.util.http.HttpUtils;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.dao.UserReviewDao;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;

public class UserReviewService {
	
	UserReviewDao userReviewDao = new UserReviewDao();
	JDBCTemplate jdt = JDBCTemplate.getInstance();

	/**
	 * 
	 * @Author : MinHee
	 */
	public List<UserReview> selectReviewByUserNo(int UserNo) {
		
		Connection conn = jdt.getConnection();
		List<UserReview> reviewList = null;
		try {
			reviewList = userReviewDao.selectReviewByUserNo(conn, UserNo);
		}finally {
			jdt.close(conn);
		}
		return reviewList;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public Map<String, Object> selectReviewByReviewNo(int reviewNo) {
		
		Connection conn = jdt.getConnection();
		Map<String, Object> reviewContent = null;
		try {
			reviewContent = userReviewDao.selectReviewByReviewNo(conn, reviewNo);
		}finally {
			jdt.close(conn);
		}
		return reviewContent;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 */
	public List<UserFmsline> selectFmslineByUserNo(int UserNo) {
		
		Connection conn = jdt.getConnection();
		List<UserFmsline> fmslineList = null;
		try {
			fmslineList = userReviewDao.selectFmslineByUserNo(conn, UserNo);
		}finally {
			jdt.close(conn);
		}
		return fmslineList;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public Map<String, Object> selectFmslineByFmslineNo(int fmslineNo) {
		
		Connection conn = jdt.getConnection();
		Map<String, Object> fmslineContent = null;
		try {
			fmslineContent = userReviewDao.selectFmslineByFmslineNo(conn, fmslineNo);
		}finally {
			jdt.close(conn);
		}
		return fmslineContent;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 */
	public int deleteReview(int reviewNo) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userReviewDao.deleteReview(conn, reviewNo);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		return res;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 */
	public int deleteFmsline(int fmslineNo) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userReviewDao.deleteFmsline(conn, fmslineNo);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public int updateReview(UserReview userReview) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userReviewDao.updateReview(conn, userReview);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	public int updateFmsline(UserFmsline userFmsline) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			res = userReviewDao.updateFmsline(conn, userFmsline);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			// TODO: handle exception
			jdt.rollback(conn);
		} finally {
			jdt.close(conn);
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @Author : 아영
	 */
	public String papagoAPI(String paramText, String paramLan) {
		Gson gson = new Gson();
		HttpUtils util = new HttpUtils();
		String clientId = "1TOE19GYAcgawcD0ESm1";
		String clientSecret = "tmgwvMjtQF";
		
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt"; 
		String text;
		String body;
		String lan = paramLan;
		
		try {
			text = URLEncoder.encode(paramText, "UTF-8");
			body = "source=ko&target="+lan+"&text=" + text;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("번역 인코딩 실패", e);
		}
		
		// json 결과
		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		
		String jsonRes = util.post(apiURL, body, requestHeaders);
		Map<String, Object> mapRes = gson.fromJson(jsonRes, Map.class);
		
		//ArrayList<String> itemList = (ArrayList<String>) mapRes.get("message");
		Map<String, Object> mapRes1 = (Map<String, Object>) mapRes.get("message");
		Map<String, Object> mapRes2 = (Map<String, Object>) mapRes1.get("result");
		String res = (String) mapRes2.get("translatedText");
		return res;
	}
	
	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 5.
	   @param mvNo
	   @return 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserReview, string)
	   @work :
	 */
	public List selectFmslineByMvNo(String mvNo) {
		Connection conn = jdt.getConnection();
		List<UserReview> reviewList = null;
		try {
			reviewList = userReviewDao.selectFmslineByMvNo(conn, mvNo);
		}finally {
			jdt.close(conn);
		}
		return reviewList;
	}
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 5.
	   @param mvNo
	   @return 제네릭 미설정이유는 객체 타입이 2개이기 떄문임. (UserReview, User)
	   @work : 영화 상세정보에 나올 유저 리뷰
	 */
	
	
	public List selectReviewByMvNo(String mvNo) {
		Connection conn = jdt.getConnection();
		List<UserReview> reviewList = null;
		try {
			reviewList = userReviewDao.selectReviewByMvNo(conn, mvNo);
		}finally {
			jdt.close(conn);
		}
		return reviewList;
	}
	
	
}
