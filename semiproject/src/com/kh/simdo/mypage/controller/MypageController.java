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
import com.kh.simdo.mypage.model.service.UserReviewService;
import com.kh.simdo.mypage.model.vo.UserFmsline;
import com.kh.simdo.mypage.model.vo.UserReview;
import com.kh.simdo.user.model.vo.User;

/**
 * Servlet implementation class MypageController
 */
@WebServlet("/mypage/*")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserReviewService userReviewService = new UserReviewService();
       
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
		case "mywritelist.do" :
			myWriteList(request, response);
			break;
		case "reviewdelete.do" :
			reviewDelete(request, response);
			break;
		case "fmslinedelete.do" :
			fmslineDelete(request, response);
			break;
		case "translation.do" : 
			translation(request, response);
			break;
		case "translationimpl.do" : 
			translationImpl(request, response);
			break;
		case "calendar.do": 
			calendar(request,response);
			break;
		case "mydailylist.do": 
			myDailyList(request,response);
			break;
		case "writereview.do" :
			writeReview(request, response);
			break;
		case "writeline.do" :
			writeLine(request, response);
			break;

		}
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
