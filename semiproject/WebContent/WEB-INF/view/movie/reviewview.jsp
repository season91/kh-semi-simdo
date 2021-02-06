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
				<div class="score-view" OnClick="location.href ='/movie/naviview.do'" style="cursor:pointer;">평점순</div>
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
			
			
			</c:forEach>
			<div class="mv_list_wrap">
				<div class="mv_list_left-wrap">
					<div class="mv_list_top-wrap">
						<div class="mv_title"> 삼진그룹 영어토익반</div>
						<a class="mv_btn-more" href="/movie/detailview.do?title= 삼진그룹 영어토익반">MORE</a>
					</div>
					<div class="mv_list_middle-wrap">
						<span>줄거리</span>
						<div class="mv_plot">마이 드림 이즈 커리어우먼”1995년, 토익 600점만 넘기면 대리가 될 수 있다!입사 8년차 동기인 말단 여직원들이 ‘삼진그룹 영어토익반’에 모인다!실무 능력 퍼펙트, 현실은 커피 타기 달인인 생산관리3부 오지랖 ‘이자영’(고아성), 추리소설 마니아로 뼈 때리는 멘트의 달인 마케팅부 돌직구 ‘정유나’(이솜), 수학 올림피아드 우승 출신, 실체는 가짜 영수증 메꾸기 달인 회계부 수학왕 ‘심보람’(박혜수)은 대리가 되면 진짜 ‘일’을 할 수 있을 것이라는 희망에 부푼다.내부고발이라도 하게? 나서지 마. 우리만 다쳐잔심부름을 하러 간 공장에서 검은 폐수가 유출되는 것을 목격한 ‘자영’은 ‘유나’, ‘보람’과 함께 회사가 무엇을 감추고자 하는지, 결정적 증거를 찾으려 한다.불가능해 보이는 싸움, 세 친구는 해고의 위험을 무릅쓰고 고군분투를 시작하는데…아이 캔 두 잇, 유 캔 두 잇, 위 캔 두 잇! 회사와 맞짱 뜨는 용감한 세 친구!</div>
					</div>
					<div class="mv_list_bottom-wrap">
					<%-- 후기결과도 여기에 더붙는다. --%>
						<div class="mv_reviewlist_wrap">
							<span>심도들의 후기</span>
							<div class="mv_reviewlist">
								<div class="name">코끼리님</div>
								<div class="reivew">마이 드림 이즈 커리어우먼!!!! 사장님 잘생겼어요...현실은 저런 사장님 없잖아요.</div>
							</div>
							<div class="mv_reviewlist">
								<div class="name">대감집노예님</div>
								<div class="reivew">여자들의 찐우정 박수칩니당~!</div>
							</div>
						</div>
						<div class="mv_fmslinelist_wrap">
							<span>심도들의 기억에 남는 명대사</span>
							<div class="mv_fmslinelist">
								<div class="name">칸타타님 </div>
								<div class="reivew">어제의 너보다 오늘 더 성장했는데~~??!!</div>
							</div>
							<div class="mv_fmslinelist">
								<div class="name">너무이쁜이솜님</div>
								<div class="reivew">나를보지 말고 너를봐.</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="mv_list_right-wrap">
					<img src="http://file.koreafilm.or.kr/thm/02/99/17/09/tn_DPK016429.jpg">
				</div>
			</div>
			<%-- 여기까지가 한 div --%>
			
			<%-- 여기서부터검색결과가 출력되는 것 --%>
			<div class="mv_list_wrap">
				<div class="mv_list_left-wrap">
					<div class="mv_list_top-wrap">
						<div class="mv_title"> 라라랜드</div>
						<a class="mv_btn-more" href="/movie/detailview.do?title= 라라랜드">MORE</a>
					</div>
					<div class="mv_list_middle-wrap">
						<span>줄거리</span>
						<div class="mv_plot">황홀한 사랑, 순수한 희망, 격렬한 열정… 이 곳에서 모든 감정이 폭발한다!꿈을 꾸는 사람들을 위한 별들의 도시 ‘라라랜드’. 재즈 피아니스트 ‘세바스찬’(라이언 고슬링)과 배우 지망생 ‘미아’(엠마 스톤), 인생에서 가장 빛나는 순간 만난 두 사람은 미완성인 서로의 무대를 만들어가기 시작한다.</div>
					</div>
					<div class="mv_list_bottom-wrap">
					<%-- 후기결과도 여기에 더붙는다. --%>
						<div class="mv_reviewlist_wrap">
							<span>심도들의 후기</span>
							<div class="mv_reviewlist">
								<div class="name">뼈해장국님</div>
								<div class="reivew">이건 노래가 다했죠.. 개인적으로 마지막부분이 너무 슬펐어요.. </div>
							</div>
							<div class="mv_reviewlist">
								<div class="name">노란원피스님</div>
								<div class="reivew">City of Stars 흥얼거리면서 왔습니다</div>
							</div>
							<div class="mv_reviewlist">
								<div class="name">닥터스트레인지님</div>
								<div class="reivew">라이언고슬링.... 라며들었다..</div>
							</div>
						</div>
						<div class="mv_fmslinelist_wrap">
							<span>심도들의 기억에 남는 명대사</span>
							<div class="mv_fmslinelist">
								<div class="name">라라랜드님</div>
								<div class="reivew">재능은 없고 열정만 가득한 사람들 있잖아. 나도 그런 사람 중 하나였나 봐.</div>
							</div>
							<div class="mv_fmslinelist">
								<div class="name">스티커님</div>
								<div class="reivew">세상에서 제일 쓸모없고 가치없는 말이 '그만하면 잘했어(GOOD GOB)'이야</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="mv_list_right-wrap">
					<img src="http://file.koreafilm.or.kr/thm/02/00/05/51/tn_DPF020277.jpg">
				</div>
			</div>
			<%-- 여기까지가 한 div --%>
			
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