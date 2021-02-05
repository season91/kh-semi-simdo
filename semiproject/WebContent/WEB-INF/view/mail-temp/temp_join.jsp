<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<body>
	<h1>반갑습니다. ${param.userEmail} 님</h1>
	<h1>Simdo:wm에 가입하신 것을 환영합니다!</h1>
	<h1>회원가입을 마무리하기 위해 인증 번호를 입력해주세요.</h1>
	<h2>인증 번호 : ${param.authNum}</h2>
</body>
</html>