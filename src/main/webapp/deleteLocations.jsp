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
	<h2>Delete Locations</h2>
	<form:form name="deleteLocationForms" action="deleteLocations.request" method="POST" modelAttribute="locationProvider">
		<table width="200">
			<c:forEach items="${locationProvider.providerLocations}" var="location">
				<tr>
					<td><input type="checkbox" value="${location.id}" name="deleteCheck"/></td>
					<td>${location.locationName}</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<input type="submit" value="Delete Locations"/>
	</form:form>
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
</body>
</html>