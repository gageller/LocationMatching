<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register for Location Scout Account</title>
</head>
<body>
		<form:form action="createNewScout.request" method="POST" commandName="locationScout">
			<h2>Register for new Location Scout Account</h2>
			<table>
				<tr>
					<td>User Name*</td>
					<td><form:input path="userName" value="jbgeller"/>
				</tr>
				<tr>
					<td>First Name*</td>
					<td><form:input path="firstName" value="Jeff"/></td>
				</tr>
				<tr>
					<td>Last Name*</td>
					<td><form:input path="lastName" value="Scout"/>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td><form:input path="phoneNumber" value="8185127426"/></td>
				<tr>
					<td>Email Address*</td>
					<td><form:input path="emailAddress" value="jgeller@jgeller.com"/></td>
				</tr>
				<tr>
					<td>Confirm Email Address*</td>
					<td><input type="text" name="confirmEmailAddress" /></td>
				</tr>

				<tr>
					<td>Password*</td>
					<td><form:password path="password" value="new"/></td>
				</tr>
				<tr>
					<td>Confirm Password*</td>
					<td><input type="password" name="confirmPassword" /></td>
				</tr>
			
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>*Required</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="submit" value="Create Account"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><a href="./index.jsp">Home</a></td>
				</tr>
			</table>
		</form:form>

</body>
</html>