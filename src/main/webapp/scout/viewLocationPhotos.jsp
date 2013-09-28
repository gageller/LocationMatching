<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Photos</title>
</head>
<h2>Location Photos</h2>
<body>
	<table cellspacing="10px" width="1000">
		<c:set var="itemCount" value="0"/>
			<tr>
		<c:forEach items="${alertLocation.locationImages}" var="image">
			<c:if test="${itemCount == 3}">
				</tr>
				<tr>
				<c:set var="itemCount" value="0"/>
			</c:if>
				<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/></td>
				
			<c:set var="itemCount" value="${itemCount + 1}"/>
		</c:forEach>
		</tr>
	</table>
	<br/>
</body>
</html>