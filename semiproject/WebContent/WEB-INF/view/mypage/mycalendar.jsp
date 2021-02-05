<%@page import="java.util.Calendar"%>
<%@page import="com.kh.simdo.common.util.calendar.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<head>
<link rel="stylesheet" href="${context}/resources/css/mypage/calendar.css">
</head>
<body>
	<div class="header-wrapper">
		<header class="header-section">
			<a class="top-logo-text" href="/index/index.do"><img class="top-logo-img" style="width: 20vh; margin-left: 5%" alt="logo" src="/resources/image/logo.png"></a>
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
				<div class="my-mv-calendar navi-menu">영화 달력</div>
				<div class="my-mv-review navi-menu" onclick="location.href='/mypage/mywritelist.do'">영화 후기</div>
				<div class="my-mv navi-menu">찜목록</div>
				<div class="my-info navi-menu" onclick="location.href='/user/infochange.do'">회원 정보 변경</div>
				<div class="my-qna navi-menu">나의 문의 & 요청</div>
			</div>
		</nav>
	</div>

	<section>
	<%
		//현재 날짜의 년도와 월을 가져옴
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		
		//값을 전송받는 것이 필요
		String paramYear = request.getParameter("year");
		String paramMonth = request.getParameter("month");
		
		//두번째 호출 페이지에서 요청된 년도와 월의 값을 저장
		if(paramYear != null){
			year = Integer.parseInt(paramYear);
		}
		
		if(paramMonth != null){
			month = Integer.parseInt(paramMonth);
		}
		
		if(month>12){
			month=1;
			year++;
		}
		
		if(month<1){
			month=12;
			year--;
		}
		
		//요청받은 년도와 월의 일자로 Calendar 클래스 세팅
		cal.set(year, month-1, 1);
		
		//매월 1일의 요일
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		//월의 최대 일수
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		//달력의 일정을 표현
		//다오 작성후에
		/* CalBoardDao dao = new CalBoardDao_Impl(); */
		
		//yyyymm
		String yyyymm = year + Util.isTwo(String.valueOf(month));
		
		//게시글 가져옴
		/* Map<String,String> map = new HashMap<String,String>();
		map.put("userNo", "abcd");
		map.put("yyyymm", yyyymm);
		
		List<CalDto> clist = dao.getCalViewList(map); */
		
		
		
	%>
	<%-- <%=clist %> --%>
	<div class="calendar-wrap">
	<table id="calendar">
		<caption id="top">
			<a href="./calendar.do?year=<%=year-1%>&month=<%=month%>">◁</a>
			<a href="./calendar.do?year=<%=year%>&month=<%=month-1%>">◀</a>
				<span class="y"><%=year %></span>년도
				<span class="m"><%=month %></span>월			
			<a href="./calendar.do?year=<%=year%>&month=<%=month+1%>">▶</a>
			<a href="./calendar.do?year=<%=year+1%>&month=<%=month%>">▷</a>
		</caption>
		<tr>
			<th>SUN</th>
			<th>MON</th>
			<th>TUE</th>
			<th>WED</th>
			<th>THU</th>
			<th>FRI</th>
			<th>SAT</th>
		</tr>
		<tr>
			<%
				//공백
				for(int i=0; i<dayOfWeek-1;i++){
					out.print("<td>&nbsp;</td>");
				}
			
				//달력 일수
				for(int i=1; i<=lastDay;i++){
					%>
					<td>
						
						<a style="color:<%=Util.fontColor(i,dayOfWeek)%>">
							<%=i %>
						</a>
						
						<!-- 썸네일 div 생성 -->
						<!-- 날짜와 유저번호를 보내서 detail의 thumbnail을 집어넣어야함 -->
						
					</td>
					<%
					
					if((dayOfWeek-1+i)%7==0){
						out.print("</tr><tr>");
					}
				}
				
				for(int i=0; i< (7-(dayOfWeek-1+lastDay)%7)%7;i++){
					out.print("<td>&nbsp;</td>");
				}
			
			%>
		</tr>
		
	</table>
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

</body>
</html>