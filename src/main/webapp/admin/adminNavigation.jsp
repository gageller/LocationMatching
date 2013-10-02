<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Provider Navigation</title>
</head>
<body>
	<h2>${adminUser.firstName} ${adminUser.lastName}</h2>

	<form:form method="GET" modelAttribute="adminUser">
		
		<h3><u>Manage My Account</u></h3>
		<p>
			<a href="returnAdminMainPage.request">View Your Account Information</a>
			<br/>
			<a href="setupEditAdminUser.request">Edit Your Account Information</a>
		</p>
		<p>
			<h3><u>Manage Photos</u></h3>
			<a href="setupApproveDeclinePhotos.request">Approve/Decline Photos</a>
			<br/>
			<a href="setupEditLocationListings.request">Edit a Location</a>
			</br/>
			<a href="setupDeleteLocation.request">Delete a Location</a>
		</p>
		<p>
			<h3><u>Location Requests</u></h3>
			<a href="setupSearchLocationRequest.request">View a list of Location Requests</a>
		</p>
		<p>
			<h3><u>Submissions</u></h3>
			<a href="setupSubmissions.request">List of Submissions</a>
		</p>
		
		<br/>
		<a href="./index.jsp">Home</a>
	</form:form>

</body>
</html>