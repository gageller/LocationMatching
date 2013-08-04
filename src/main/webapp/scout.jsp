<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Scout</title>
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
		function editLocationRequestButtonClick(index) {
			document.forms("provider").locationIndex.value = index;
			document.forms("provider").action = "editLocationRequest.request";
			document.forms("provider").submit();
		}
	-->
</script>
</head>
<body>
<form:form name="provider" method="GET" commandName="locationScout">
	<input type="hidden" name="requestIndex"/>
	<h2>${locationScout.firstName} ${locationScout.lastName }</h2>
	<table>
		<tr>
			<td class="boldCellText">Email Address:</td>
			<td>${locationScout.emailAddress}</td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td>${locationScout.phoneNumber}</td>
		</tr>
		<tr>
			<td><input type="button" value="Edit User Information..." onClick='processButtonClick("editProvider.request")'/></td>
		</tr>
	</table>
	<br/>
	<input type="button" name="addLocationRequestBttn" value="Add Location Request..." onClick='processButtonClick("addLocationRequest.request")'/>
	<br/>
	<h3>Locations</h3>
		
			<c:forEach items="${locationScout.locationRequests}" var="locationRequest">
			<table width="80%">
				<tbody>
					<tr>
						<td colspan="3"]><b>Location Name:</b> ${locationRequest.locationRequestName}</td>
						<td><input type="button" value="Edit Location..." onClick='editLocationRequestButtonClick("${locationRequest.id}")'/></td>
					</tr>
					<tr>
						<th align="left">Type of Location:</th>
						<th align="left">Location City:</th>
						<th align="left">Location State:</th>
						<th align="left">Location Zipcode:</th>
					</tr>
					<tr>	
						<td>${locationRequest.locationType}</td>
						<td>${locationRequest.locationRequestCity}</td>
						<td>${locationRequest.locationRequestState}</td>
						<td>${locationRequest.locationRequestZipcode}</td>
					</tr>
					
					<tr>
						<th align="left">Submission Date:</th>
						<th align="left">Beginning Date of Shooting:</th>
						<th align="left">End Date of Shooting:</th>
						<th align="left">Rate:</th>
					</tr>
					<tr>
						<td>${locationRequest.submissionDate}</td>
						<td>${locationRequest.shootBeginDate}</td>
						<td>${locationRequest.shootEndDate}</td>
						<td>${locationRequest.rate}</td>
					</tr>
					<tr>
						<td colspan="2"><b>Location Description:</b></td>
						<td colspan="2"><b>Project Notes:</b></td>
					</tr>
					<tr>
						<td colspan="2">${locationRequest.locationDescription}</td>
						<td colspan="2">${locationRequest.projectNotes}</td>
					</tr>
				</tbody>
				
				</table>
			</c:forEach>

	</form:form>
	<br/>
	<a href="./index.jsp">Home</a>

</body>
</html>