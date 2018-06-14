<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<body>
	<form action="register.do" method="GET">
		Name: <input type="text" name="name">
		 <br> 
		Surname: <input type="text" name="surname">
		 <br>
		Login: <input type="text" name="login">
		 <br>
		Password: <input type="text" name="password">
		 <br>
		<input type="submit" value="Submit" />
	</form>
	<% if ( request.getAttribute( "message" ) != null ) { %>
	<%=request.getAttribute( "message" )%>
	<% } %>
</body>
</html>