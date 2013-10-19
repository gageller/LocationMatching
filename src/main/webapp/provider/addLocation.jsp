<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">

<title>Add a New Location</title>
</head>
<body> 
<h2>Add a New Location</h2>
	<br/>
	<p class="errorMessage">${errorMessage}</p>
	<br/>
	<form:form name="addLocation" action="addLocation.request" method="POST"  modelAttribute="location">
		<input type="hidden" name="source" value="${locationProvider.id}" />
		<table>
			<tr>
				<td>Location Name*</td>
				<td><form:input path="locationName"/></td>
			</tr>
			<tr>
				<td>Address*</td>
				<td><form:input path="locationAddress"/></td>
			</tr>
			<tr>
				<td>Address 2</td>
				<td><form:input path="locationAddress2"/></td>
			</tr>
			<tr>
				<td>City*</td>
				<td><form:input path="locationCity"/></td>
			</tr>
			<tr>
				<td>State*</td>
				<td><form:select path="locationState" items="${stateSelectList}"/></td>
			</tr>
			<tr>
				<td>Zip Code*</td>
				<td><form:input path="locationZipcode"/></td>
			</tr>
		</table>
		<br/>
		<br/>
		<br/>
		<p>After finishing adding the Location information, click on the "Finish Adding Location" button. You will then be 
		<br/>taken to the "Add Photo" page where you can upload photos of your property.</p>

		<br/>
		<br/>

		<input type="submit" value="Finish Adding Location"/>		
		</form:form>
</body>
</html>