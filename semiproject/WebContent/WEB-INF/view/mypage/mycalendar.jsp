<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/mypage/calendar.css">
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
					<a class="top_user top_join" href="/mypage/calendar.do">마이페이지</a>
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
<div class="content">

<table id="calendar" border="3" align="center" style="border-color:#3333FF ">
    <tr><!-- label은 마우스로 클릭을 편하게 해줌 -->
        <td class="cal-top"><label onclick="prevMonth()"><</label></td>
        <td class="cal-top" align="center" id="tbCalendarYM" colspan="5">
        yyyy년 m월</td>
        <td class="cal-top"><label onclick="nextMonth()">>
            
        </label></td>
    </tr>
    <tr>
        <td class="cal-top" align="center"><font color ="#CD0000">일</td>
        <td class="cal-top" align="center">월</td>
        <td class="cal-top" align="center">화</td>
        <td class="cal-top" align="center">수</td>
        <td class="cal-top" align="center">목</td>
        <td class="cal-top" align="center">금</td>
        <td class="cal-top" align="center"><font color ="#00008C">토</td>
    </tr> 
</table>
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

<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/resources/js/mypage/calendar.js"></script>
<script type="text/javascript">
const abc = () =>{
	let month = today.getMonth()+1;
	let yy = today.getFullYear();
	let year = String(yy).substr(2,2);
	if(month < 10){
		month = '0'+ month;
	}
	
	let current = year + '/' + month;

	let date = {
			 'year' : year,
			 'month' : month
			};
	getReviewList(date);
}

const getReviewList = (date)=>{		
	$.ajax({
		  url: '/mypage/callist.do?year=' + date.year + '&month=' + date.month,
		  type: 'get',
		  data: {
		    date
		  },
		  dataType: 'text',
		  success: function(response) {
		    printReview(JSON.parse(response));
		  },
		  fail: function(error) {
			  /* ajax통신 실패시 알림창 */
			  console.log("fail");
		  }
		 
		});
		
}

const printReview = (reviewList)=>{
	/* for (let k in reviewList) {
		
	} */
	
	    let k = reviewList.length - 1;
	    let wDate = reviewList[0].watchDate;
	    let wYear = wDate.slice(-2);
	    let wMonth = wDate.split('월')[0];
	    let wDay = wDate.split('월')[1].split(',')[0].trim();
	    let month;
	    let day;
	    if(wMonth.length < 2){
			month = '0'+ wMonth;
		}else{
			month = wMonth;
		}
	    if(wDay.length < 2){
			day = '0'+ wDay;
		}else{
			day = wDay;
		}
	    
	    if(k < 1){
	    	$("#d_" + wDay).append('<a class="img-wrap" href="/mypage/mydailylist.do?year=' + wYear + '&month=' + month + '&day=' + day + '"><br> <img class="img-wrap" src="' + reviewList[0].thumbnail + '"></a>');
	    }else{
	    	$("#d_" + wDay).append('<a class="img-wrap" href="/mypage/mydailylist.do?year=' + wYear + '&month=' + month + '&day=' + day + '"><br> + ' + k + ' <img class="img-wrap" src="' + reviewList[0].thumbnail + '"></a>');
	    }
}
	
	buildCalendar();
	abc();
	
	
	
	const prevMonth = ()=>{
		prevCalendar();
		abc();
	}
	
	const nextMonth =()=>{
		nextCalendar();
		abc();
	}
	
	
	
</script>

</body>

</html>