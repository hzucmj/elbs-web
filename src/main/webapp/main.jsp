<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.monitor.Functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String auth = Functions.getLocalAuth();
	try {
		if (auth.equals("admin")) {
			request.getRequestDispatcher("./adminIdx.jxp").forward(request, response);
		} else {
			request.getRequestDispatcher("./index.jxp").forward(request, response);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
%>
</body>
</html>