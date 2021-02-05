package com.kh.simdo.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kh.simdo.common.util.http.HttpUtils;
import com.kh.simdo.user.model.service.UserService;
import com.kh.simdo.user.model.vo.User;

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 3.
 * @work : 로그인, 카카오 계정으로 로그인, 네이버 아이디로 로그인, 회원가입, 이메일 인증,
 * 			사용자 이용 약관, 만 14세 이상 확인, 회원 정보 변경, 비밀번호 찾기, 로그아웃, 회원 탈퇴
 */

/**
 * 
 * @Author : MinHee
 * @Date : 2021. 2. 5.
 * @work : infoChange 메서드 작성, mywritelist jsp와 css 수정, infochange 회원 탈퇴 기능 오류 수정,
 * 			login.jsp 의 css 수정, join.jsp 상단 SIMDO:wm 로고로 수정, join.jsp css 수정,
 * 			/index.do 경로 수정
 */

@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		
		switch(uriArr[uriArr.length - 1]) {
		case "login.do" :
			login(request, response);
			break;
		case "loginimpl.do" :
			loginImpl(request, response);
			break;
		case "kakaologinimpl.do" :
			kakaoLoginImpl(request, response);
			break;
		case "naverlogin.do" :
			naverLogin(request, response);
			break;
		case "naverloginimpl.do" :
			naverLoginImpl(request, response);
			break;
		case "join.do" : 
			join(request, response);
			break;
		case "emailcheck.do" : 
			emailCheck(request, response);
			break;
		case "mailauth.do" : 
			authenticateEmail(request, response);
			break;
		case "authnumcheck.do" : 
			authNumCheck(request, response);
			break;
		case "useragreement.do" : 
			userAgreement(request, response);
			break;
		case "ageagreement.do" : 
			ageAgreement(request, response);
			break;
		case "joinimpl.do" : 
			joinImpl(request, response);
			break;
		case "infochange.do" : 
			infoChange(request, response);
			break;
		case "infochangeimpl.do" : 
			infoChangeImpl(request, response);
			break;
		case "pwsearch.do" : 
			pwSearch(request, response);
			break;
		case "pwsearchImpl.do" : 
			findUserPwEmail(request, response);
			break;
		case "logout.do" :
			logout(request, response);
			break;
		case "quit.do" :
			quit(request, response);
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
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request
		.getRequestDispatcher("/WEB-INF/view/user/login.jsp")
		.forward(request, response);
	}
	
	private void loginImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		String userPw = request.getParameter("userPw");
		
		User user = userService.userAuthenticate(userEmail, userPw);
		
		if(user != null) {
			request.getSession().setAttribute("user", user);
			request.setAttribute("alertMsg", "로그인에 성공했습니다.");
			request.setAttribute("url", "/index.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		//로그인 실패
		}else {
			request.setAttribute("alertMsg", "이메일 계정과 비밀번호를 다시 확인해주세요.");
			request.setAttribute("url", "/user/login.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	private void kakaoLoginImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);
		
		String userEmail = (String) parsedData.get("userEmail");
		String userAccessToken = (String) parsedData.get("userAccessToken");
		
		User user = new User(); 
		user.setUserEmail(userEmail);
		
		request.getSession().setAttribute("accessToken", userAccessToken);
		
		if(userService.selectUserByEmail(userEmail) == null) {
			userService.insertKakaoUser(user);
		}
		
		User getUser = userService.selectUserByEmail(userEmail);
		request.getSession().setAttribute("user", getUser);
		
		if(userService.selectUserByEmail(userEmail) != null) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
		
	}
	
	private void naverLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientId = "YpRlcj6rjiKHlB6bWRCM";
		String clientSecret = "wCEOnVZhpS";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String redirectURI = URLEncoder.encode("http://localhost:9090/user/naverlogin.do", "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode==200) { // 정상 호출
			  	br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
			  	br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if(responseCode==200) {
				Gson gson = new Gson();
				Map parsedData = gson.fromJson(res.toString(), Map.class);
				String access_token = (String) parsedData.get("access_token");
				request.setAttribute("access_token", access_token);
				request.getRequestDispatcher("/user/naverloginimpl.do")
				.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void naverLoginImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = (String) request.getAttribute("access_token");
		String header = "Bearer " + token;
		
		String url = "https://openapi.naver.com/v1/nid/me";
		
		HttpUtils http = new HttpUtils();
		
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", header);
		
		String jsonData = http.get(url, headers);
		JsonObject jsonObject = new Gson().fromJson(jsonData, JsonObject.class);
		String userEmail = jsonObject.get("response").getAsJsonObject().get("email").getAsString();
		
		User user = new User(); 
		user.setUserEmail(userEmail);
		
		request.getSession().setAttribute("access_token", token);
		
		if(userService.selectUserByEmail(userEmail) == null) {
			userService.insertNaverUser(user);
		}
		
		User getUser = userService.selectUserByEmail(userEmail);
		request.getSession().setAttribute("user", getUser);
		
		if(userService.selectUserByEmail(userEmail) != null) {
			request.setAttribute("alertMsg", "네이버 아이디로 로그인에 성공했습니다.");
			request.setAttribute("url", "/index.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "네이버 아이디로 로그인에 실패했습니다.");
			request.setAttribute("url", "/user/login.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	private void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/user/join.jsp")
		.forward(request, response);
	}
	
	private void emailCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		User user = userService.selectUserByEmail(userEmail);
		
		if(user == null) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void authenticateEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		
		User user = new User();
		user.setUserEmail(userEmail);
		
		userService.authenticateEmail(request, user);
	}
	
	private void pwSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/user/pw_search.jsp")
		.forward(request, response);
	}
	
	private void findUserPwEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		User user = userService.selectUserByEmail(userEmail);
		
		if(user != null) {
			userService.findUserPwEmail(user);
			request.setAttribute("alertMsg", "비밀번호가 이메일로 전송되었습니다. 이메일을 확인해주세요.");
			request.setAttribute("url", "/user/login.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "Simdo:wm에 가입되어 있지 않은 이메일 계정입니다.");
			request.setAttribute("url", "/user/pwsearch.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
		
	}
	
	private void authNumCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authNum = request.getParameter("authNum");
		
		if(authNum.equals(request.getSession().getAttribute("authNum"))) {
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void userAgreement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/user/user_agreement.jsp")
		.forward(request, response);
	}
	
	private void ageAgreement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/user/user_age_agreement.jsp")
		.forward(request, response);
	}
	
	private void joinImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		String userPw = request.getParameter("userPw");
		String userTel = request.getParameter("userTel");
		
		User user = new User();
		user.setUserEmail(userEmail);
		user.setUserPw(userPw);
		user.setUserTel(userTel);
		
		int res = userService.insertUser(user);
		if(res > 0) {
			request.setAttribute("alertMsg", "회원가입을 축하드립니다.");
			request.setAttribute("url", "/user/login.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	private void infoChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/user/infochange.jsp")
		.forward(request, response);
	}
	
	private void infoChangeImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userProfile = request.getParameter("userProfile");
		String userNm = request.getParameter("userNm");
		String userPw = request.getParameter("userPw");
		String userTel = request.getParameter("userTel");
		String userGender = request.getParameter("userGender");
		String bDay = request.getParameter("userBirth");
		java.sql.Date userBirth = null;
		
		if(bDay.length() > 0) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, Integer.parseInt(bDay.substring(0, 4)));
			c.set(Calendar.MONTH, Integer.parseInt(bDay.substring(5, 7))-1);
			c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(bDay.substring(8, 10)));
			long date = c.getTimeInMillis();
			
			userBirth = new java.sql.Date(date);
		}
		
		User userBefore = (User) request.getSession().getAttribute("user");
		User userAfter = new User();
		userAfter.setUserProfile(userProfile);
		userAfter.setUserNm(userNm);
		userAfter.setUserPw(userPw);
		userAfter.setUserTel(userTel);
		userAfter.setUserGender(userGender);
		userAfter.setUserBirth(userBirth);
		
		int res = userService.updateUser(userBefore, userAfter);
		if(res > 0) {
			User user = userService.selectUserByEmail(userBefore.getUserEmail());
			request.getSession().setAttribute("user", user);
			
			request.setAttribute("alertMsg", "회원정보가 변경되었습니다.");
			request.setAttribute("url", "/user/infochange.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}else {
			request.setAttribute("alertMsg", "에러 발생");
			request.setAttribute("url", "/user/infochange.do");
			
			request
			.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
			.forward(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		request.setAttribute("alertMsg", "로그아웃 되었습니다.");
		request.setAttribute("url", "/index.do");
		
		request
		.getRequestDispatcher("/WEB-INF/view/common/result.jsp")
		.forward(request, response);
	}
	
	private void quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		
		int res = userService.deleteUser(userEmail);
		if(res > 0) {
			request.getSession().removeAttribute("user");
		}
	}

}
