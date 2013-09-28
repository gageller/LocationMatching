<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<script type="text/javascript">
		<!--
		function editLocationRequestBttnClick(editIdValue) {
			document.forms("editList").editId.value = editIdValue;
			document.forms("editList").submit();
		}
		-->
	</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Edit a Location Request</title>
</head>
<body>
	<h2>Select a Location Request to Edit</h2>
	<form name="editList" action="setupEditLocationRequest.request" method="POST" commandName="locationScout">
		<input type="hidden" name="editId"/>
		<c:forEach items="${locationScout.locationRequests}" var="request">
			<table border="1" width="100%">
				<tr>
					<td colspan="2"><label class="boldText">Location Name:</label> ${request.locationRequestName}</td>
					<td><label class="boldText">Location Type:</label>${request.locationType}</td>
					<td><input type="button" value="Edit Location Request..." onClick='editLocationRequestBttnClick("${request.id}")'/></td>						
				</tr>
				<tr>
					<td width="25%"><label class="boldText">City:</label></td>
					<td width="25%"><label class="boldText">County:</label></td>
					<td width="35%"><label class="boldText">State:</label></td>
					<td width="15%"><label class="boldText">Zip Code:</label></td>
				</tr>
				<tr>
					<td>${request.locationRequestCity}</td>				
					<td>${request.locationRequestCounty}</td>												
					<td>${request.locationRequestState}</td>
					<td>${request.locationRequestZipcode}</td>					
				</tr>
				<tr>
					<td colspan="2"><label class="boldText">Location Description</label></td>
					<td colspan="2"><label class="boldText">Project Notes</label></td>
				</tr>
				<tr>
					<td colspan="2">${request.locationDescription}</td>
					<td colspan="2">${request.projectNotes}</td>
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