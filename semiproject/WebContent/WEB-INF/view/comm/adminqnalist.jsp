<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/comm/adminqnalist.css">
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
   		<c:if test="${!empty admin }">
   		<a href="/comm/adminqnalist.do" style="color:white">문의사항답변</a>
   		<a href="/comm/adminnotice.do" style="color:white">공지사항작성</a>
   		</c:if>
   	</div>
   	
    <div class="noticelist">
	   <table>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>답변상황</th>
			</tr>
			<c:forEach var="qna" items="${res}" varStatus="status">
				<%-- score 글번호 releaseDate 작성일자 mvTitle 글제목 --%>
				<tr>
					<td>${qna.qnaNo}</td>
					<td style="cursor:pointer;" onclick="location.href='/comm/adminqnacoment.do?qnano=${qna.qnaNo}'" >${qna.comm.qstnTitle}</td>
					<td>${qna.comm.qstnRegDate}</td>
					<c:choose>
						<c:when test="${empty qna.comm.qstnComent}">
							<td>미답변</td>
						</c:when>
						<c:otherwise>
							<td>답변완료</td>
						</c:otherwise>
					</c:choose>
					
				</tr>
			</c:forEach>
			
		</table>
		<div>페이지번호 
		<c:forEach var="i" begin="1" end="${requestScope.end}" step="1" varStatus="status">
		<c:choose>
			
			<c:when test="${page != status.count}">
			<a href="/comm/adminqnalist.do?page=${i}"><c:out value="${i}"/></a>
			</c:when>
			<c:otherwise>
			<a href="/comm/adminqnalist.do?page=${i}"><span style="color:rgb(000,153,255);"><c:out value="${i}"/></span></a>
			</c:otherwise>
			
		</c:choose>
		
		</c:forEach>
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
   
   
   