<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/comm/adminqnacoment.css">
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
	   	<p style="font-weight: bold;">커뮤니케이션</p>
	   	<a href="/comm/noticelist.do">공지게시판</a>
	   	<a href="/comm/write.do">QnA</a>
	   	<%-- 관리자라면 권한메뉴 추가  --%>
	   	<c:choose>
	   		<c:when test="${!empty admin}">
	   		<a style="color:pink; font-weight: bold;" href="/comm/adminqnalist.do">고객문의게시판</a>
	   		<a href="/comm/adminnotice.do">관리자메뉴2(공지사항작성,삭제)</a>
	   		</c:when>
	   	</c:choose>
   	</div>
   	<div class="qna_wrap"> 
			<div class="qna-info">
				<div class="qna_head"><p>고객 문의</p><a style="color:#696765"href="/mypage/myqnalist.do">목록으로</a></div>
				<div class="qna_title">${res.qstnTitle}</div>
				<div class="qna_info">글번호 ${res.qstnNo} / 작성자 : ${res.userNm} / 작성일 : ${res.qstnRegDate}</div>
			</div>
			<div class="qna_write_wrap">
				<div class="qna_write_head">문의 내용</div>
				<div class="qna_write_content">${res.qstnContent}</div>
			</div>
			<div class="admin_answer_wrap">
				<div class="admin_answer_head">관리자 답변</div>
					<form action="/comm/adminqnacomentimpl.do?qstnno=${res.qstnNo}" method="post">
						<textarea cols="60" rows="5" class="admin_answer_write" name="coment"></textarea>
						<button style="color: #DADAD9; font-size:2vh;">답변 등록</button>
					</form>
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
				 <a href="/aboutus.do">ABOUT US</a><br>
            <a href="/mypage/calendar.do"> 고객페이지</a><br>
            <a href="/mypage/calendar.do"> 마이페이지</a><br>
            <a href="/user/infochange.do"> 내정보관리</a><br>
			</div>
		</div>
	</footer>
	
	
	<script type="text/javascript" src="${context}/resources/js/movie/movie.js"></script>
</body>
</html>
   
   
   