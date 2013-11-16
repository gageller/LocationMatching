<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
	<title>Edit Location</title>
	<script type="text/javascript">
		<!--
		function bttnClick(action) {
			document.forms("editLocationForm").action = action;
			document.forms("editLocationForm").method="GET";
			document.forms("editLocationForm").submit();
		}
		-->
	</script>
</head>
<body>
	<h2><spring:message code="editLocation.pageHeader"/></h2>
	<form:form name="editLocationForm" action="editLocation.request" method="POST" modelAttribute="location">

		<table>
			<tr>
				<td><spring:message code="common.field.locationName"/></td>
				<td><form:input path="locationName"/></td>
			</tr>
			<tr>
				<td><spring:message code="common.field.address"/></td>
				<td><form:input path="locationAddress"/></td>
			</tr>
			<tr>
				<td><spring:message code="common.field.address2Optional"/></td>
				<td><form:input path="locationAddress2"/></td>
			</tr>
			<tr>
				<td><spring:message code="common.field.city"/></td>
				<td><form:input path="locationCity"/></td>
			</tr>
			<tr>
				<td><spring:message code="common.field.state"/></td>
				<td><form:select path="locationState" items="${stateSelectList}"/></td>
			</tr>
			<tr>
				<td><spring:message code="common.field.zipcode"/></td>
				<td><form:input path="locationZipcode"/></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" name="addPhoto" value="<spring:message code="editLocation.addPhotoButtonText"/>" onClick="bttnClick('setupAddPhoto.request')"/></td>
- 				<td><input type="button" name="managePhotos" value="<spring:message code="editLocation.managePhotosButtonText"/>" onClick="bttnClick('setupManageExistingPhotos.request')"/></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td><input type="submit" value="<spring:message code="editLocation.submitButtonText"/>"/></td>
			</tr>
		</table>	
	</form:form>
<!-- 
<br/>
<a href="./providerNavigation.jsp">My Main Page</a>
<br/>
<a href="./index.jsp">Home</a>
-->
</body>
</html>