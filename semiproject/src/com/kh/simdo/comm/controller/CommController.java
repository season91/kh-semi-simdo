package com.kh.simdo.comm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.simdo.comm.model.service.CommService;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.user.model.vo.User;

/**
 * Servlet implementation class CommController
 */
@WebServlet("/comm/*")
public class CommController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CommService commService = new CommService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		switch(uriArr[uriArr.length - 1]) {
		
		case "write.do" :   //q&a쓰기 
			commWrite(request, response);
			break;
		case "list.do" :  // 공지사항 리스트
			commList(request,response);
			break;
		case "view.do" : // 공지사항 상세보기
			commview(request,response); 
			break;
		case "upload.do" :  //파일업로드
			uploadComm(request,response);
			break;
		default:
			break;
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void commList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/comm/commlist.jsp")
		.forward(request, response);
	}
	
	private void commWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/view/comm/commwrite.jsp")
	.forward(request, response);

	}

	private void commview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String qstn_no = request.getParameter("qstn_no");
		Map<String,Object> commandMap = commService.selectBoardDetail(qstn_no);
		request.setAttribute("data", commandMap);
		request.getRequestDispatcher("/WEB-INF/view/comm/commview.jsp")
	.forward(request, response);
}
	private void uploadComm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//제목, 본문, 작성자(session), 첨부파일
				//파일테이블 : 원본파일명, 리네임파일명, 게시글번호, 저장경로 
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user_nm");

				commService.insertComm(user.getUserNm(), request);
				
				request.setAttribute("alertMsg", "게시글 등록이 완료되었습니다.");
				request.setAttribute("url", "/index.do");
		request.getRequestDispatcher("/WEB-INF/view/comm/commview.jsp")
		.forward(request, response);
	}
}

