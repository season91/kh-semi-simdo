<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<head>
<link rel="stylesheet" href="${context}/resources/css/common/reset.css">
<link rel="stylesheet" href="${context}/resources/css/mypage/myqnalist.css">
<link rel="stylesheet" href="${context}/resources/css/all.css">

</head>
<body>
	
	<div class="header-wrapper">
		<header class="header-section">
			 <a class="top-logo-text"><img class="top-logo-img" style="width: 20vh; margin-left: 5%; cursor:pointer;" alt="logo;" src="/resources/image/logo.png"
         		  OnClick="location.href ='/index.do'"></a>
			<c:choose>
				<c:when test="${empty sessionScope.user}">
					<%-- 비로그인 상태 --%>
					<div class="top-right" style="width: 20vh">
						<a class="top_user top_join" href="/user/login.do">로그인</a> <a
							class="top_user" href="/user/join.do">회원가입</a>
					</div>
				</c:when>
				<c:otherwise>
					<%-- 로그인 상태 --%>
					<div class="top-right" style="width: 40vh">
						<a class="top_user top_join" href="/mypage/calendar.do">마이페이지</a> <a
							class="top_user" href="/comm/noticelist.do">커뮤니케이션</a> <a
							class="top_user" href="/user/logout.do">로그아웃</a>
					</div>
				</c:otherwise>
			</c:choose>
		</header>

		<nav class="navi">
			<div class="navi-wrapper">
				<div class="my-mv-calendar navi-menu" style="cursor:pointer;" onclick="location.href='/mypage/calendar.do'">영화 달력</div>
				<div class="my-mv-review navi-menu" style="cursor:pointer;" onclick="location.href='/mypage/mywritelist.do'">영화 후기</div>
				<div class="my-mv navi-menu" style="cursor:pointer;" onclick="location.href='/mypage/mywish.do'">찜목록</div>
				<div class="my-info navi-menu" style="cursor:pointer;" onclick="location.href='/user/infochange.do'">회원 정보 변경</div>
				<div class="my-qna navi-menu" style="cursor:pointer;" onclick="location.href='/mypage/myqnalist.do'">나의 문의 & 요청</div>
			</div>
		</nav>
	</div>
	
	<div class="content">
		<div class="qna_head">나의 문의 요청!</div>
		<p>작성은 <a href="/comm/write.do">커뮤니케이션->QnA(클릭시 이동)</a>에서 작성해주세요^^</p>
		
		<table>
		
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>관리자 답변</th>
			</tr>
			<c:forEach var="qna" items="${res}" varStatus="status">
				<%-- score 글번호 releaseDate 작성일자 mvTitle 글제목 --%>
				<tr>
					<td>${qna.qnaNo}</td>
					<td style="cursor:pointer;" onclick="location.href='/mypage/myqnadetail.do?qstnno=${qna.qnaNo}'" >${qna.comm.qstnTitle}</td>
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
			
			<c:when test="${page!=status.count}">
			<a href="/mypage/myqnalist.do?page=${i}"><c:out value="${i}"/></a>
			</c:when>
			<c:otherwise>
			<a href="/mypage/myqnalist.do?page=${i}"><span style="color:rgb(000,153,255);"><c:out value="${i}"/></span></a>
			</c:otherwise>
			
		</c:choose>
		
		</c:forEach>
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
				<p>주소:대한민국</p>
				<address>TEL:031)111-1212</address>
			</div>
			<div class="bottom_right">
				 <a href="/aboutus.do">ABOUT US</a><br>
              <a href="/comm/noticelist.do"> 고객페이지</a><br>
            <a href="/mypage/calendar.do"> 마이페이지</a><br>
            <a href="/user/infochange.do"> 내정보관리</a><br>

			</div>
		</div>
	</footer>

	<script type="text/javascript" src="${context}/resources/js/mypage/qnalist.js"></script>
</body>
</html>