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
<script>
	<!--
		// Process either the Location Provider edit bttn click or
		// the Add Location bttn click.
		function processButtonClick(action) {
			document.forms("provider").action = action;
			document.forms("provider").submit();
		}
	
		// Process the edit button click for a particular
		// location
		function editLocationButtonClick(index) {
			document.forms("provider").locationIndex.value = index;
			document.forms("provider").action = "editLocation.request";
			document.forms("provider").submit();
		}
	-->
</script>
</head>
<body>
<form:form name="provider" method="GET" commandName="locationProvider">
	<input type="hidden" name="locationIndex"/>
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
				<table border="1" width="600">
					<tr>
						<td colspan="4"><b>Location Name:</b> ${location.locationName}</td>
						<td><input type="button" value="Edit Location..." onClick='editLocationButtonClick("${location.id}")'/></td>						
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
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
</body>
</html>