package com.kh.simdo.communication.controller;

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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.simdo.communication.model.service.CommunicationService;
import com.kh.simdo.communication.model.vo.Communication;
import com.kh.simdo.communication.model.vo.Notice;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.user.model.vo.User;

/**
 * @author 김백관
 */
@WebServlet("/comm/*")
public class CommunicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommunicationService communicationService = new CommunicationService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunicationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		switch(uriArr[uriArr.length - 1]) {
		
		case "write.do" :   //q&a쓰기  김백관
			commWrite(request, response);
			break;
		
		case "upload.do" :  //QnA 게시글업로드 김백관 
			uploadComm(request,response);
			break;
		case "download.do" : download(request,response); break; //김백관
		case "noticelist.do": noticeList(request,response); break; //조아영
		case "noticedetail.do" : noticeDetail(request,response); break; //조아영
		default:
			break;
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param res
	   @return
	   @work : jsp로 보내기 위해 list->json 파싱
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
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param request
	   @param response
	   @throws ServletException
	   @throws IOException
	   @work : 공지사항 페이징처리 및 리스트 불러오기
	 */
	protected void noticeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 목록 리스트
		
		// 페이징 표현 해주는 메서드
		String text = request.getParameter("page");
		List noticeList = new ArrayList();
		int page = 0;
		if(text == null) {
			page++;
		} else {
			page = Integer.parseInt(text);
		}
		

		int[] res = communicationService.selectPagingByNotice(page);
		request.setAttribute("start", res[0]);
		request.setAttribute("end", res[1]);
		
		List<Map<String, Object>> pageRes = communicationService.selectNoticeList(page);
		
		noticeList = parseJson(pageRes);
		request.setAttribute("res", noticeList);
		request.setAttribute("page", page);
		//score 글번호 releaseDate 작성일자 mvTitle 글제목
		request.getRequestDispatcher("/WEB-INF/view/communication/noticelist.jsp").forward(request, response);
	}

	/**
	 * 
	 * @Author : 조아영
	   @Date : 2021. 2. 8.
	   @param request
	   @param response
	   @throws ServletException
	   @throws IOException
	   @work : 페이징된 공지사항 제목 눌렀을때 상세내용 가져오는 메서드. 
	 */
	protected void noticeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아영, 게시글 번호 기준으로 notice테이블 정보 가져온다.
		String strNoticeNo = String.valueOf(request.getParameter("noticeno"));
		int noticeNo = Integer.parseInt(strNoticeNo);
		Notice notice = communicationService.selectNoticeByNoticeNo(noticeNo);
		
		// 아영, notice 객체 jsp로 전달
		request.setAttribute("res", notice);
		request.getRequestDispatcher("/WEB-INF/view/comm/noticedetail.jsp").forward(request, response);
	}

	
	private void commWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/view/comm/commwrite.jsp")
	.forward(request, response);

	}

	
	private void uploadComm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user= (User)request.getSession().getAttribute("user");
		
		String qstntitle = request.getParameter("qstntitle");
		String qstncontent = request.getParameter("qstncontent");
		String qstntype = request.getParameter("qstntype");
		
		Communication communication = new Communication();
		communication.setQstnTitle(qstntitle);
		communication.setUserNm(user.getUserNm());
		
		communication.setQstnContent(qstncontent);
		communication.setQstnType(qstntype);
		
		int res = communicationService.insertComm(communication);
		if(res > 0) {
			request
			.getRequestDispatcher("/WEB-INF/view/comm/commlist.jsp")
			.forward(request, response);
		}else {
			System.out.println("확인");
		}
	}

	private void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String originFileName = request.getParameter("ofname");
	String renameFileName = request.getParameter("rfname");
	String savePath = request.getParameter("savePath");
	response.setHeader("content-disposition", "attachment; filename="+originFileName);
	request.getRequestDispatcher("/file"+savePath+renameFileName)
	.forward(request, response);
}
}


