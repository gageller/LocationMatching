<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scout Navigation</title>
</head>
<body>
	<form method="GET" commandName="locationScout">
		<h2>${locationScout.firstName} ${locationScout.lastName}</h2>
		
		<h3><u>Manage My Account</u></h3>
		<p>
			<a href="returnScoutMainPage.request">View Account Information and Location Requests</a>
			<br/>
			<a href="setupEditLocationScout.request">Edit Account Information</a>
		</p>
		<p>
			<h3><u>Manage My Location Requests</u></h3>
			<a href="addLocationRequest.request">Add a Location Request</a>
			<br/>
			<a href="setupEditLocationRequestListings.request">Edit a Location Request</a>
			</br/>
			<a href="deleteLocationRequestListing.jsp">Delete a Location Request</a>
		</p>
		<p>
			<h3><u>Alerts</u></h3>
			<a href="setupScoutAlerts.request">View a list of Alerts</a>
		</p>
		<br/>
		<a href="./index.jsp">Home</a>
	</form>

</body>
</html>