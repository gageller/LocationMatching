<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a Location</title>
</head>
<body>
	<form:form action="addLocation.request" method="POST" commandName="location">
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
				<td><form:select path="locationStateCode" items="${stateCodeList}"></form:select></td>
			</tr>
			<tr>
				<td>Zip Code*</td>
				<td><form:input path="locationZipcode"/></td>
			</tr>
		</table>
		<br/>
		<input type="button" name="addImage" value="Add Photo..."/>
		<input type="submit"/>		
	</form:form>
</body>
</html>