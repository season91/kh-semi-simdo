package com.kh.simdo.movie.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.simdo.movie.model.service.MovieService;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.service.MypageService;
import com.kh.simdo.mypage.model.vo.UserReview;
import com.kh.simdo.mypage.model.vo.Wish;
import com.kh.simdo.user.model.vo.User;
/**
 * @author 조아영
 */
/**
 * Servlet implementation class MovieController
 */
@WebServlet("/movie/*")
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MypageService mypageService = new MypageService();
	MovieService movieService = new MovieService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");

		switch (uriArr[uriArr.length - 1]) {
		case "db.do": setDB(); break;
		case "naviview.do": searchNavi(request, response); break;
		case "scoreview.do": selectMovieByScore(request, response); break;
		case "reviewview.do": selectMovieByReviewCount(request,response); break;
		case "detailview.do": readMore(request, response); break;
		case "searchview.do": searchTitle(request, response); break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// [3]. 영화 상세 화면 메서드
	// 기능분리 : JSP로 보내기 위해 json 파싱하기, 평점 계산하기, 상세화면 상단에 들어갈 명대사 추출하기.
	// 1. 영화와 후기,명대사 검색
	protected void readMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mvNo = request.getParameter("mvno");
		// 찜했다면 찜하트 색칠해주어야함, 후기썻다면 후기 색 색칠해주어야함.
		// user session 있으면 wish 찾을 것.
		User user = (User) request.getSession().getAttribute("user");
		Wish wish = null;
		UserReview userReview = null;
		if(user != null) {
			int userNo = user.getUserNo();
			wish = mypageService.selectWish(userNo, mvNo);
			userReview = mypageService.selectReviewByUserNoMvNo(userNo, mvNo);
		}
		
		request.setAttribute("wish", wish);
		request.setAttribute("write", userReview);		
		
		// 영화 번호로 영화정보와 고객리뷰, 명대사정보 가져오기.
		// 1. 영화정보 가져오기
		Movie detailRes = movieService.selectMovieByMvNo(mvNo);
		request.setAttribute("res", detailRes);
		// 2. 영화리뷰, 명대사 가져오고 파싱해주기 
		List reviewList = mypageService.selectReviewByMvNo(mvNo);
		List fmsList = mypageService.selectFmslineByMvNo(mvNo);		
		// 후기출력
		List parseJsonrev = parseJson(reviewList);
		request.setAttribute("reviewList", parseJsonrev);
		// 평점 출력
		String scoreAvg = scoreAvg(parseJsonrev);
		request.setAttribute("score", scoreAvg);
		List parseJsonfms = parseJson(fmsList);
		request.setAttribute("fmsList", parseJsonfms);
		
		
		
		String headfms = null;
		if(parseJsonfms.size() > 0) {
			headfms = headFms(parseJsonfms);
		} 
		// 후기 유저 조건문 추가 
		// 상세화면 상단에 넣어줄 명대사 출력.
		request.setAttribute("headfms", headfms);
		request.getRequestDispatcher("/WEB-INF/view/movie/detailview.jsp").forward(request, response);
	}
	// 2. 평점구하는 메서드
	protected String scoreAvg(List reviewList) {
		double parseScore = 0.0;
		for (int i = 0; i < reviewList.size(); i++) {
			String json = new Gson().toJson(reviewList.get(i));
			Map<String, Object> commandMap =  new Gson().fromJson(json, Map.class);
			Map<String, String>  res = (Map<String, String>) commandMap.get("review");
			String score = String.valueOf(res.get("score"));
			parseScore += Double.parseDouble(score);
		}
		double scoreAvg = parseScore / reviewList.size();
		String avg = String.format("%.1f", scoreAvg);
		return avg;
	}
	
	// 3. 영화 명대사 출력 메서드 기준: 젤 첫번째꺼
	protected String headFms(List fmsList) {
		String json = new Gson().toJson(fmsList.get(0));
		Map<String, Object> commandMap =  new Gson().fromJson(json, Map.class);
		Map<String, String>  res = (Map<String, String>) commandMap.get("fmsline");
		String headfms  = res.get("fmlContent");
		return headfms;
	}
	
	// [2]. navi 메뉴들 조회하는 메서드
	// 기능분리 : 각 메뉴별 조회 메서드 / JSP로 보내기 위한 json 파싱용 메서드.
	
	// 1. 영화용 파싱 메서드 영화정보를 jsp로 보내려면 gson을 이용해 map obj로 변환해주어야함. 자주사용해서 기능분리.
	protected List parseJson(List res) {
		List list = new ArrayList();
		Map<String, Object> commandMap = new HashMap<String, Object>();
		for (int i = 0; i < res.size(); i++) {
			String json = new Gson().toJson(res.get(i));
			commandMap = new Gson().fromJson(json, Map.class);
			list.add(commandMap);
		}
		return list;
	}

	// navi 메뉴1,2. 나라별, 장르별 조회 메서드.
	protected void searchNavi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// navi 확장메뉴인 나라, 장르 파라미터 값에 따라 장르별조회할지 나라별조회할지 선택.
		String nation = request.getParameter("nation");
		String genre = request.getParameter("genre");
		List movieList = new ArrayList();
		
		if(nation == null && genre != null) {
			System.out.println("장르로조회");
			List<Movie> genreRes = movieService.selectMovieByGenre(genre);
			movieList = parseJson(genreRes);
			request.setAttribute("navi", "장르 : "+genre);
		} else if(nation != null && genre == null){
			System.out.println("나라로 조회");
			List<Movie> nationRes = movieService.selectMovieByNation(nation);
			movieList = parseJson(nationRes);
			request.setAttribute("navi", "나라 : "+nation);
			
		}
		request.setAttribute("res", movieList);
		request.getRequestDispatcher("/WEB-INF/view/movie/naviview.jsp").forward(request, response);
	}

	// navi 메뉴 3. 평점순 상위 10개만 조회해주는 거로
	protected void selectMovieByScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int count = 7;
		List<Movie> scoreRes = movieService.selectMovieByScore(count);
		List movieList = parseJson(scoreRes);
		request.setAttribute("navi", "평점순(상위 7개)");
		request.setAttribute("res", movieList);
		request.getRequestDispatcher("/WEB-INF/view/movie/scoreview.jsp").forward(request, response);
	}

	
	
	// navi 메뉴 4. 후기개수순 조회, 후기개수2개 이상인 것들 조회.
	protected void selectMovieByReviewCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Movie> res = movieService.selectMovieByReviewCount();
		// 영화 번호 추출해서 후기 가져온다.
		
		//영화 정보 담긴 리스트
		List movieList = parseJson(res);
		Gson gson = new Gson();
		
		// 영화 검섹 개수 만큼
		// 영화vo와 영화vo에맞는 후기들 List에 넣어주고 jsp에 넘겨주기
		List resList= new ArrayList();
		for (int i = 0; i < movieList.size(); i++) {
			// 영화 vo의 영화 번호 출력
			String resStr = gson.toJson(movieList.get(i));
			Map<String,Movie> resmap = gson.fromJson(resStr, Map.class);
		
			// 영화 번호기준으로 리뷰리스트 가져오기
			List reviewRes = mypageService.selectReviewByMvNo(String.valueOf(resmap.get("mvNo")));
			List parseRes = parseJson(reviewRes);
			
			//해당영화vo와 그영화의 리뷰 map에 담아주기
			Map commadMap = new HashMap();
			commadMap.put("movie", movieList.get(i));
			commadMap.put("reviews", parseRes);
			// jsp로 보낼최종 리스트에 담아주기
			resList.add(commadMap);
		}
		System.out.println(resList);
		request.setAttribute("res", resList);
		request.getRequestDispatcher("/WEB-INF/view/movie/reviewview.jsp").forward(request, response);
			
	
	}
	
	
	// navi 메뉴 5. 영화제목으로 영화 검색하기.
	protected void searchTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchTitle = request.getParameter("search");
		System.out.println(searchTitle);
		// 영화정보 받아오기 
		List<Movie> searchRes = movieService.selectMovieByTitle(searchTitle);
		List movieList = parseJson(searchRes);
		
		request.setAttribute("res", movieList);
		request.getRequestDispatcher("/WEB-INF/view/movie/searchview.jsp").forward(request, response);
	}

	
	// [1]. DB 넣는 메서드. 
	// 통신하는 API는 총 2개이다. (KMDB, 네이버영화)
	// 기능분리 : movie.vo 넣기 전에 json 분해 / vo넣기 전 날짜 타입 파싱하기 /  movie.vo에 넣기
	
	// DB용 메서드로 service에게 보내어 실제 DB에 넣는 메서드
	protected void setDB() {

		// 여기선 2가지 API 사용한다.
		// 기본정보는 KMDB를 사용
		// 썸네일정보는 달력API에서 동일규격 포스터사이즈를 사용해야하기에 네이버 영화 API 사용

		// KMDB 받은 자료
		Map<String, Object> movieDB = movieService.parseDb();
		// 네이버 API 받은 자료
		String thumbnail = movieService.parseThumb();
		// movie.vo에 넣어주기

		Movie movie = movieService.addMovieVo(movieDB, thumbnail);
		//System.out.println(movie);
		
		// DB에 movie넣는걸 성공했다면 성공알람 실패시 실패 알람
//			int movieRes = MovieService.insertMovieInfo(movie);
//			if (movieRes > 0) {
//				System.out.println("movie성공");
//			} else {
//				System.out.println("movie실패");
//			}
	}
	
}
