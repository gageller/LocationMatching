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
	<form:form modelAttribute="searchLocationRequest" action="processSearchLocationRequest.request" method="POST">
		<h2><u>Search For Location Requests</u></h2>
		<table>
			<tr>
				<th align="left">City</th>
				<th align="left">State</th>
				<th align="left">Zipcode</th>
				<th align="left">County</th>
				<th align="left">Location Type</th>
				<th align="left">Location Request Name</th>
			</tr>
			<tr>
				<td><form:input path="locationRequestCity"/></td>
				<td><form:select path="locationRequestState" items="${stateSelectList}"/></td>
				<td><form:input path="locationRequestZipcode"/></td>
				<td><form:input path="locationRequestCounty"/></td>
				<td><form:select path="locationType" items="${locationTypeList}"/></td>
				<td><form:input path="locationRequestName"/></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Search..."/>
	</form:form>
	<br/>
	<c:forEach items="${requestSearchResults}" var="locationRequest">
			<table width="1000">
				<tbody>
					<tr>
						<td colspan="4"]><b>Location Name:</b> ${locationRequest.locationRequestName}</td>
					</tr>
					<tr>
						<th width="250" align="left">Type of Location:</th>
						<th width="250" align="left">Location City:</th>
						<th width="250" align="left">Location State:</th>
						<th width="250" align="left">Location Zipcode:</th>
					</tr>
					<tr>	
						<td>${locationRequest.locationType}</td>
						<td>${locationRequest.locationRequestCity}</td>
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
						<td width="500" colspan="2"><b>Location Description:</b></td>
						<td width="500" colspan="2"><b>Project Notes:</b></td>
					</tr>
					<tr>
						<td width="500" colspan="2">${locationRequest.locationDescription}</td>
						<td width="500" colspan="2">${locationRequest.projectNotes}</td>
					</tr>
					</tbody>
				</table>
				<br/>
	</c:forEach>
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
</body>
</html>