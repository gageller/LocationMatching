<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a New Location Request</title>
</head>
<body>
	<form:form action="addLocationRequest.request" method="POST" commandName="locationRequest">
		<table>
			<tr>
				<td>Location Request Name*</td>
				<td><form:input path="locationRequestName"/></td>
			</tr>
			<tr>
				<td>Location Type*</td>
 				<td><form:select path="locationType" items="${locationTypeList}"/></td>
			</tr>
			<tr>
				<td>Location City*</td>
				<td><form:input path="locationRequestCity"/></td>
			</tr>
			<tr>
				<td>Location State*</td>
 				<td><form:select path="locationRequestState" items="${stateSelectList}"/></td> 
			</tr>
			<tr>
				<td>Location Zip Code*</td>
				<td><form:input path="locationRequestZipcode"/></td>
			</tr>
			<tr>
				<td>County where Location resides</td>
				<td><form:input path="locationRequestCounty"/>
			</tr>
			<tr>
				<td>Submission Date*</td>
				<td><form:input path="submissionDate"/></td>
			</tr>
			<tr>
				<td>Shoot Begin Date*</td>
				<td><form:input path="shootBeginDate"/></td>
			</tr>
			<tr>
				<td>Shoot End date*</td>
				<td><form:input path="shootEndDate"/></td>
			</tr>
			<tr>
				<td>Rate*</td>
				<td><form:input path="rate" /></td>
			</tr>
			<tr>
				<td>Location Description*</td>
				<td><form:textarea rows="3" columns="25" path="locationDescription"/></td>
			</tr>
			<tr>
				<td>Project Notes*</td>
				<td><form:textarea rows="3" columns="25" path="projectNotes" /></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Add Location Request"/>	
		<br/>
		<a href="./index.jsp">Home</a>	
	</form:form>

</body>
</html>