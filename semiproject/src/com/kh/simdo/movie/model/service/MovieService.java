package com.kh.simdo.movie.model.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	// [3] 영화 상세정보 관련 메서드
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
	
	// [2] 영화 조회 메서드
	//영화 상세정보 조회
	public Movie selectMovieByMvNo(String mvNo){
		System.out.println("selectDetail"+mvNo);
		Movie res = new Movie();
		Connection conn = jdt.getConnection();
		try {
			res = movieDao.selectMovieByMvNo(conn, mvNo);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 장르별 조회
	public List<Movie> selectMovieByGenre(String genre){
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		System.out.println("selectGenre서비스"+genre);
		try {
			res = movieDao.selectMovieByGenre(conn, genre);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 나라별 조회
	public List<Movie> selectMovieByNation(String nation){
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		System.out.println("selectNation"+nation);
		try {
			res = movieDao.selectMovieByNation(conn, nation);
		} finally {
			jdt.close(conn);
		}
		return res;
	}
	
	// 영화 정보 검색으로 영화정보 조회해서 가져오기.
	public List<Movie> selectMovieByTitle(String searchTitle) {
		System.out.println("selectSearchTitle"+searchTitle);
		List<Movie> res = new ArrayList<Movie>();
		Connection conn = jdt.getConnection();
		try {
			res = movieDao.selectMovieByTitle(conn, searchTitle);
		} finally {
			jdt.close(conn);
		}
	
		return res;
	}
	
	// [1] DB 넣는 메서드부분
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
	
	// DB에 넣는 메서드
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


	// 1. vo넣기전에 json파일을 한번 더 분해해야 한다. 매개변수로 분해 기준 카테고리 받는다.
	// DB용 메서드로 API통신후 받은 json을 필요에따라 추가 분해해야하는 경우가 있어 기능분리
	public Map<String, Object> listSeparation(Map<String, Object> map, String beforecategory, String aftercategory) {
		Gson gson = new Gson();
		Map<String, Object> resMap = (Map<String, Object>) map.get(beforecategory);
		ArrayList<String> resList = (ArrayList<String>) resMap.get(aftercategory);
		String resStr = gson.toJson(resList.get(0));
		Map<String, Object> res = gson.fromJson(resStr, Map.class);

		return res;
	}

	// 2. vo넣기 전 Date를 util->sql 타입으로 변경해주기.
	// DB용 메서드로 날짜 형변환 해주는 메서드
	public Date transformDate(String strDate) {
		// 개봉일자는 String -> util.date -> sql.date 로 변환을 해주어야 한다.
		// util.date로 변환해주기.
		SimpleDateFormat beforFormat = new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date tempDate = null;
		try {
			tempDate = beforFormat.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sql.date로 변환해주기.
		String transDate = afterFormat.format(tempDate);
		Date date = Date.valueOf(transDate);
		return date;
	}

	// 3. vo에 넣기
	// DB용 메서드로 vo객체에 전달받은 json값을 하나씩 넣어주는 메서드
	
	public Movie addMovieVo(Map<String, Object> movieDB, String thumbnail) {
		// 1. KMDB 영화정보 넣어주기
		Gson gson = new Gson();
		Movie movie = null;

		movie = new Movie();
		movie.setMvNo((String) movieDB.get("DOCID"));
		movie.setMvTitle((String) movieDB.get("title"));
		movie.setMvTitleorg((String) movieDB.get("titleOrg"));

		// 한번더 분해
		Map<String, Object> directDB = listSeparation(movieDB, "directors", "director");
		movie.setDirector((String) directDB.get("directorNm"));

		movie.setGenre((String) movieDB.get("genre"));

		// 개봉일자는 String -> util.date -> sql.date 로 변환을 해주어야 한다.
		Date date = transformDate((String) movieDB.get("repRlsDate"));
		movie.setReleaseDate(date);

		// 한번더 분해
		Map<String, Object> plotDB = listSeparation(movieDB, "plots", "plot");
		movie.setPlot((String) plotDB.get("plotText"));

		movie.setNation((String) movieDB.get("nation"));
		movie.setRuntime(Integer.parseInt((String) movieDB.get("runtime")));
		movie.setRating((String) movieDB.get("rating"));

		String str = (String) movieDB.get("posters");
		String[] pstArr = str.split("[|]");
		movie.setPoster(pstArr[0]);
		
		// 2.네이버 API 영화 썸네일 넣어주기

		movie.setThumbnail(thumbnail);

		return movie;
	}

}
