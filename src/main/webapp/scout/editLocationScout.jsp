<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/lmStyle.css">
<title></title>
<script type="text/javascript">
	<!--
	function cancelBttnClicked() {
		document.forms("editScoutForm").action = "returnScoutMainPage.request";
		document.forms("editScoutForm").method = "GET";
		document.forms("editScoutForm").submit();
	}
	-->
</script>
</head>
<body>
	<h2>Edit Provider Information</h2>
	<form:form name="editScoutForm" action="editLocationScout.request" method="POST" modelAttribute="locationScout">
		<table>
			<tr>
				<td>First Name: </td>
				<td><form:input path="firstName"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><form:input path="lastName"/></td>
			</tr>
			<tr>
				<td>Email Address: </td>
				<td><form:input path="emailAddress"/></td>
			</tr>
			<tr>
				<td>Phone Number: </td>
				<td><form:input path="phoneNumber"/></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td><input type="submit" value="Update Information"/></td>
				<td><input type="button" value="Cancel" onClick="cancelBttnClicked()"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>