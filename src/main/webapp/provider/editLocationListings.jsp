<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<script type="text/javascript">
		<!--
		function editLocationBttnClick(editIdValue) {
			document.forms("editList").editId.value = editIdValue;
			document.forms("editList").submit();
		}
		-->
	</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit a Location</title>
</head>
<body>
	<h2>Select a Location to Edit</h2>
	<form name="editList" action="editLocationSetup.request" method="POST" commandName="locationProvider">
		<input type="hidden" name="editId"/>
		<c:forEach items="${locationProvider.providerLocations}" var="location">
			<table border="1" width="600">
				<tr>
					<td colspan="4"><b>Location Name:</b> ${location.locationName}</td>
					<td><input type="button" value="Edit Location..." onClick='editLocationBttnClick("${location.id}")'/></td>						
				</tr>
				<tr>
					<td><b>Address:</b></td>
					<td><b>Address 2:</b></td>
					<td><b>City:</b></td>
					<td><b>State:</b></td>
					<td><b>Zip Code:</b></td>
				</tr>
				<tr>
					<td>${location.locationAddress}</td>				
					<td>${location.locationAddress2}</td>
					<td>${location.locationCity}</td>												
					<td>${location.locationState.stateName}</td>
					<td>${location.locationZipcode}</td>					
				</tr>
			</table>
			<br/>
		</c:forEach>
	</form>
<!-- 
	<br/>
	<a href="./providerNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>	
-->
</body>
</html>