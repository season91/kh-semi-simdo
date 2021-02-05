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
import com.kh.simdo.mypage.model.service.UserReviewService;
import com.kh.simdo.mypage.model.vo.UserReview;
/**
 * @author 조아영
 */
/**
 * Servlet implementation class MovieController
 */
@WebServlet("/movie/*")
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		case "scoreview.do": searchReview(request, response); break;
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
	
	// 상위 10개만 조회해주는 거로
	protected void searchReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/movie/scoreview.jsp").forward(request, response);
	}
	
	
	//평점구하는 메서드
	protected String scoreAvg(List reviewList) {
		double parseScore = 0.0;
		for (int i = 0; i < reviewList.size(); i++) {
			String json = new Gson().toJson(reviewList.get(i));
			Map<String, Object> commandMap =  new Gson().fromJson(json, Map.class);
			Map<String, String>  res = (Map<String, String>) commandMap.get("review");
			//String ia = String.valueOf(res.get("score"));
			String score = String.valueOf(res.get("score"));
			parseScore += Double.parseDouble(score);
		}
		double scoreAvg = parseScore / reviewList.size();
		String avg = String.format("%.1f", scoreAvg);
		return avg;
	}
	
	// 영화 명대사 출력 메서드 기준: 젤 첫번째꺼
	protected String headFms(List fmsList) {
		String json = new Gson().toJson(fmsList.get(0));
		Map<String, Object> commandMap =  new Gson().fromJson(json, Map.class);
		Map<String, String>  res = (Map<String, String>) commandMap.get("fmsline");
		String headfms  = res.get("fmlContent");
		return headfms;
	}
	// 더보기 메서드
	protected void readMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mvNo = request.getParameter("mvno");
		// 영화 제목으로 영화정보와 고객리뷰, 명대사
		// 제목검색이랑 같이쓰려고했는데, 제목검색부분은 너프한기준이고 여기는 딱맞게 1개여애해서 따로 dao 생성하기로 결심
		Movie detailRes = movieService.selectDetail(mvNo);
		request.setAttribute("res", detailRes);
		
		List reviewList = movieService.selectReviewByMvNo(mvNo);
		List fmsList = movieService.selectFmslineByMvNo(mvNo);
		
		
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
		// 상세화면 상단에 넣어줄 명대사 출력.
		request.setAttribute("headfms", headfms);
		
		request.getRequestDispatcher("/WEB-INF/view/movie/detailview.jsp").forward(request, response);
	}
	
	
	// 영화용 파싱 메서드 영화정보를 jsp로 보내려면 gson을 이용해 map obj로 변환해주어야함. 자주사용해서 기능분리.
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

	// navi메뉴인 나라별, 장르별 조회 메서드, service 부터는 분리되어있다.
	protected void searchNavi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// navi 확장메뉴인 나라, 장르 파라미터 값에 따라 장르별조회할지 나라별조회할지 선택.
		String nation = request.getParameter("nation");
		String genre = request.getParameter("genre");
		List movieList = new ArrayList();
		
		if(nation == null && genre != null) {
			System.out.println("장르로조회");
			List<Movie> genreRes = movieService.selectGenre(genre);
			movieList = parseJson(genreRes);
			request.setAttribute("navi", "장르 : "+genre);
		} else if(nation != null && genre == null){
			System.out.println("나라로 조회");
			List<Movie> nationRes = movieService.selectNation(nation);
			movieList = parseJson(nationRes);
			request.setAttribute("navi", "나라 : "+nation);
			
		}
		request.setAttribute("res", movieList);
		request.getRequestDispatcher("/WEB-INF/view/movie/naviview.jsp").forward(request, response);
	}

	protected void selectMovieByReviewCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Movie> res = movieService.selectMovieByReviewCount();
		// 영화 번호 추출해서 후기 가져온다.
		List mv_rvList = new ArrayList();
		
		//영화 정보 담긴 리스트
		List movieList = parseJson(res);
		
		List<String> mvnoList = new ArrayList<String>();
		Gson gson = new Gson();
		for (int i = 0; i < movieList.size(); i++) {
			String resStr =gson.toJson(movieList.get(i));
			Map resmap = gson.fromJson(resStr, Map.class);
			mvnoList.add((String) resmap.get("mvNo"));
		}
		
		Map<String, Object> resMap = new HashMap();
		
		// 생각해보니까 후기순인데 명대사는 굳이 없어도될 듯 왜냐면 상세화면 들어갈 이유가 없음..
		for (int i = 0; i < mvnoList.size(); i++) {
			List reviewRes = movieService.selectReviewByMvNo(mvnoList.get(i));
			List parseRes = parseJson(reviewRes);
			List reviewList =  new ArrayList();
			for (int j = 0; j < parseRes.size(); j++) {
				reviewList.add(parseRes.get(j));				
			}
			System.out.println(reviewList);
			resMap.put("moviereview", reviewList);
		}
		
		//검색된 영화 목록만큼 후기가 딸려와야함
		// 해당번쨰 영화찾아야하니깐 리스트로(영화리스트, 해당영화리뷰 맵)
		// 1번째영화 movie vo 객체가담긴 맵, 이영화 리뷰가 담긴 맵/ 총 2개의 맵이 담긴 list를 보내줘야한다.
		
		request.setAttribute("res", movieList);
		request.setAttribute("review", mv_rvList);
		request.getRequestDispatcher("/WEB-INF/view/movie/reviewview.jsp").forward(request, response);
	}
	
	
	// 영화제목 검색 메서드
	protected void searchTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchTitle = request.getParameter("search");
		System.out.println(searchTitle);
		// 영화정보 받아오기 
		List<Movie> searchRes = movieService.selectSearchTitle(searchTitle);
		List movieList = parseJson(searchRes);
		
		request.setAttribute("res", movieList);
		request.getRequestDispatcher("/WEB-INF/view/movie/searchview.jsp").forward(request, response);
	}

	// DB용 메서드로 API통신후 받은 json을 필요에따라 추가 분해해야하는 경우가 있어 기능분리
	protected Map<String, Object> listSeparation(Map<String, Object> map, String beforecategory, String aftercategory) {
		Gson gson = new Gson();
		Map<String, Object> resMap = (Map<String, Object>) map.get(beforecategory);
		ArrayList<String> resList = (ArrayList<String>) resMap.get(aftercategory);
		String resStr = gson.toJson(resList.get(0));
		Map<String, Object> res = gson.fromJson(resStr, Map.class);

		return res;
	}

	// DB용 메서드로 날짜 형변환 해주는 메서드
	protected Date transformDate(String strDate) {
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

	// DB용 메서드로 vo객체에 전달받은 json값을 하나씩 넣어주는 메서드
	protected Movie addMovieVo(Map<String, Object> movieDB, String thumbnail) {
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

	// DB용 메서드로 service에게 보내어 실제 DB에 넣는 메서드
	protected void setDB() {

		// 여기선 2가지 API를 섞어서 쓸것임
		// 기본정보는 KMDB를 사용
		// 썸네일정보는 달력API에서 동일규격 포스터사이즈를 사용해야하기에 네이버 영화 API 사용

		// KMDB 받은 자료
		Map<String, Object> movieDB = movieService.parseDb();
		// 네이버 API 받은 자료
		String thumbnail = movieService.parseThumb();
		// movie.vo에 넣어주기

		Movie movie = addMovieVo(movieDB, thumbnail);
		//System.out.println(movie);
		// DB에 movie넣는걸 성공했다면 성공알람 실패시 실패 알람
//		int movieRes = MovieService.insertMovieInfo(movie);
//		if (movieRes > 0) {
//			System.out.println("movie성공");
//		} else {
//			System.out.println("movie실패");
//		}
	}
}
