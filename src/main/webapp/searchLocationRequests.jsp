<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Request</title>
</head>
<body>
	<form:form modelAttribute="searchLocationRequest" action="searchLocationRequest.request" method="POST">
		<h2><u>Search For Location Requests</u></h2>
		<table>
			<tr>
				<th>City<th>
				<th>State</th>
				<th>Zipcode</th>
				<th>County</th>
			</tr>
			<tr>
				<td><form:input path="locationRequestCity"/></td>
				<td><form:select path="locationRequestState" items="${stateSelectList}"/></td>
				<td><form:input path="locationRequestZipcode"/></td>
				<td><form:input path="locationRequestZipcode"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>