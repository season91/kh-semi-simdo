<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.kh.simdo.common.code.ConfigCode"
    %>
<%@ include file="/WEB-INF/view/include/head.jsp" %>
<body>
	<c:choose>
		<c:when test="${param.userPw} == 'null'">
			<h1>안녕하세요. ${param.userEmail} 님</h1>
			<h2>${param.userEmail}님은 아직 Simdo:wm의 회원이 아닙니다.</h2>
			<h2>아래의 링크를 눌러 Simdo:wm의 회원이 되어주세요!</h2>
			<a href="<%= ConfigCode.DOMAIN %>/user/join.do">Simdo:wm 회원가입 하러 가기</a>
		</c:when>
		<c:otherwise>
			<h1>안녕하세요. ${param.userEmail} 님</h1>
			<h2>Simdo:wm에서 문의하신 비밀번호를 발송해드립니다.</h2>
			<h2>아래의 비밀번호를 확인해주세요.</h2>
			<h2>비밀번호 : ${param.userPw}</h2>
		</c:otherwise>
	</c:choose>
</body>
</html>