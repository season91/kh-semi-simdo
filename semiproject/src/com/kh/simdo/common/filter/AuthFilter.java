package com.kh.simdo.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.user.model.vo.User;

/**
 * Servlet Filter implementation class AuthFilter
 */
//권한 관리
public class AuthFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	// 필터만 잘 만든다면 전체적인 개발효율이 좋아진다. null 포인터가 확 줄어서!
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 로그인이 안된 사용자일 경우 회원정보페이지에 접근할 수 없게 막아주기
		// 로그인이 안된 사용자 == session에 user속성값이 없는 사용
		// request받아서 걸러주면 됨

		// HttpServletRequest에 session이 있기때문에 다운캐스팅부터
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		// 사용자가 접근한 url 경로 확인
		String[] uriArr = httpRequest.getRequestURI().split("/");
		// uri 몇뎁스인지에 따라 달라짐,, 주로 3뎁스?
		// 사용자가 로그인 상태가 아닐 경우 권한 관리.

		// 사용자가 로그인 상태가 아닐경우 권한 관리
		if (uriArr.length > 0) {
			// 비로그인 상태일 때 권한 관리

			switch (uriArr[1]) {
			case "mypage":
				if (session.getAttribute("user") == null) {
					throw new ToAlertException(ErrorCode.AUTH01);
				}
				break;
			case "user":
				switch (uriArr[2]) {
				case "infochange.do":
					if (session.getAttribute("user") == null) {
						throw new ToAlertException(ErrorCode.AUTH01);
					}
					break;
				}

				break;
			case "movie":
				if(session.getAttribute("user") == null) {
					throw new ToAlertException(ErrorCode.AUTH01);
				} else {
					switch (uriArr[2]) {
					case "db.do":
						User user = (User) session.getAttribute("user");
						if (user.getAdmin() == null) {
							throw new ToAlertException(ErrorCode.AUTH04);
						}
						break;
					}
				}
				break;
			case "comm" :
				if(session.getAttribute("user") == null) {
					throw new ToAlertException(ErrorCode.AUTH01);
				} else {
					User user = (User) session.getAttribute("user");
					switch (uriArr[2]) {
					// 공지사항 작성 권한
					case "adminnotice.do":
						if (user.getAdmin() == null) {
							throw new ToAlertException(ErrorCode.AUTH04);
						}
						break;
					// 유저 문의하기 답변할 권한
					case "adminqnalist.do":
						if (user.getAdmin() == null) {
							throw new ToAlertException(ErrorCode.AUTH04);
						}
						break;
						
					case "adminqnacoment.do":
						if (user.getAdmin() == null) {
							throw new ToAlertException(ErrorCode.AUTH04);
						}
						break;
					}
				}
				
				break;
			}
			
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
