<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Provider</title>
<script>
	<!--
		function processButtonClick(action) {
			document.forms("provider").action = action;
			document.forms("provider").submit();
		}
	-->
</script>
</head>
<body>
<form:form name="provider" method="GET" commandName="locationProvider">
	<h2>${locationProvider.firstName} ${locationProvider.lastName }</h2>
	<table>
		<tr>
			<td class="boldCellText">Email Address:</td>
			<td>${locationProvider.emailAddress}</td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td>${locationProvider.phoneNumber}</td>
		</tr>
		<tr>
			<td><input type="button" value="Edit User Information..." onClick='processButtonClick("editProvider.request")'/></td>
		</tr>
	</table>
	<br/>
	<input type="button" name="addLocationBttn" value="Add Location..." onClick='processButtonClick("addLocation.request")'/>
	<br/>
	<h3>Locations</h3>
		<c:forEach items="${locationProvider.providerLocations}" var="location">
			
			<b>Location Name:</b> ${location.locationName}
			<table style="border-collapse: collapse;">
				<tr CLASS="rowBorder">
					<td>Address:</td>
					<td>${location.locationAddress}</td>
					<td>&nbsp;</td>
					<td>Address 2:</td>
					<td>${location.locationAddress2}</td>
					<td>&nbsp;</td>
					<td>City:</td>
					<td>${location.locationCity}</td>
					<td>&nbsp;</td>
					<td>State:</td>
					<td>${location.locationStateCode}</td>
					<td>&nbsp;</td>
					<td>Zip Code:</td>
					<td>${location.locationZipcode}</td>
				</tr>
			</table>
		</c:forEach>
	</form:form>
</body>
</html>