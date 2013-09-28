<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Locations</title>
</head>
<body>
	<h2>Delete Location Requests</h2>
	<form:form name="deleteLocationRequestsForm" action="deleteLocationRequests.request" method="POST" modelAttribute="locationScout">
		<table width="200">
			<c:forEach items="${locationScout.locationRequests}" var="locationRequest">
				<tr>
					<td><input type="checkbox" value="${locationRequest.id}" name="deleteCheckBoxArray"/></td>
					<td>${locationRequest.locationRequestName}</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<input type="submit" value="Delete Location Requests"/>
	</form:form>
<!-- 
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
-->
</body>
</html>