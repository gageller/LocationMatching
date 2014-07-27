<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit Location</title>
	<script type="text/javascript">
		<!--
		function bttnClick(action) {
			document.forms("editLocationRequestForm").action = action;
			document.forms("editLocationRequestForm").method="GET";
			document.forms("editLocationRequestForm").submit();
		}
		-->
	</script>
</head>
<body>
	<h2>Edit this Location</h2>
	<form:form name="editLocationRequestForm" action="editLocationRequest.request" method="POST" modelAttribute="locationRequest">

		<table border=1 width="100%">
			<tr>
				<td width="30%">Location Request Name*</td>
				<td width="70%"><form:input path="locationRequestName"/></td>
			</tr>
			<tr>
				<td>Location Type*</td>
 				<td><form:select path="locationType" items="${locationTypeList}"/></td>
			</tr>
			<tr>
				<td>Location City</td>
				<td><form:input path="locationRequestCity"/></td>
			</tr>
			<tr>
				<td>Location State*</td>
 				<td><form:select path="locationRequestState" items="${stateSelectList}"/></td> 
			</tr>
			<tr>
				<td>Location Zip Code</td>
				<td><form:input path="locationRequestZipcode"/></td>
			</tr>
			<tr>
				<td>County where Location resides*</td>
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
				<td><form:textarea rows="4" cols="80" path="locationDescription"/></td>
			</tr>
			<tr>
				<td>Project Notes*</td>
				<td><form:textarea rows="4" cols="80" path="projectNotes" /></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Update Location Request Information"/>
	</form:form>
<!-- 
<br/>
<a href="./providerNavigation.jsp">My Main Page</a>
<br/>
<a href="./index.jsp">Home</a>
-->
</body>
</html>