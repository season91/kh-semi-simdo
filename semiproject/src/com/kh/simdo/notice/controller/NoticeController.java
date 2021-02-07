package com.kh.simdo.notice.controller;

import java.io.IOException;
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
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.notice.model.service.NoticeService;
import com.kh.simdo.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NoticeService noticeService = new NoticeService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		switch(uriArr[uriArr.length - 1]) {
		
		case "noticelist.do" :  // 공지사항 리스트
			noticeList(request,response);
			break;
		case "noticedatil.do" : // 공지사항 상세보기
			noticedetail(request,response); 
			break;
		default:
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
	//공지사항 자세히보기
	private void noticedetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ntTitle = request.getParameter("ntTitle");
		request.getRequestDispatcher("/WEB-INF/view/notice/noticeview.jsp").forward(request, response);
	}
	//parseNOtice
	 protected List parseJson(List<Notice> res) {
			List list = new ArrayList();
			Map<String, Object> commandMap = new HashMap<String, Object>();
			for (int i = 0; i < res.size(); i++) {
				String json = new Gson().toJson(res.get(i));
				commandMap = new Gson().fromJson(json, Map.class);
				list.add(commandMap);
			}
			return list;
	}
	 //공지사항 리스트
protected void noticeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 페이징표현해주는 메서드
			String text = request.getParameter("page");
			List noticeList = new ArrayList();
			int page = 0;
			if(text == null) {
				page++;
			} else {
				page = Integer.parseInt(text);
			}
			
			int[] res = noticeService.selectPagingList(page);
			request.setAttribute("start", res[0]);
			request.setAttribute("end", res[1]);

			System.out.println(page);
			List<Notice> pageRes = noticeService.selectNoticeList(page);
			noticeList = parseJson(pageRes);
			
			request.setAttribute("res", noticeList);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/view/notice/noticelist.jsp")
			.forward(request, response);
	}
}
