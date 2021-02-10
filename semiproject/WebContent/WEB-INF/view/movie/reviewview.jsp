<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp"%>
<head>
<link rel="stylesheet" href="${context}/resources/css/common/reset.css">
<link rel="stylesheet" href="${context}/resources/css/movie/review.css">
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
				<div class="score-view" OnClick="location.href ='/movie/scoreview.do'" style="cursor:pointer;">평점순</div>
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
		<div class="mv_view_title">심도 후기별 조회 결과입니다.</div>
		<div class="mv_wrapper">
			<%-- 여기서부터검색결과가 출력되는 것 --%>
			<c:forEach var="movie" items="${res}">
			<div class="mv_list_wrap">
				<div class="mv_list_left-wrap">
					<div class="mv_list_top-wrap">
						<div class="mv_title">${movie.movie.mvTitle}</div>
						<a class="mv_btn-more" href="/movie/detailview.do?mvno=${movie.movie.mvNo}">MORE</a>
					</div>
					<div class="mv_list_middle-wrap">
						<span>줄거리</span>
						<div class="mv_plot">${movie.movie.plot}</div>
					</div>
					<div class="mv_list_bottom-wrap">
					<%-- 후기결과도 여기에 더붙는다. --%>
					<c:forEach var="review" items="${movie.reviews}">
						<div class="mv_reviewlist_wrap">
							<span>심도들의 후기</span>
							<div class="mv_reviewlist">
								<div class="name">${review.nick}</div>
								<div class="reivew">${review.review.rvContent}</div>
							</div>
						</div>
					</c:forEach>
					</div>
				</div>
				<div class="mv_list_right-wrap">
					<img src="${movie.movie.poster}">
				</div>
			</div>
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
				<a href="/aboutus/">ABOUT US</a><br> <a href="/고객페이지/">
					고객페이지</a><br> <a href="/마이페이지/"> 마이페이지</a><br> <a
					href="/내정보관리/"> 내정보관리</a><br>

			</div>
		</div>
	</footer>

	<script type="text/javascript"
		src="${context}/resources/js/movie/movie.js"></script>
</body>
</html>