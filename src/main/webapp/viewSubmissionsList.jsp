<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submissions</title>
</head>
<body>
<h2>Submissions</h2>
	<form:form modelAttribute="locationProvider">
		<input type="hidden" name="nextPage" value="providerNavigation"/>
		<c:if test="${locationProvider.requestSubmissions.size() == 0}">
			<p>You have no submissions at this time.</p>
		</c:if>
		<c:forEach items="${locationProvider.requestSubmissions}" var="submission">
			<table border="1" width="1000">
				<tr>
					<th width="250">Location Name</th>
					<th width="250">Submission Date</th>
					<th width="250"></th>
					<th width="250">Delete Submission:<input type="checkbox" name="deleteSubmission" value="${submission.id}"/></th>
				</tr>
				<tr>
					<td>${submission.location.locationName}</td>
					<td>${submission.submissionDate}</td>
					<td></td>
					<td></td>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" colspan="4">Location Request Name</th>
				</tr>
				<tr>
					<td colspan="4">${submission.locationRequest.locationRequestName}</td>
				</tr>
				<tr>
					<th width="250">Rate</th>
					<th width="250">Submission Date</th>
					<th width="250">Shoot Begin Date</th>
					<th width="250">Shoot End Date</th>
				</tr>
				<tr>
					<td>${submission.locationRequest.rate}</td>
					<td>${submission.locationRequest.submissionDate}</td>
					<td>${submission.locationRequest.shootBeginDate}</td>
					<td>${submission.locationRequest.shootEndDate}</td>
				</tr>
				<tr>
					<th width="250">Location Request City</th>
					<th width="250">Location Request County</th>
					<th width="250">Location Request State</th>
					<th width="250">Location Request Zip Code</th>
				</tr>
				<tr>
					<td>${submission.locationRequest.locationRequestCity}</td>
					<td>${submission.locationRequest.locationRequestCounty}</td>
					<td>${submission.locationRequest.locationRequestState}</td>
					<td>${submission.locationRequest.locationRequestZipcode}</td>
				</tr>
 				
				<tr>
					<th colspan="2" width="400">Location Request Description</th>
					<th colspan="2" width="400">Project Notes</th>
				</tr>
				<tr>
					<td colspan="2" width="400">${submission.locationRequest.locationDescription}</td>
					<td colspan="2" width="400">${submission.locationRequest.projectNotes}</td>
				</tr>
			</table>
			<br/>
			<br/>
		</c:forEach>
	</form:form>
	<br/>
	<!-- <a href="navigateFromSubmissionListingPage.request">My Main Page</a> -->
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
	
</body>
</html>