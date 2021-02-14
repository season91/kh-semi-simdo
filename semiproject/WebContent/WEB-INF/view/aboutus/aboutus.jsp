<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<html>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/index/index.css">
	<link rel="stylesheet" href="/resources/css/index/aboutus.css">
	<style>
	
	</style>

</head>
<body style="margin:0">

	  <div class="header-wrapper">
     <header class="header-section">
    <a class="top-logo-text"><img class="top-logo-img" style="width: 20vh; margin-left: 5%; cursor:pointer;" alt="logo;" src="/resources/image/logo.png"
	  OnClick="location.href ='/index.do'"></a>
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
                  <a class="top_user top_join" href="/mypage/calendar.do">마이페이지</a>
                  <a class="top_user" href="/comm/noticelist.do">커뮤니케이션</a>
                  <a class="top_user" href="/user/logout.do">로그아웃</a>
               </div>
            </c:otherwise>
         </c:choose>
      </header>

      <nav class="navi">
	      <div class="navi-wrapper">
	      	<div class="nation-view" style="cursor:pointer;">나라별</div>
	        <div class="genre-view" style="cursor:pointer;">장르별</div>
	        <div class="score-view" OnClick="location.href ='/movie/naviview.do'" style="cursor:pointer;">평점순</div>
	        <div class="review-view" OnClick="location.href ='/movie/reviewview.do'" style="cursor:pointer;">후기순</div>
	        <div class="search-view">
					<input type="search" class="input_navi-search" name="search">
	        		<button class="btn_navi-search"><i class="fas fa-search"></i></button>
			</div>
		</div>
      </nav>
   </div>
   		
   		<div class="aboutus">
   		<a class="top-logo-text"><img class="top-logo-img" style="width: 50vh; margin-right: 5%" alt="logo" src="/resources/image/aboutus.jpg"></a>
   		
   		
   		<h2>제작의도</h2>
   		
   		<p class="p1">
   	     배우고 싶은 언어 또는 관심있는 나라에 따라 영화를 보고 명대사를 직접 공부해보는 사이트<br>
   	     국가별로 생활언어에 유용한 작품을 추천하여 영화를 보며 언어능력을 향상시키고픈 사용자들에게 유용한 정보를 제공한다.<br>
   	     국가별,장르별로 관심이 있는 작품을 본 뒤 생각나는 명대사를 직접 영어로 작문해보고 번역기능을 이용할 수 있다.!<br>	
   		</p>
   		
   		<h2>사용 기술</h2>
   		
   		<p class="p2">사용언어 : JAVA,JAVASCRIPT,ORACLE,HTML,CSS,JSP,SERVELT<br>
   		사용한 api :추천기능 오픈소스, 공공데이터 영화DB, 네이버와 카카오 로그인, 캘린더, 게시판에디터  <br>
   		</p>
   		
   		<h2>개발자</h2>
   		<p class="p3">조민희,조아영,김종환,김백관</p>
   		</div>
  
		
	
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
				 <a href="/aboutus.do">ABOUT US</a><br>
            <a href="/mypage/calendar.do"> 고객페이지</a><br>
            <a href="/mypage/calendar.do"> 마이페이지</a><br>
            <a href="/user/infochange.do"> 내정보관리</a><br>
			</div>
		</div>
	</footer>
	

</body>
</html>