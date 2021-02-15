<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<head>
	<link rel="stylesheet" href="/resources/css/all.css">
	<link rel="stylesheet" href="/resources/css/common/reset.css">
	<link rel="stylesheet" href="/resources/css/user/infochange.css">
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
	
	<section class="content">
		<form class="frm_revise" action="${context}/user/infochangeimpl.do">
			<div class="content-wrapper">
				<div class="content-left div_content">
					<div>PROFILE</div>
					<div class="profile-img">
						<c:choose>
							<c:when test="${!empty sessionScope.user.userProfile}">
								<img src="${sessionScope.user.userProfile}">
							</c:when>
							<c:when test="${sessionScope.user.userProfile != ''}">
								<img src="${sessionScope.user.userProfile}">
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</div>
					<div>
						<div>프로필 사진 수정</div>
						<div class="profile-imgs-wrapper">
							<div class="radio-wrapper">
								<label class="profile-imgs"><input type="radio" name="userProfile" value="/resources/image/profile01.png" class="profile-img-example" data-url='profile01.png'>&#9312;</label>
								<label class="profile-imgs"><input type="radio" name="userProfile" value="/resources/image/profile02.png" class="profile-img-example" data-url='profile02.png'>&#9313;</label>
								<label class="profile-imgs"><input type="radio" name="userProfile" value="/resources/image/profile03.png" class="profile-img-example" data-url='profile03.png'>&#9314;</label>
								<label class="profile-imgs"><input type="radio" name="userProfile" value="/resources/image/profile04.png" class="profile-img-example" data-url='profile04.png'>&#9315;</label>
								<label class="profile-imgs"><input type="radio" name="userProfile" value="/resources/image/profile05.png" class="profile-img-example" data-url='profile05.png'>&#9316;</label>
							</div>
							<button type="button" class="create-img">확인</button>
						</div>
					</div>
					<div class="userinfo">
						<label class="userinfo-title">닉네임</label>
						<c:choose>
							<c:when test="${empty sessionScope.user.userNm}">
								<input type="text" name="userNm" id="userNm" placeholder="닉네임은 6자리 이하로 입력해주세요.">
							</c:when>
							<c:otherwise>
								<input type="text" name="userNm" id="userNm" placeholder="${sessionScope.user.userNm}">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="userinfo">
						<label class="userinfo-title">이메일</label>
						<div class="userEmail">${sessionScope.user.userEmail}</div>
					</div>
					<div class="userinfo">
						<label class="userinfo-title">비밀번호</label>
						<input type="password" name="userPw" id="userPw">
					</div>
				</div>
				<div class="content-right div_content">
					<div class="userinfo">
						<label class="userinfo-title">휴대전화</label>
						<c:choose>
							<c:when test="${empty sessionScope.user.userTel}">
								<input type="tel" name="userTel">
							</c:when>
							<c:otherwise>
								<input type="tel" name="userTel" placeholder="${sessionScope.user.userTel}">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="userinfo">
						<label class="userinfo-title">성별</label>
						<div class="gender-wrapper">
							<c:choose>
								<c:when test="${sessionScope.user.userGender == 'M'}">
									<label><input class="user-gender" type="radio" name="userGender" value="M" checked>남자</label>
									<label><input class="user-gender" type="radio" name="userGender" value="F">여자</label>
								</c:when>
								<c:when test="${sessionScope.user.userGender == 'F'}">
									<label><input class="user-gender" type="radio" name="userGender" value="M">남자</label>
									<label><input class="user-gender" type="radio" name="userGender" value="F" checked>여자</label>
								</c:when>
								<c:otherwise>
									<label><input class="user-gender" type="radio" name="userGender" value="M">남자</label>
									<label><input class="user-gender" type="radio" name="userGender" value="F">여자</label>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="userinfo">
						<label class="userinfo-title">생일</label>
						<c:choose>
							<c:when test="${empty sessionScope.user.userBirth}">
								<input type="date" name="userBirth" id="userBirth">
							</c:when>
							<c:otherwise>
								<input type="date" name="userBirth" id="userBirth" value="${sessionScope.user.userBirth}">
							</c:otherwise>
						</c:choose>
					</div>
					<button class="btn_leave" type="button" onclick="mySubmit()">회원 탈퇴</button>
				</div>
			</div>
			<button class="btn_complete">수정 완료</button>
		</form>
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
	
	<script type="text/javascript" src="/resources/js/user/infochange.js"></script>
	<script type="text/javascript">
		function mySubmit(){
			let userEmail = "${sessionScope.user.userEmail}";
			let headerObj = new Headers();
			headerObj.append('content-type', "application/x-www-form-urlencoded");
			console.dir(userEmail);
			fetch("/user/quit.do",{
				method : "post",
				headers : headerObj,
				body : "userEmail="+userEmail
			}).then(response => {
				if(response.ok){
					alert('회원 탈퇴가 완료되었습니다.');
					location.href = "/index.do";
				}else{
					alert('회원 탈퇴 중 에러가 발생했습니다.');
				}
			}).catch(error=>{
				error.alertMessage();
			})
			
		}
	</script>
</body>
</html>