<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Provider Navigation</title>
</head>
<body>
	<h2>${locationProvider.firstName} ${locationProvider.lastName}</h2>

	<form method="GET" commandName="locationProvider">
		
		<h3><u><spring:message code="menu.sectionTitle.manageAccount"/></u></h3>
		<p>
			<a href="returnProviderMainPage.request"><spring:message code="menu.link.viewAccountInformationLocations"/></a>
			<br/>
			<a href="setupEditLocationProvider.request"><spring:message code="menu.link.editAccountInformation"/></a>
			<br/>
			<a href="setupManagePayments.request"><spring:message code="menu.link.managePaymentInformation"/></a>
		</p>
		<p>
			<h3><u><spring:message code="menu.sectionTitle.manageLocations"/></u></h3>
			<a href="addLocation.request"><spring:message code="menu.link.addLocation"/></a>
			<br/>
			<a href="setupEditLocationListings.request"><spring:message code="menu.link.editLocation"/></a>
			<br/>
			<a href="setupDeleteLocation.request"><spring:message code="menu.link.deleteLocation"/></a>
		</p>
		<p>
			<h3><u><spring:message code="menu.sectionTitle.locationRequests"/></u></h3>
			<a href="setupSearchLocationRequest.request"><spring:message code="menu.link.locationRequestsList"/></a>
		</p>
		<p>
			<h3><u><spring:message code="menu.sectionTitle.submissions"/></u></h3>
			<a href="setupSubmissions.request"><spring:message code="menu.link.submissionsList"/></a>
		</p>
		
		<br/>
		<a href="./index.jsp">Home</a>
	</form>

</body>
</html>