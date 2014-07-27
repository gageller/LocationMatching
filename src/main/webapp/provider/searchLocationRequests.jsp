<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Location Request</title>
</head>

<body>
<script>
	
		function submissionBttnClick(locationRequestId, locationId){
			if(locationId.length > 0){
				document.forms("searchLocationRequest").locationRequestId.value = locationRequestId;
				document.forms("searchLocationRequest").action = "processLocationRequestSubmission.request";
				document.forms("searchLocationRequest").locationId.value = locationId;
				document.forms("searchLocationRequest").submit();
			}
			else {
				alert("Please make a selection from My Locations to contact the Requestor.")
			}
		}
	
</script>

	<form:form modelAttribute="searchLocationRequest" action="processSearchLocationRequest.request" method="POST">
		<input type="hidden" name="locationRequestId" value=""/>
		<input type="hidden" name="locationId" value=""/>
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

	<c:if test="${errorSubmissionMessage.length() > 0}">
		<p class="errorMessage">${errorSubmissionMessage}</p>
	</c:if>
	<c:if test="${successfulSubmissionMessage.length() > 0}">
		<p class="successMessage">${successfulSubmissionMessage}</p>
	</c:if>
	<c:forEach items="${requestSearchResults}" var="locationRequest">
			<table width="1000">
				<tbody>
					<tr>
						<td colspan="2"><b>Location Name:</b> ${locationRequest.value.locationRequestName}</td>
						<td>My Locations: <select name="locationSelect${locationRequest.value.id}">
							<option value=""></option>
							<c:forEach items="${locationProvider.providerLocations}" var="location">
								<option value="${location.id}">${location.locationName}</option>
							</c:forEach>
						</select>
						<td><input type="button" value="Contact Requestor" onClick='submissionBttnClick(${locationRequest.value.id}, locationSelect${locationRequest.value.id}.value)'/>
					</tr>
					<tr>
						<th width="250" align="left">Type of Location:</th>
						<th width="250" align="left">Location City:</th>
						<th width="250" align="left">Location State:</th>
						<th width="250" align="left">Location Zipcode:</th>
					</tr>
					<tr>	
						<td>${locationRequest.value.locationType}</td>
						<td>${locationRequest.value.locationRequestCity}</td>
						<td>${locationRequest.value.locationRequestState}</td>
						<td>${locationRequest.value.locationRequestZipcode}</td>
					</tr>
					
					<tr>
						<th width="250" align="left">Submission Date:</th>
						<th width="250" align="left">Beginning Date of Shooting:</th>
						<th width="250" align="left">End Date of Shooting:</th>
						<th width="250" align="left">Rate:</th>
					</tr>
					<tr>
						<fmt:formatDate  type="date" pattern="M/dd/yyyy" var="formattednDate" value="${locationRequest.value.submissionDate}" />
						<td>${formattednDate}</td>
						<fmt:formatDate  type="date" pattern="M/dd/yyyy" var="formattednDate" value="${locationRequest.value.shootBeginDate}"/>
						<td>${formattednDate}</td>
						<fmt:formatDate  type="date" pattern="M/dd/yyyy" var="formattednDate" value="${locationRequest.value.shootEndDate}"/>
						<td>${formattednDate}</td>
						<td>${locationRequest.value.rate}</td>
					</tr>
					<tr>
						<td width="500" colspan="2"><b>Location Description:</b></td>
						<td width="500" colspan="2"><b>Project Notes:</b></td>
					</tr>
					<tr>
						<td width="500" colspan="2">${locationRequest.value.locationDescription}</td>
						<td width="500" colspan="2">${locationRequest.value.projectNotes}</td>
					</tr>
					</tbody>
				</table>
				<br/>
	</c:forEach>
		</form:form>
<!-- 
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
-->
</body>
</html>