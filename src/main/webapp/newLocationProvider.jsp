<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register for Location Provider Account</title>
</head>
	<body>
		<form:form action="createNewProvider.request" method="POST" modelAttribute="newLocationProvider">
			<h2>Register for new Location Provider Account</h2>
			<table>
				<tr>
					<td>User Name*</td>
					<td><form:input path="userName"/>
				</tr>
				<tr>
					<td>First Name*</td>
					<td><form:input path="firstName"/></td>
				</tr>
				<tr>
					<td>Last Name*</td>
					<td><form:input path="lastName"/>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td><form:input path="phoneNumber"/></td>
				<tr>
					<td>Email Address*</td>
					<td><input type="text" name="emailAddress"/></td>
				</tr>
				<tr>
					<td>Confirm Email Address*</td>
					<td><input type="text" name="confirmEmailAddress"/></td>
				</tr>

				<tr>
					<td>Password*</td>
					<td><form:password path="password"/></td>
				</tr>
				<tr>
					<td>Confirm Password*</td>
					<td><input type="password" name="confirmPassword"/></td>
				</tr>
			
				<tr colspan=2>
					<td>&nbsp;</td>
				</tr>
				<tr colspan=2>
					<td>*Required</td>
				</tr>
				<tr colspan=2>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="submit"/></td>
				</tr>
				<tr colspan=2>
					<td>&nbsp;</td>
				</tr>
			</table>
		</form:form>
	</body>
</html>