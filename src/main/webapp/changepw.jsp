<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<body>
	<form action="change.do" method="GET">
		Old password: <input type="text" name="oldpw">
		 <br> 
		New password: <input type="text" name="newpw">
		 <br>
		<input type="submit" value="Submit" />
	</form>
</body>
</html>