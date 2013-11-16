<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">

<title>Add a New Location</title>
</head>
<body> 
<h2><spring:message code="addLocation.pageHeader"/></h2>
	<br/>
	<p class="errorMessage">${errorMessage}</p>
	<br/>
	<form:form name="addLocation" action="addLocation.request" method="POST"  modelAttribute="location">
		<input type="hidden" name="source" value="${locationProvider.id}" />
		<table>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.locationName"/></label></td>
				<td><form:input path="locationName"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.address"/></label></td>
				<td><form:input path="locationAddress"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.address2Optional"/></label></td>
				<td><form:input path="locationAddress2"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.city"/></label></td>
				<td><form:input path="locationCity"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.state"/></label></td>
				<td><form:select path="locationState" items="${stateSelectList}"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.zipcode"/></label></td>
				<td><form:input path="locationZipcode"/></td>
			</tr>
		</table>
		<br/>
		<br/>
		<br/>
		<p><spring:message code="addLocation.instructionsPart1"/> 
		<br/><spring:message code="addLocation.instructionsPart2"/></p>

		<br/>
		<br/>

		<input type="submit" value="<spring:message code="addLocation.submitButtonText"/>"/>		
		</form:form>
</body>
</html>