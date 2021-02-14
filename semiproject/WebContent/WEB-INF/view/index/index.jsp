<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>

<html>
<head>
   <link rel="stylesheet" href="/resources/css/all.css">
   <link rel="stylesheet" href="/resources/css/common/reset.css">
   <link rel="stylesheet" href="/resources/css/index/index.css">
   <link rel="stylesheet" href="/resources/css/index/slide.css">

</head>

<!--
 author:kwan
 -->

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
   
  <section>
   <div class="wrapper">
   <div class= "title" >나라별영화</div>
   <hr class="line" width="150" align="left" color="white">
      <ul class="inner">
       <li class="inner-item bak">
         <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/00/01/32/tn_DPF001450.jpg">
         <div class="inner-item-contents">
            <h2>인셉션</h2>
            <p>
            개요:액션|미국|147분<br>
            개봉:2010.07.21<br>
            평점:9.61<br>
            관객수:599만
            </p>
         </div>
       </li>   
       <li class="inner-item mad">
          <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/00/02/94/tn_DPF008206.JPG">
          <div class="inner-item-contents">
            <h2>해리포터:마법사의돌</h2>
            <p>
            개요:판타지|영국|152분<br>
            개봉:2001.12.14<br>
            평점:9.33<br>
            관객수:26만 
            </p>
         </div>
       </li>
       <li class="inner-item three">   
          <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/99/17/14/tn_DPK016642.jpg">
          <div class="inner-item-contents">
            <h2>세자매</h2>
            <p>
            개요:드라마|한국|115분<br>
            개봉:2021.01.27<br>
            평점:8.98<br>
            관객수:4.2만
            </p>
         </div>
       </li>
       
      </ul>
   </div>
   
   
   <div class="wrapper">
   <div class= "title">장르별 영화</div>
      <hr class="line" width="150" align="left" color="white">
      <ul class="inner">
       <li class="inner-item bak">
         <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/00/05/51/tn_DPF020277.jpg">
         <div class="inner-item-contents">
            <h2>라라랜드</h2>
            <p>
            개요:드라마|미국|120분<br>
            개봉:2016.12.07<br>
            평점:8.91<br>
            관객수:375만명
            </p>
         </div>
       </li>   
       <li class="inner-item mad">
          <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/00/01/79/tn_DPF003120.jpg">
          <div class="inner-item-contents">
            <h2>레미제라블</h2>
            <p>
            개요:드라마|영국|158분<br>
            개봉:2012.12.19<br>
            평점:9.20<br>
            관객수:593만명 
            </p>
         </div>
       </li>
       <li class="inner-item begin">   
          <img class="inner-item-cover" src="http://file.koreafilm.or.kr/thm/02/00/01/33/tn_DPF001491.jpg">
          <div class="inner-item-contents">
            <h2>이클립스</h2>
            <p>
            개요:판타지|미국|124분<br>
            개봉:2010.07.7<br>
            평점:7.37<br>
            관객수:206만명
            </p>
         </div>
       </li>
       
      </ul>
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