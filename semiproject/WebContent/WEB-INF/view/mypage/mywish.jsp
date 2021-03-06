<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<head>
<link rel="stylesheet" href="${context}/resources/css/common/reset.css">
<link rel="stylesheet" href="${context}/resources/css/mypage/mywish.css">
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
   <div class="content_title">
      <div class="name_wish">내가 찜한 영화</div>
      <button class="btn_write" onclick="location.href='/movie/naviview.do?nation=대한민국'">영화 고르러 가기</button>
   </div> 
               
         <c:choose>
            <c:when test="${!empty requestScope.wishList}">
            <c:forEach var="wish" items="${requestScope.wishList}" varStatus="status">
            
            <div class="content_all">
            <div class="mywish">
            <img class="wishposter" id="wsPoster" src="${wish.poster}">
            </div>
            <div class="content_detail">
            <div class="mvDate">
               <div>찜한 날짜 ${wish.wishRegDate}</div>
               
            </div>
            </div>
            </div>
            
            
         
         </c:forEach>
         </c:when>
         
         
         <c:otherwise>
            <div class="denay_wish">
            아직 등록하신 찜목록이 없습니다.<br>               
            감명깊은영화 또는 보고 싶은 영화를 찜설정해주세요!
            </div>
      
         </c:otherwise>
   </c:choose>
   
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

   
</body>
</html>