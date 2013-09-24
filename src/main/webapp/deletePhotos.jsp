<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<script type="text/javascript">
	<!--
	function verifyDeletes() {
		// Check to see if at least one check box is checked
		var isAtLeastOneChecked = false;
		var deleteCheckBoxes = document.getElementsByName("deleteCheck")
		var numberOfCheckBoxes = deleteCheckBoxes.length;
		for(index=0; index < numberOfCheckBoxes; index++) {
			if(deleteCheckBoxes[index].checked == true) {
				isAtLeastOneChecked = true;
				break;
			}
		}
		if(isAtLeastOneChecked == true) {
			return confirm("Are you sure you want to delete the selected photos?");
		}
		else {
			alert("You must select at least one photo to delete.")
		}
		return false;
	}
	-->
</script>
</head>
<body>
	<h2>Delete Photos</h2>
	<label class="successMessage">Select Photos to Delete</label>
	<form:form name="deletePhotosForm" action="deletePhotos.request" method="POST" modelAttribute="location">
		<table cellspacing="10px" width="100%">
			<tr>
			<c:forEach items="${location.locationImages}" var="image" varStatus="iterationStatus">
	 			<c:if test="${image.hidden == false}">
							<td><img src="${image.relativeUrlPath}" height="250" width="330"/><br/>Delete <input type="checkbox" value="${image.id}" id="deleteCheckBoxes" name="deleteCheck"/></td>
 	 				<c:if test="${iterationStatus.count % 3 == 0}">
	 					<!-- Only three photos per row -->
	 					</tr>
	 					<tr>
	 				</c:if>
 				</c:if>
			</c:forEach>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Delete Photos" onClick="return verifyDeletes()"/>
	</form:form>
</body>
</html>