<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/comm/noticedetail.css">
	


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
                  <a class="top_user top_join" href="/mypage/mypage.do">마이페이지</a>
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
				<div class="score-view" OnClick="location.href ='/movie/socreview.do'" style="cursor:pointer;">평점순</div>
				<div class="review-view" OnClick="location.href ='/movie/reviewview.do'" style="cursor:pointer;">후기순</div>
				<form class="search-view" action="/movie/searchview.do">
					<input type="search" class="input_navi-search" name="search">
					<button class="btn_navi-search">
						<i class="fas fa-search"></i>
					</button>
				</form>
			</div>
		</nav>
   </div>
   <div class="content">  
   <div class="menu">
   	<br>
   	<a style="font-weight: bold; font-size:big">커뮤니케이션</a><br>
   	<a href="/comm/noticelist.do">공지게시판</a><br>
   	<a href="/comm/write.do">QnA</a>
   	</div>
   	
   	<div class="content2"> 
    <h2 class="tit">공지사항</h2>
    <hr class="a">
    <div class="desc_board">
      <h4 class="tit_board">제목 : ${res.ntTitle }</h4>
      <hr class="b">
      <div class="info" >
          <span>게시글 번호 : ${res.noticeNo} </span>
          <span>등록일 :${res.regDate} </span>
      </div>
     <%--  <div class="info">
      	<c:forEach var="file" items="${data.fileList}">
      		<button type="button" class="btn_down-file"
      			onclick="downloadFile('${file.originFileName}'
      								,'${file.renameFileName}'
      								,'${file.savePath}')"
      		>${file.originFileName}</button><br>
      	</c:forEach>
      </div> --%>
      <hr class="c">
      <div class="text">
      <br>
          ${res.ntContent} 
      </div>
      <div class="btn_section btn_list">
          <button style="color:white" onclick="submitData('list')"><span>목록</span></button>
      </div>
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
				<a href="/aboutus/aboutus.do">ABOUT US</a><br>
				<a href="/고객페이지/"> 고객페이지</a><br>
				<a href="/마이페이지/"> 마이페이지</a><br>
				<a href="/내정보관리/"> 내정보관리</a><br>
			</div>
		</div>
	</footer>
	
	<script type="text/javascript"
		src="${context}/resources/js/movie/movie.js"></script>
	
</body>
</html>
   
   
   