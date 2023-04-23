<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
%>	

	<a href="<%=contextPath%>/car/ee">
		<img src="<%=contextPath%>/img/Main.jpg" width="100%">
	</a>

</body>
</html>