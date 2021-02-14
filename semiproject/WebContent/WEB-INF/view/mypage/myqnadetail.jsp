<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="${context}/resources/css/common/reset.css">
<link rel="stylesheet" href="${context}/resources/css/mypage/myqnadetail.css">
<link rel="stylesheet" href="${context}/resources/css/all.css">
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
				<div class="my-mv-calendar navi-menu" onclick="location.href='/mypage/calendar.do'">영화 달력</div>
				<div class="my-mv-review navi-menu" onclick="location.href='/mypage/mywritelist.do'">영화 후기</div>
				<div class="my-mv navi-menu" onclick="location.href='/mypage/mywish.do'">찜목록</div>
				<div class="my-info navi-menu" onclick="location.href='/user/infochange.do'">회원 정보 변경</div>
				<div class="my-qna navi-menu" onclick="location.href='/mypage/myqnalist.do'">나의 문의 & 요청</div>
			</div>
		</nav>
	</div>
	
	<div class="content">
		<div class="qna_wrap"> 
			<div class="qna-info">
				<div class="qna_head">나의 문의 & 요청</div>
				<div class="qna_title">${res.qstnTitle}</div>
				<div class="qna_info">글번호${res.qstnNo} / 작성일 : ${res.qstnRegDate}</div>
			</div>
			<div class="qna_write_wrap">
				<div class="qna_write_head">문의 내용</div>
				<div class="qna_write_content">${res.qstnContent}</div>
			</div>
			<div class="admin_answer_wrap">
				<div class="admin_answer_head">관리자 답변</div>
				<c:choose>
					<c:when test="${empty res.qstnComent}">
						<div class="admin_answer_write">관리자가 문의를 확인 중 입니다.</div>
					</c:when>
					<c:otherwise>
						<div class="admin_answer_write">${res.qstnComent}</div>
					</c:otherwise>
				</c:choose>
				
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
				<p>주소:대한민국</p>
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