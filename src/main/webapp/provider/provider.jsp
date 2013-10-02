<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/lmStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Provider</title>
</head>
<body>
<h2>Account Information and Locations</h2>
<form:form name="provider" method="GET" commandName="locationProvider">
	<input type="hidden" name="locationIndex"/>
	<table>
		<tr>
			<td class="boldCellText">Email Address:</td>
			<td>${locationProvider.emailAddress}</td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td>${locationProvider.phoneNumber}</td>
		</tr>
	</table>
	<br/>
	<br/>
	<h3>Locations</h3>
			<c:forEach items="${locationProvider.providerLocations}" var="location">
				<table border="1" width="600">
					<tr>
						<td colspan="5"><b>Location Name:</b> ${location.locationName}</td>
					<!-- 	<td><input type="button" value="Edit Location..." onClick='editLocationButtonClick("${location.id}")'/></td> -->						
					</tr>
					<tr>
						<td><b>Address:</b></td>
						<td><b>Address 2:</b></td>
						<td><b>City:</b></td>
						<td><b>State:</b></td>
						<td><b>Zip Code:</b></td>
					</tr>
					<tr>
						<td>${location.locationAddress}</td>				
						<td>${location.locationAddress2}</td>
						<td>${location.locationCity}</td>												
						<td>${location.locationState}</td>
						<td>${location.locationZipcode}</td>					
					</tr>
				</table>
				<br/>
			</c:forEach>
	</form:form>
</body>
</html>