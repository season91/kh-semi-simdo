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
import com.kh.simdo.comm.model.vo.Comm;
import com.kh.simdo.movie.model.vo.Movie;
import com.kh.simdo.user.model.vo.User;

/**
 * @author 김백관
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
		
		case "upload.do" :  //파일업로드
			uploadComm(request,response);
			break;
		case "download.do" : download(request,response);
		default:
			break;
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
		
		Comm comm = new Comm();
		comm.setQstnTitle(qstntitle);
		comm.setUserNm(user.getUserNm());
		
		comm.setQstnContent(qstncontent);
		comm.setQstnType(qstntype);
		
		int res = commService.insertComm(comm);
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


