<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td><label class="boldText">Email Address:</label></td>
			<td><label>${adminUser.emailAddress}</label></td>
		</tr>
		<tr>
			<td><label class="boldText">Phone Number:</label></td>
			<td><label>${adminUser.phoneNumber}</label></td>
		</tr>
		<tr>
			<td><label class="boldText">User Level:</label></td>
			<td><label>${adminUser.userLevel}</label></td>
		</tr>
	</table>

</body>
</html>