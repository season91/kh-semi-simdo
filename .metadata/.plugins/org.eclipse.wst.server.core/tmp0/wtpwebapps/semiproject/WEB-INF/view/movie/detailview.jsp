<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp"%>
<head>
<link rel="stylesheet" href="${context}/resources/css/common/reset.css">
<link rel="stylesheet" href="${context}/resources/css/movie/detail.css">
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

			<div class="mv_info_wrap">
			<div class="mv_info_top-wrap">
				<p class="mv_info_detail">영화 상세 정보</p>
				<div class="mv_info_title">
					<c:choose>
						<c:when test="${empty headfms}">
						<p>${res.mvTitle}</p>
						</c:when>
						<c:otherwise>
						<p>${headfms}</p>
						</c:otherwise>
					</c:choose>
						
						
				</div>
			</div>
			<div class="mv_info_middle-wrap">
			
				<div class="mv_info_top_left mv_info_img">
					<img src="${res.poster}">
				</div>
				<div class="mv_info_top_right">
					<div class="mv_info-title_wrap">
						<div class="mv_info_basic">기본 정보</div>
						<%-- 이부분 해당 영화에 글을썻거나, 찜했으면 색깔 넣어서 표현해주어야 한다.--%>
						
						<div class="mv_info_btn">

						<c:choose>
							<c:when test="${empty wish}">
								<button name="wish" class="wish">
										<i class="fas fa-heart wish" id="btn_wish" style="color:black"></i>
								</button>
							</c:when>
							<c:otherwise>
								<button name="wish" class="wish">
								<i class="fas fa-heart" id="btn_wish" style="color:red"></i>
								</button>
							</c:otherwise>
						</c:choose>
							
						<c:choose>
								<c:when test="${empty write}">
									<i class="fas fa-journal-whills write-is" id="btn_write-is"></i>
								</c:when>
								<c:otherwise>
									<i class="fas fa-journal-whills write-is" id="btn_write-is" style="color:pink"></i>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					<div class="mv_info_basic_content">
					<input type="hidden" name="mvno" value="${res.mvNo}" class="mvno">
						<p>${res.mvTitle}</p>
						<p>감독 : ${res.director}</p>
						<p>장르 : ${res.genre}</p>
						<p>제작국가 : ${res.nation}</p>
						<p>상영시간 : ${res.runtime}</p>
						<p>관람등급 : ${res.rating}</p>
					</div>
					<div class="mv_info_plot">
						<p>줄거리<p>
						<div class="mv_info_plot_content">
							${res.plot}	
						</div>

					</div>
					<div class="mv_info_score">
					<c:choose>
						<c:when test="${score == 'NaN'}">
							<p>심도 평점을 매겨주세요!</p>
						</c:when>
						<c:otherwise>
							<p>심도 평점 : ${score}</p>
						</c:otherwise>
					</c:choose>
						
					</div>
					<div class="mv_info_write">
						<span><a href="/mypage/writereview.do?mvno=${res.mvNo}">후기작성</a></span> <span><a href="/mypage/writeline.do?mvno=${res.mvNo}">나만의 명대사 작성</a></span>
					</div>

				</div>
			</div>

			<div class="mv_info_bottom-wrap">
				<div class="mv_info_bottom_left">
					<div class="mv_info_simdo-review-wrapper">
						<span class="mv_info_simdo-review">심도 후기</span>
						<%-- 여기서부터 포문 반복, 없다면 첫등록 안내--%>
						<c:choose>
							<c:when test="${empty reviewList}">
								<div class="review"> 첫 후기를 등록해보세요!</div>
							</c:when>
							<c:otherwise>
								<c:forEach var="review" items="${reviewList}">
								<div class="mv_review_wrap">
									<div class="mv_review">
										<div class="name">${review.nick}</div>
										<div class="review">${review.review.rvContent}</div>
									</div>
								</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						
					</div>
				</div>
				<div class="mv_info_bottom_right">
					<div class="mv_info_simdo-fmsline-wrapper">
						<div class="mv_info_simdo-fmsline">심도 명대사</div>
						<div class="mv_fmsline">
						<%-- 여기서부터 포문 반복 명대사,없다면 첫등록 안내 --%>
						<c:choose>
							<c:when test="${empty fmsList}">
								<div class="review"> 첫 명대사를 등록해보세요!</div>
							</c:when>
							<c:otherwise>
								<c:forEach var="fmsline" items="${fmsList}">
								<div class="mv_review_wrap">
									<div class="mv_review">
										<div class="name">${fmsline.nick}</div>
										<div class="review">${fmsline.fmsline.fmlContent}</div>
									</div>
								</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</div>
					</div>
				</div>
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

	<script type="text/javascript" src="${context}/resources/js/movie/movie.js"></script>
	<script type="text/javascript" src="${context}/resources/js/movie/detail.js"></script>
</body>
</html>