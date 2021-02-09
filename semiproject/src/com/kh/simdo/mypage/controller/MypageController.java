package com.kh.simdo.mypage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.simdo.communication.model.service.CommunicationService;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.movie.model.service.MovieService;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.service.UserReviewService;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;
import com.kh.simdo.user.model.vo.User;

/**
 * @author 김종환(달력), 조민희(후기,명대사리스트), 조아영(QnA),김백관(메인, 찜목록, 공지사항)
 */
/**
 * Servlet implementation class MypageController
 */
@WebServlet("/mypage/*")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserReviewService userReviewService = new UserReviewService();
    CommunicationService communicationService = new CommunicationService();
    MovieService movieService = new MovieService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		
		switch(uriArr[uriArr.length - 1]) {
		case "mypage.do" : //김백관
			request.getRequestDispatcher("/WEB-INF/view/mypage/mypage.jsp").forward(request, response);
			break;
		case "mywish.do" : // 김백관 추가작업예정 찜목록
			request.getRequestDispatcher("/WEB-INF/view/mypage/mywish.jsp").forward(request, response);
			break;
		case "mywishadd.do" : // 조아영 찜목록 DB에 넣기
			myWishAdd(request,response);
			break;
		case "mywishdel.do" : // 조아영 찜목록 DB에 삭제
			myWishDel(request,response);
			break;
		case "mywritelist.do" : //조민희
			myWriteList(request, response);
			break;
		case "reviewdelete.do" : //조민희
			reviewDelete(request, response);
			break;
		case "fmslinedelete.do" : //조민희
			fmslineDelete(request, response);
			break;
		case "translation.do" : //넘기는목적, 하다가지울게요
			translation(request, response);
			break;
		case "translationimpl.do" : // 조민희, 조아영
			translationImpl(request, response);
			break;
		case "calendar.do": // 김종환
			calendar(request,response);
			break;
		case "callist.do" : // 김종환
			calendarList(request,response);
			break;
		case "mydailylist.do":  // 김종환
			myDailyList(request,response);
			break;
		case "writereview.do" : // 김종환
			writeReview(request, response);
			break;
		case "insertreview.do" : // 김종환
			insertReview(request, response);
			break;
		case "writeline.do" :  // 김종환
			writeLine(request, response);
			break;
		case "insetreline.do" : // 김종환
			insertLine(request, response);
			break;
		case "myqnalist.do" :  // 조아영
			myQnaList(request,response);
			break;
		case "myqnadetail.do" : // 조아영
			myQnaDetail(request,response); 
			break;
		case "myreviewupdate.do" : //조민희
			myReviewUpdate(request, response);
			break;
		case "myreviewupdateimpl.do" : //조민희
			myReviewUpdateImpl(request, response);
			break;
		case "mylineupdate.do" : //조민희
			myLineUpdate(request, response);
			break;
		case "mylineupdateimpl.do" : //조민희
			myLineUpdateImpl(request, response);
			break;
		}
	}
	
	/**
	 * @author 조아영
	 */
	private void myWishAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Movie movie = new Movie();
		movie.setMvNo(request.getParameter("mvno"));
		movie.setPoster(request.getParameter("poster"));
		
		int res = userReviewService.insertWish(user.getUserNo(), movie);
		if(res > 0) {
			System.out.println("성공");
			
		} else {
			System.out.println("실패");
		}
		
	}
	
	/**
	 * @author 조아영
	 */
	private void myWishDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String mvNo = request.getParameter("mvno");
		int res = userReviewService.deleteWish(user.getUserNo(), mvNo);
		
		if(res > 0) {
			System.out.println("삭제성공");
		} else {
			System.out.println("삭제실패");
		}
	}
	
	/**
	 * @author 조아영
	 * 
	 */
	
	 protected List parseJson(List res) {
			List list = new ArrayList();
			Map<String, Object> commandMap = new HashMap<String, Object>();
			for (int i = 0; i < res.size(); i++) {
				String json = new Gson().toJson(res.get(i));
				//System.out.println("전"+json);
				commandMap = new Gson().fromJson(json, Map.class);
				//System.out.println("후"+commandMap.get("qstnNo"));
				list.add(commandMap);
			}
			return list;
	}
	 /**
		 * @author 조아영
		 * 
		 */
	protected void myQnaList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징표현해주는 메서드
		String text = request.getParameter("page");
		User user = (User) request.getSession().getAttribute("user");
		List qnaList = new ArrayList();
		int page = 0;
		if(text == null) {
			page++;
		} else {
			page = Integer.parseInt(text);
		}
		
		int[] res = communicationService.selectPagingByQna(page);
		request.setAttribute("start", res[0]);
		request.setAttribute("end", res[1]);

		
		List<Map<String, Object>> pageRes = communicationService.selectQnaList(page, user.getUserNo());
		
		qnaList = parseJson(pageRes);
		request.setAttribute("res", qnaList);
		request.setAttribute("page", page);
		
		//score 글번호 releaseDate 작성일자 mvTitle 글제목
		
		request.getRequestDispatcher("/WEB-INF/view/mypage/myqnalist.jsp").forward(request, response);	
	}
	
	/**
	 * @author 조아영
	 * 
	 */
	protected void myQnaDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String qnaNo = String.valueOf(request.getParameter("qstnno"));
		int qstnNo = Integer.parseInt(qnaNo);
		
		Communication communication = communicationService.selectQnaByQstnNo(qstnNo);
		System.out.println(communication);
		request.setAttribute("res", communication);
		request.getRequestDispatcher("/WEB-INF/view/mypage/myqnadetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void myWriteList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		List<UserReview> reviewRes = userReviewService.selectReviewByUserNo(user.getUserNo());
		List<UserFmsline> fmslineRes = userReviewService.selectFmslineByUserNo(user.getUserNo());
		
		Map<String, Object> reviewCommandMap = new HashMap<>();
		Map<String, Object> fmslineCommandMap = new HashMap<>();
		
		List reviewList = new ArrayList();
		List fmslineList = new ArrayList();
		
		for(int i = 0; i < reviewRes.size(); i++) {
			String reviewJson = new Gson().toJson(reviewRes.get(i));
			reviewCommandMap = new Gson().fromJson(reviewJson, Map.class);
			reviewList.add(reviewCommandMap);
		}
		
		for(int i = 0; i < fmslineRes.size(); i++) {
			String fmslineJson = new Gson().toJson(fmslineRes.get(i));
			fmslineCommandMap = new Gson().fromJson(fmslineJson, Map.class);
			fmslineList.add(fmslineCommandMap);
		}
		
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("fmslineList", fmslineList);
		request.getRequestDispatcher("/WEB-INF/view/mypage/mywritelist.jsp")
		.forward(request, response);
	}
	
	private void reviewDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		int res = userReviewService.deleteReview(reviewNo);
		if(res > 0) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void fmslineDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fmslineNo = Integer.parseInt(request.getParameter("fmslineNo"));
		
		int res = userReviewService.deleteFmsline(fmslineNo);
		if(res > 0) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void translation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/mypage/translation.jsp")
		.forward(request, response);
	}
	
	private void translationImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);
		
		String text = (String) parsedData.get("text");
		String lan = (String) parsedData.get("lan");
		
		String res = userReviewService.papagoAPI(text, lan);
		
		if(res != null) {
			response.getWriter().print(res);
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void calendar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/mypage/mycalendar.jsp")
		.forward(request, response);
	}
	
	private void calendarList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String date = year + "/" + month;
		System.out.println("year" + year);
		System.out.println("month" + month);
		
		List<UserReview> reviewRes = userReviewService.takeMonthlyReview(user.getUserNo(), date);
		
		
		
		/*
		 * Map<String, Object> reviewCommandMap = new HashMap<>();
		 * 
		 * List reviewList = new ArrayList();
		 * 
		 * for(int i = 0; i < reviewRes.size(); i++) { String reviewJson = new
		 * Gson().toJson(reviewRes.get(i)); reviewCommandMap = new
		 * Gson().fromJson(reviewJson, Map.class); reviewList.add(reviewCommandMap); }
		 */
		
		
		response.getWriter().print(new Gson().toJson(reviewRes));
		
	
		
		
	}
	
	private void myDailyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		
		String watchDate = year + "/" + month + "/" + day;
		List<UserReview> reviewRes = userReviewService.dailyReviewByUserNo(user.getUserNo(), watchDate);
		
		
		Map<String, Object> reviewCommandMap = new HashMap<>();
		
		
		List reviewList = new ArrayList();
		
		
		for(int i = 0; i < reviewRes.size(); i++) {
			String reviewJson = new Gson().toJson(reviewRes.get(i));
			reviewCommandMap = new Gson().fromJson(reviewJson, Map.class);
			reviewList.add(reviewCommandMap);
		}
		
		
		request.setAttribute("reviewList", reviewList);
		request.getRequestDispatcher("/WEB-INF/view/mypage/mydailylist.jsp")
		.forward(request, response);
	
	}
	
	private void writeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mvNo = request.getParameter("mvno");
		Movie detailRes = movieService.selectMovieByMvNo(mvNo);
		request.setAttribute("res", detailRes);
			
		request.getRequestDispatcher("/WEB-INF/view/mypage/writereview.jsp")
		.forward(request, response);
	}
	
	private void insertReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String mvNo = request.getParameter("mvno");
		Movie movie = movieService.selectMovieByMvNo(mvNo);

		double score = Double.parseDouble(request.getParameter("score"));
		String wtchDt = request.getParameter("watchDate");
		String rvContent = request.getParameter("rvContent");
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(wtchDt.substring(0, 4)));
		c.set(Calendar.MONTH, Integer.parseInt(wtchDt.substring(5, 7))-1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(wtchDt.substring(8, 10)));
		long date = c.getTimeInMillis();
		
		java.sql.Date watchDate = new java.sql.Date(date);
		
		UserReview userReview = new UserReview();
		userReview.setUserNo(user.getUserNo());
		userReview.setScore(score);
		userReview.setRvContent(rvContent);
		userReview.setWatchDate(watchDate);
		userReview.setMvNo(movie.getMvNo());
		userReview.setMvTitle(movie.getMvTitle());
		userReview.setThumbnail(movie.getThumbnail());
		
		
		System.out.println(userReview.toString());
		int res = userReviewService.insertReview(userReview);
		if(res > 0) {
			request.setAttribute("alertMsg", "영화 후기가 등록되었습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		} else {
			request.setAttribute("alertMsg", "영화 후기 등록 중 에러가 발생했습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	private void writeLine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mvNo = request.getParameter("mvno");
		Movie detailRes = movieService.selectMovieByMvNo(mvNo);
		request.setAttribute("res", detailRes);
			
		request.getRequestDispatcher("/WEB-INF/view/mypage/writeline.jsp")
		.forward(request, response);
	}
	
	private void insertLine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String mvNo = request.getParameter("mvno");
		Movie movie = movieService.selectMovieByMvNo(mvNo);

		String rvContent = request.getParameter("rvContent");

		
		UserFmsline userFmsline = new UserFmsline();
		userFmsline.setUserNo(user.getUserNo());
		userFmsline.setFmlContent(rvContent);
		userFmsline.setMvNo(movie.getMvNo());
		userFmsline.setMvTitle(movie.getMvTitle());
		userFmsline.setThumbnail(movie.getThumbnail());
		
		
		
		int res = userReviewService.insertLine(userFmsline);
		if(res > 0) {
			request.getSession().removeAttribute("reviewNo");
			request.setAttribute("alertMsg", "영화 후기 등록이 완료되었습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		} else {
			request.setAttribute("alertMsg", "영화 후기 등록중 에러가 났습니다");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	private void myReviewUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = (int) Double.parseDouble(request.getParameter("reviewNo"));
		Map<String, Object> reviewContent = userReviewService.selectReviewByReviewNo(reviewNo);
		UserReview userReview = (UserReview) reviewContent.get("userReview");
		String poster = (String) reviewContent.get("poster");
		
		request.setAttribute("userReview", userReview);
		request.setAttribute("poster", poster);
		request.getSession().setAttribute("reviewNo", userReview.getReviewNo());
		
		request.getRequestDispatcher("/WEB-INF/view/mypage/myreviewupdate.jsp")
		.forward(request, response);
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	private void myReviewUpdateImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double score = Double.parseDouble(request.getParameter("score"));
		String wtchDt = request.getParameter("watchDate");
		String rvContent = request.getParameter("rvContent");
		int reviewNo = (int) request.getSession().getAttribute("reviewNo");
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(wtchDt.substring(0, 4)));
		c.set(Calendar.MONTH, Integer.parseInt(wtchDt.substring(5, 7))-1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(wtchDt.substring(8, 10)));
		long date = c.getTimeInMillis();
		
		java.sql.Date watchDate = new java.sql.Date(date);
		
		UserReview userReview = new UserReview();
		userReview.setScore(score);
		userReview.setWatchDate(watchDate);
		userReview.setRvContent(rvContent);
		userReview.setReviewNo(reviewNo);
		
		int res = userReviewService.updateReview(userReview);
		if(res > 0) {
			request.getSession().removeAttribute("reviewNo");
			request.setAttribute("alertMsg", "영화 후기 수정이 완료되었습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		} else {
			request.setAttribute("alertMsg", "영화 후기 수정 중 에러가 발생했습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	private void myLineUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fmslineNo = (int) Double.parseDouble(request.getParameter("fmslineNo"));
		Map<String, Object> fmslineContent = userReviewService.selectFmslineByFmslineNo(fmslineNo);
		UserFmsline userFmsline = (UserFmsline) fmslineContent.get("userFmsline");
		String poster = (String) fmslineContent.get("poster");
		
		request.setAttribute("userFmsline", userFmsline);
		request.setAttribute("poster", poster);
		request.getSession().setAttribute("fmslineNo", userFmsline.getFmslineNo());
		
		
		request.getRequestDispatcher("/WEB-INF/view/mypage/mylineupdate.jsp")
		.forward(request, response);
	}
	
	/**
	 * 
	 * @Author : MinHee
	 * @Date : 2021. 2. 6.
	 * @work :
	 */
	private void myLineUpdateImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fmlContent = request.getParameter("fmlContent");
		int fmslineNo = (int) request.getSession().getAttribute("fmslineNo");
		
		UserFmsline userFmsline = new UserFmsline();
		userFmsline.setFmlContent(fmlContent);
		userFmsline.setFmslineNo(fmslineNo);
		
		int res = userReviewService.updateFmsline(userFmsline);
		if(res > 0) {
			request.getSession().removeAttribute("fmslineNo");
			request.setAttribute("alertMsg", "나만의 명대사 수정이 완료되었습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		} else {
			request.setAttribute("alertMsg", "나만의 명대사 수정 중 에러가 발생했습니다.");
			request.setAttribute("url", "/mypage/mywritelist.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
		
		request.getRequestDispatcher("/WEB-INF/view/mypage/mylineupdate.jsp")
		.forward(request, response);
	}


}
