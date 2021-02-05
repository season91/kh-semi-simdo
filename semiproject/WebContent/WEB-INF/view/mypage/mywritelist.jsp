<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/mypage/mywritelist.css">
</head>
<body>
	<div class="header-wrapper">
		<header class="header-section">
			<a class="top-logo-text" href="/index.do"><img class="top-logo-img" style="width: 20vh; margin-left: 5%" alt="logo" src="/resources/image/logo.png"></a>
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
				<div class="my-mv-calendar navi-menu" onclick="location.href='/mypage/calendar.do'">영화 달력</div>
				<div class="my-mv-review navi-menu" onclick="location.href='/mypage/mywritelist.do'">영화 후기</div>
				<div class="my-mv navi-menu" onclick="location.href='/mypage/mywish.do'">찜목록</div>
				<div class="my-info navi-menu" onclick="location.href='/user/infochange.do'">회원 정보 변경</div>
				<div class="my-qna navi-menu" onclick="location.href='/mypage/myqnalist.do'">나의 문의 & 요청</div>
			</div>
		</nav>
	</div>
	
	<section class="content">
		<div class="content-left-wrapper">
			<div class="left-top-wrapper">
				<div>내가 작성한 영화 후기</div>
				<button class="btn_write">등록하기</button>
			</div>
			<c:choose>
				<c:when test="${!empty requestScope.reviewList}">
					<c:forEach var="review" items="${requestScope.reviewList}" varStatus="status">
						<div class="reviewBox">
							<div class="reviewBox-top-wrapper">
								<div class="mvPoster">
									<img class="mvPoster" id="rvPoster" src="${review.thumbnail}">
								</div>
								<div class="reviewBox-mvInfo">
									<div class="mvTitle">${review.mvTitle}</div>
									<div class="mvScore">
										<i class="fas fa-star"></i>
										<span>${review.score}</span>
									</div>
									<div class="mvDate">
										<div>${review.watchDate} 감상</div>
										<div>${review.rvRegDate} 작성</div>
									</div>
								</div>
							</div>
							<div class="reviewBox-content">
								${review.rvContent}
							</div>
							<div class="reviewBox-bottom-wrapper">
								<a><i class="fas fa-plus revise-content"></i></a>
								<a onclick="reviewDel(${review.reviewNo})"><i class="far fa-trash-alt"></i></a>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="reviewBox">
						아직 작성된 후기가 없습니다.<br>
						후기를 작성해주세요.
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="content-right-wrapper">
			<div class="right-top-wrapper">
				<div>내가 작성한 나만의 명대사</div>
				<button class="btn_write">등록하기</button>
			</div>
			<c:choose>
				<c:when test="${!empty requestScope.fmslineList}">
					<c:forEach var="fmsline" items="${requestScope.fmslineList}" varStatus="status">
						<div class="fvlineBox">
							<div class="fvlineBox-top-wrapper">
								<div class="div_mvPoster">
									<img class="mvPoster" src="${fmsline.thumbnail}">
								</div>
								<div class="fvlineBox-mvInfo">
									<div class="mvTitle">${fmsline.mvTitle}</div>
								</div>
							</div>
							<div class="fvlineBox-content">
								${fmsline.fmlContent}
							</div>
							<div class="fvlineBox-bottom-wrapper">
								<a><i class="fas fa-plus revise-content"></i></a>
								<a onclick="fmslineDel(${fmsline.fmslineNo})"><i class="far fa-trash-alt"></i></a>
								<select name="lan" size=1 class="choose-lan" id="${status.count}">
									<option value="en">영어</option>
									<option value="ja">일본어</option>
									<option value="fr">프랑스어</option>
									<option value="zh-CN">중국어 간체</option>
									<option value="zh-TW">중국어 번체</option>
									<option value="ru">러시아어</option>
									<option value="de">독일어</option>
									<option value="es">스페인어</option>
								</select>
								<button class="btn_papago" onclick="translation('${fmsline.fmlContent}','${status.count}')">번역보기</button>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="fvlineBox">
						아직 작성된 명대사가 없습니다.<br>
						명대사를 작성해주세요.
					</div>
				</c:otherwise>
			</c:choose>
		</div>
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
	
	<script type="text/javascript" src="/resources/js/mypage/mywritelist.js"></script>
</body>
</html>