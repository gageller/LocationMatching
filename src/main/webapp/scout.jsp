<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/lmStyle.css">
<title>Location Scout</title>
</head>
<body>
<form:form name="scoutForm" method="GET" commandName="locationScout">
	<input type="hidden" name="requestIndex"/>
	<table>
		<tr>
			<td class="boldCellText">Email Address:</td>
			<td>${locationScout.emailAddress}</td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td>${locationScout.phoneNumber}</td>
		</tr>
	</table>
	<br/>
	<h3>Location Requests</h3>
		
			<c:forEach items="${locationScout.locationRequests}" var="locationRequest">
			<table width="1000" border="1">
				<tbody>
					<tr>
						<td colspan="2"]><label class="boldText">Location Name:</label> ${locationRequest.locationRequestName}</td>
						<td colspan="2"]><label class="boldText">Type of Location: </label>${locationRequest.locationType}</td>
					</tr>
					<tr>
						<th width="250" align="left">Location City:</th>
						<th width="250" align="left">Location County:</th>
						<th width="250" align="left">Location State:</th>
						<th width="250" align="left">Location Zipcode:</th>
					</tr>
					<tr>	
						<td>${locationRequest.locationRequestCity}</td>
						<td>${locationRequest.locationRequestCounty}</td>
						<td>${locationRequest.locationRequestState}</td>
						<td>${locationRequest.locationRequestZipcode}</td>
					</tr>
					
					<tr>
						<th width="250" align="left">Submission Date:</th>
						<th width="250" align="left">Beginning Date of Shooting:</th>
						<th width="250" align="left">End Date of Shooting:</th>
						<th width="250" align="left">Rate:</th>
					</tr>
					<tr>
						<td>${locationRequest.submissionDate}</td>
						<td>${locationRequest.shootBeginDate}</td>
						<td>${locationRequest.shootEndDate}</td>
						<td>${locationRequest.rate}</td>
					</tr>
					<tr>
						<td width="500" colspan="2"><label class="boldText">Location Description:</label></td>
						<td width="500" colspan="2"><label class="boldText">Project Notes:</label></td>
					</tr>
					<tr>
						<td width="500" colspan="2">${locationRequest.locationDescription}</td>
						<td width="500" colspan="2">${locationRequest.projectNotes}</td>
					</tr>
				</tbody>
				
				</table>
				<br/>
			</c:forEach>

	</form:form>
<!-- 
	<br/>
		<a href="./scoutNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
-->
</body>
</html>