<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/lmStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Provider</title>
</head>
<body>
<h2><spring:message code="provider.pageHeader"/></h2>
<form:form name="provider" method="GET" commandName="locationProvider">
	<input type="hidden" name="locationIndex"/>
	<table>
		<tr>
			<td><label class="boldText"><spring:message code="common.field.emailAddressColon"/></label></td>
			<td>${locationProvider.emailAddress}</td>
		</tr>
		<tr> 
			<td><label class="boldText"><spring:message code="common.field.phoneNumberColon"/></label></td>
			<td>${locationProvider.phoneNumber}</td>
		</tr>
	</table>
	<br/>
	<br/>
	<h3><spring:message code="provider.locationSectionHeader"/></h3>
			<c:forEach items="${locationProvider.providerLocations}" var="location">
				<table border="1" width="1000">
					<tr>
						<td colspan="5"><label class="boldText"><spring:message code="common.field.locationNameColon"/></label> ${location.locationName}</td>
					<!-- 	<td><input type="button" value="Edit Location..." onClick='editLocationButtonClick("${location.id}")'/></td> -->						
					</tr>
					<tr>
						<td width="300"><label class="boldText"><spring:message code="common.field.address"/></label></b></td>
						<td width="100"><label class="boldText"><spring:message code="common.field.address2"/></label></td>
						<td width="300"><label class="boldText"><spring:message code="common.field.city"/></label></td>
						<td width="200"><label class="boldText"><spring:message code="common.field.state"/></label></td>
						<td width="100"><label class="boldText"><spring:message code="common.field.zipcode"/></label></td>
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
</body>
</html>