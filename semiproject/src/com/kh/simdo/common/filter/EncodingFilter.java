package com.kh.simdo.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter("/EncodingFilter")
public class EncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodingFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here 나머진 건들필요없구 두필터 이부분만 작성하면 됨
		request.setCharacterEncoding("utf-8");
		//서블릿리스폰스 객체는 셋헤더가 없기때문에 컨텐트차입을 지정할수 있게 해주는 setContentTytpe메서드 사용
		// 이거때문에 css가 안먹었던것임 응답할때 css파일인데 이거 html이야 하고 보냈기 때문임	
		// 이거 지우면 작동함 왜냐면 jsp파일 상단에 UTF-8 이 들어가있기 때문임!
		response.setContentType("text/html; charset=utf-8");
		// String ipAddress = request.getRemoteAddr();
		
		// 같은 url-pattern으로 여러개 filter를 지정할 수 있다.
		// 해당 filter들은 filter-chain형태로 관리되며, web.xml에 등록한 순서대로 실행된다.
		// 필터체인을 탐색해 다음 필터가 있다면 다음 필터의 diFilter메서드를 호출
		// 다음 필터가 없다면 servlet을 호출한다.
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
