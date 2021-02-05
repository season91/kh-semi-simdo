<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<html>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/comm/comm.css">
	<link rel="stylesheet" href="/resources/css/index/index.css">


</head>
<body style="margin:0">

	  <div class="header-wrapper">
     <header class="header-section">
         <a class="top-logo-text"><img class="top-logo-img" style="width: 20vh; margin-left: 5%" alt="logo" src="/resources/image/logo.png"></a>
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

      <nav class="navi">
	      <div class="navi-wrapper">
	      	<div class="nation-view">나라별</div>
	        <div class="year-view">장르별</div>
	        <div class="rank-view"><a>심도순</a></div>
	        <div class="new-view"><a>평점순</a></div>
	        <div class="search-view">
					<input type="search" class="input_navi-search" name="search">
	        		<button class="btn_navi-search"><i class="fas fa-search"></i></button>
	        
		</div>
		</div>
      </nav>
   </div>
   <div class="comm-all">
    <div class="comm-title">
    <center>
      <p class="p1">커뮤니케이션</p>
      <p class="p2">공지게시판</p>  
      <p class="p3">문의&요청</p>
     </center>
   
   </div>
   
   
   <div class="comm-content">
   <table>
    	<tr>
   		
   
   		</tr>
   		<tr>
   			<th style=text-align:center;>공지제목이 출력됩니다.</th>
   			
   			
   		</tr>
   
   		<tr>
   			
   			<td><textarea rows="15" cols="60" name="content"></textarea></td>
   		</tr>
   		
   		
   </table>
   <br>
   <input type="submit" value="목록">
   </div>
  
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
				<a href="/aboutus/">ABOUT US</a><br>
				<a href="/고객페이지/"> 고객페이지</a><br>
				<a href="/마이페이지/"> 마이페이지</a><br>
				<a href="/내정보관리/"> 내정보관리</a><br>
			</div>
		</div>
	</footer>
	
	
</body>
</html>
   
   
   