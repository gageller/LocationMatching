<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./images/lmStyle.css">
<title>Alerts</title>
<script type="text/javascript">
	<!--
	function showLocationPhotos(requestedIdNumber) {
		document.forms("showAlerts").requestedAlertId.value = requestedIdNumber;
		document.forms("showAlerts").submit();
	}
	-->
</script>
</head>
<body>
<h2>Alerts</h2>
<h4 class="unreadScoutAlert">Unread Alerts will appear in red.</h4>
	<form:form name="showAlerts" action="viewAlertLocationPhotos.request" method="POST" modelAttribute="locationScout">
		<input type="hidden" name="requestedAlertId" value=""/>
		<div valign="bottom">
		<c:forEach items="${locationScout.requestAlerts}" var="requestAlert">
			<table  style="border: 1px solid;"  width="650" <c:if test="${requestAlert.viewed == false}">class="unreadScoutAlert"</c:if>>
				<tr>
					<td class="tableCellNoBorder" width="225">City: ${requestAlert.location.locationCity}</td>
					<td width="275">State: ${requestAlert.location.locationState}</td>
					<td width="150">Zip Code: ${requestAlert.location.locationZipcode}</td>
				</tr>
				<tr class="tableCellNoBorder">
					<td><img src="${requestAlert.location.coverPhotoUrl}" width="100", height="75"/></td>
					<td>Location Provider Email Address: ${requestAlert.location.locationOwner.emailAddress}</td>
					<td><input type="button" value="View Location Photos" onClick='showLocationPhotos(${requestAlert.locationId})')/></td>
				</tr>
			</table>
			<br/>
		</c:forEach>
		</div>
	</form:form>
	<a href="./scoutNavigation.jsp">My Main Page</a>
	<br/>
	<a href="./index.jsp">Home</a>
	
</body>
</html>