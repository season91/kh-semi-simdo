<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/user/join.css">
	
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script type="text/javascript">
		Kakao.init('b140a5f5d7731e0c477c0a030751f776');
		console.log(Kakao.isInitialized());
	</script>
</head>
<body>
	<header class="header-section">
		<a class="top-logo-text" href="/index.do"><img style="width: 20vh; margin-left: 5%" alt="logo" src="/resources/image/logo.png"></a>
		<c:choose>
			<c:when test="${empty sessionScope.user}">
				<%-- 비로그인 상태 --%>
				<div class="top-right" style="width: 20vh">
					<a class="top_user top_join" href="/user/login.do">로그인</a>
					<a class="top_user" href="/user/join.do">회원가입</a>
				</div>
			</c:when>
			<c:otherwise>
				<%-- 로그인 상태 --%>
				<div class="top-right" style="width: 40vh">
					<a class="top_user top_join" href="/mypage/mypage.do">마이페이지</a>
					<a class="top_user" href="/comm/noticelist.do">커뮤니케이션</a>
					<a class="top_user" href="/user/logout.do">로그아웃</a>
				</div>
			</c:otherwise>
		</c:choose>
	</header>
	
	<section class="content">
		<div class="top_content underline"><img style="width: 15vh;" alt="logo" src="/resources/image/logo.png"></div>
		<div class="btn-wrapper underline">
			<%
				String clientId = "YpRlcj6rjiKHlB6bWRCM";
				String redirectURI = URLEncoder.encode("http://localhost:9090/user/naverlogin.do", "UTF-8");
				SecureRandom random = new SecureRandom();
				String state = new BigInteger(130, random).toString();
				String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
				apiURL += "&client_id=" + clientId;
				apiURL += "&redirect_uri=" + redirectURI;
				apiURL += "&state=" + state;
				session.setAttribute("state", state);
			%>
			<a class="btn_naver-login" href="<%=apiURL%>">
				<img width="100%" height="50" src="https://static.nid.naver.com/oauth/big_g.PNG?version=js-2.0.0"/>
			</a>
			<a class="btn_kakao-login" href="javascript:loginWithKakao()">
			  <img
			    src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
			    width="100%" height="50"
			  />
			</a>
		</div>
		
		<form class="frm_join" action="${context}/user/joinimpl.do" method="post">
			<div class="join-info underline">
				<label class="label_join">이메일</label>
				<div class="email-wrapper">
					<input class="input_join input_width" id="userEmail" type="email" name="userEmail" placeholder="Enter email address" required="required">
					<button class="btn_emailCheck" type="button" onclick="emailCheck()">중복 확인</button>
				</div>
			</div>
			<div class="join-info underline">
				<label class="label_join">비밀번호</label>
				<input class="input_join" id="userPw" type="password" name="userPw" placeholder="Enter password	숫자, 영문자, 특수문자 조합의 8글자 이상" required="required">
			</div>
			<div class="join-info underline">
				<label class="label_join">비밀번호 확인</label>
				<div class="pwCheck-wrapper">
					<input class="input_join input_width" id="userPwCheck" type="password" name="userPwCheck" placeholder="Confirm password" required="required">
					<button class="btn_pwCheck" type="button" onclick="pwCheck()">확인</button>
				</div>
			</div>
			<div class="join-info underline">
				<label class="label_join">휴대전화</label>
				<input class="input_join" id="userTel" type="tel" name="userTel" placeholder="Phone number" required="required">
			</div>
			<div class="join-info underline">
				<button class="btn_certify" type="button" onclick='mySubmit()'>인증 이메일 발송</button>
				<div class="div_certify">
					<input class="input_join input_certify" id="authNum" type="text" placeholder="인증 번호 입력">
					<button class="btn_confirm" type="button" onclick="authNumCheck()">확인</button>
				</div>
			</div>
			<div class="join-info underline">
				<div class="join-checkbox check-more">
					<div>
						<input class="agreement necess" type="checkbox" id="user-agreement" value="agree">
						<label for="user-agreement">사용자 이용 약관(필수)</label>
					</div>
					<i class="far fa-plus-square user-read-more read-more"></i>
				</div>
				<div class="join-checkbox check-more">
					<div>
						<input class="agreement necess" type="checkbox" id="user-age-check" value="agree">
						<label for="user-age-check">만 14세 이상 확인(필수)</label>
					</div>
					<i class="far fa-plus-square age-read-more read-more"></i>
				</div>
				<div class="join-checkbox">
					<input class="agreement" type="checkbox" id="membership-agreement" value="agree">
					<label for="membership-agreement">평생회원제 동의(선택)</label>
				</div>
			</div>
			<div>
				<input type="checkbox" id="all-agreement" value="agree">
				<label for="all-agreement">사용자 약관 전체 동의</label>
			</div>
			<button class="register" type="submit">REGISTER</button>
		</form>
	</section>
	
	<footer class="bottom">
		<div class="bottom_main">
			<h2>SIMDO:WM</h2>
		</div>
		<div class="bottom_content">
			<div class="bottom_left">
				<p>상호 주식회사 심도</p>
				<p>사업자 등록번호:123-45-67890</p>
				<p>주소:대한민국 </p>
				<address>TEL:031)111-1212</address>
			</div>
			<div class="bottom_right">
				<a href="/aboutus/">ABOUT US</a><br>
				<a href="/고객페이지/"> 고객페이지</a><br>
				<a href="/마이페이지/"> 마이페이지</a><br>
				<a href="/내정보관리/"> 내정보관리</a><br>
			</div>
		</div>
	</footer>
	
	<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script type="text/javascript" src="/resources/js/user/login.js"></script>
	<script type="text/javascript" src="/resources/js/user/join.js"></script>
</body>
</html>