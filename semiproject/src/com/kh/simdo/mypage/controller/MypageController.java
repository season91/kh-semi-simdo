package com.kh.simdo.mypage.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.simdo.common.util.encoding.EncodingUtil;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.mypage.model.service.QnaListService;
import com.kh.simdo.mypage.model.service.UserReviewService;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;
import com.kh.simdo.user.model.vo.User;

/**
 * @author 김종환, 조민희(후기,명대사리스트), 조아영(QnA),김백관(메인)
 */
/**
 * Servlet implementation class MypageController
 */
@WebServlet("/mypage/*")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserReviewService userReviewService = new UserReviewService();
    QnaListService qnalistService = new QnaListService();
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
		case "mydailylist.do":  // 김종환
			myDailyList(request,response);
			break;
		case "writereview.do" : // 김종환
			writeReview(request, response);
			break;
		case "writeline.do" :  // 김종환
			writeLine(request, response);
			break;
		case "myqnalist.do" :  // 조아영
			myQnaList(request,response);
			break;
		case "myqnadetail.do" :
			myQnaDetail(request,response); 
			break;
		}
	}
	
	/**
	 * @author 조아영
	 * 
	 */
	
	//여기 movie는 아직 문의요청 더미가 없어서 테스트용으로 사용한 vo입니다. 더미 들어오면 수정예정.
	 protected List parseJson(List<Movie> res) {
			List list = new ArrayList();
			Map<String, Object> commandMap = new HashMap<String, Object>();
			for (int i = 0; i < res.size(); i++) {
				String json = new Gson().toJson(res.get(i));
				commandMap = new Gson().fromJson(json, Map.class);
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
		List movieList = new ArrayList();
		int page = 0;
		if(text == null) {
			page++;
		} else {
			page = Integer.parseInt(text);
		}
		
		int[] res = qnalistService.selectPagingList(page);
		request.setAttribute("start", res[0]);
		request.setAttribute("end", res[1]);

		System.out.println(page);
		List<Movie> pageRes = qnalistService.selectQnaList(page);
		movieList = parseJson(pageRes);
		
		request.setAttribute("res", movieList);
		request.setAttribute("page", page);
		
		//score 글번호 releaseDate 작성일자 mvTitle 글제목
		
		request.getRequestDispatcher("/WEB-INF/view/mypage/myqnalist.jsp").forward(request, response);	
	}
	
	/**
	 * @author 조아영
	 * 
	 */
	protected void myQnaDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
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
	
	private void myDailyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/mypage/mydailylist.jsp")
		.forward(request, response);
	}
	
	private void writeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/mypage/writereview.jsp")
		.forward(request, response);
	}
	
	private void writeLine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/mypage/writeline.jsp")
		.forward(request, response);
	}


}
