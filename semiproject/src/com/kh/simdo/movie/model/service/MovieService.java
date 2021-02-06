package com.kh.simdo.movie.model.service;

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
import com.kh.simdo.movie.model.dao.MovieDao;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.vo.UserReview;
/**
 * 
 * @author 조아영
 *
 */
public class MovieService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	MovieDao movieDao = new MovieDao();
	
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
			reviewList = movieDao.selectFmslineByMvNo(conn, mvNo);
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
			reviewList = movieDao.selectReviewByMvNo(conn, mvNo);
		}finally {
			jdt.close(conn);
		}
		return reviewList;
	}
	
	public List<Movie> selectMovieByReviewCount(){
		Connection conn = jdt.getConnection();
		List<Movie> res = new ArrayList();
		try {
			res = movieDao.selectMovieByReviewCount(conn);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	/**
	 * 
	 * @Author :
	   @Date : 2021. 2. 6.
	   @param count
	   @return
	   @work :
	 */
	public List<Movie> selectMovieByScore(int count){
		Connection conn = jdt.getConnection();
		List<Movie> res = new ArrayList();
		try {
			res = movieDao.selectMovieBySocre(conn, count);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	
	//영화 상세정보 조회
	public Movie selectDetail(String mvNo){
		System.out.println("selectDetail"+mvNo);
		Movie res = new Movie();
		Connection conn = jdt.getConnection();
		try {
			res = movieDao.selectDetail(conn, mvNo);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 장르별 조회
	public List<Movie> selectGenre(String genre){
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		System.out.println("selectGenre서비스"+genre);
		try {
			res = movieDao.selectGenre(conn, genre);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 나라별 조회
	public List<Movie> selectNation(String nation){
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		System.out.println("selectNation"+nation);
		try {
			res = movieDao.selectNation(conn, nation);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 정보 검색으로 영화정보 조회해서 가져오기.
	public List<Movie> selectSearchTitle(String searchTitle) {
		System.out.println("selectSearchTitle"+searchTitle);
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		try {
			res = movieDao.selectSearchMovie(conn, searchTitle);
		} finally {
			jdt.close(conn);
		}
	
		return res;
	}
	
	
	// KMDB와 통신하는 메서드
	public Map<String, Object> parseDb() {
		HttpUtils utils = new HttpUtils();
		String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?ServiceKey=RLYJPR31F2X100MT6HX3&listCount=1&actor=로버트패틴슨&collection=kmdb_new2&detail=Y&query=크리스틴스튜어트&startCount=4";
		String jsonRes = utils.get(url);
		//System.out.println(jsonRes);
		Gson gson = new Gson();
		Map<String, Object> mapRes = gson.fromJson(jsonRes, Map.class);
		
		// 받은 자료 Data 값만 가져오게 분해 후 리턴
		ArrayList<String> dataList = (ArrayList<String>) mapRes.get("Data");
		String dataRes = gson.toJson(dataList.get(0));
		Map<String, Object> dataMap = gson.fromJson(dataRes, Map.class);

		// for문 반복사용 전 까지 맞춰줘야하므로 배열화까지 해서 리턴
		ArrayList<String> resultList = (ArrayList<String>) dataMap.get("Result");
		String resultRes = gson.toJson(resultList.get(0));
		Map<String, Object> resultMap = gson.fromJson(resultRes, Map.class);
		
		return resultMap;
		
	}

	// 네이버 API와 통신하는 메서드
	public String parseThumb() {
		HttpUtils util = new HttpUtils();
		String clientId = "1TOE19GYAcgawcD0ESm1";
		String clientSecret = "tmgwvMjtQF";
		Gson gson = new Gson();
		String title = null;

		try {
			title = URLEncoder.encode("브레이킹던", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + title +"&yearfrom=2012&yearto=2013"; // json 결과

		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);

		String jsonRes = util.get(apiURL, requestHeaders);
		System.out.println(jsonRes);
		
		Map<String, Object> mapRes = gson.fromJson(jsonRes, Map.class);
		ArrayList<String> itemList = (ArrayList<String>) mapRes.get("items");
		String itemRes = gson.toJson(itemList.get(0));
		Map<String, Object> itemMap = gson.fromJson(itemRes, Map.class);
		String thumb = (String) itemMap.get("image");
		System.out.println(thumb);
		return thumb;
	}
	
	public int insertMovieInfo(Movie movie) {
		Connection conn =jdt.getConnection();
		int res = 0;
		try {
			res = movieDao.insertMovieInfo(conn, movie);
			jdt.commit(conn);
		} catch (DataAccessException e) {
			jdt.rollback(conn);
			// Dao에서 DataAccessException발동하면 여기서 잡히게됨. 그럼 ExceptionHandler로 안가지니깐
			// ToAlertException에 DataAccessException의 에러정보 전달해준다.
			throw new ToAlertException(e.error);
		} finally {
			jdt.close(conn);
		}
		System.out.println("서비스");
		return res;
	}

}
