<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<script type="text/javascript">
	<!--
		function addPhoto() {
			document.forms("addLocation").action="gotoPhoto.request";
			document.forms("addLocation").submit();
		}
	-->
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a New Location</title>
</head>
<body> 
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
			<input type="button" value="Add Photos"  onclick="addPhoto()"/>

		<br/>
		<br/>

		<input type="submit" value="Finish Adding Location"/>		
		</form:form>
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>

</body>
</html>