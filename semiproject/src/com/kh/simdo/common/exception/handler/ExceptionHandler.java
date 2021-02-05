package com.kh.simdo.common.exception.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.simdo.common.exception.CustomException;

/**
 * Servlet implementation class ExceptionHandler
 */
@WebServlet("/exception")
public class ExceptionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExceptionHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pw = request.getParameter("pw");
		System.out.println("ExceptionHanler에서 찍음 : "+ pw);
		
		// 넘어온 예외를 꺼낸다
		// request처리 도중 발생한 예외들은 resquest에 
		// javax.servlet.error.exception 이라는속성명으로 담겨온다
		// 즉 gerAttribute() 메서드를 사용해 꺼낼 수 있다.
		
		CustomException e = (CustomException) request.getAttribute("javax.servlet.error.exception");
		
		request.setAttribute("alertMsg", e.error.errMsg());
		request.setAttribute("url", e.error.url());
		request.getRequestDispatcher("/WEB-INF/view/common/result.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
