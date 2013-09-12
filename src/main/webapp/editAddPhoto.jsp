<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	<!--
		function saveImages() {
			document.forms("addPhoto").action="returnFromFileUpload.request";
			document.forms("addPhoto").nextPage.value="editLocation";
			document.forms("addPhoto").submit();
		}
	-->
</script>
<title>Add Photo</title>
</head>
<body>
	<h2>Add Photo</h2>
	<form:form name="addPhoto" method="POST" action="uploadFile.request" enctype="multipart/form-data" modelAttribute="editLocation">
			<input name="multipartFile" type="file" size="75"/>
			<input type="submit" value="Add Photo"/>
			<input type="hidden" name="nextPage" value="editAddPhoto"/>
			
			<p class="errorMessage">${errorMessage}</p>
			<p class="successMessage">${fileuploadSuccessMessage}</p>
			<table cellspacing="10px" width="1000">
			<c:set var="itemCount" value="0"/>
				<tr>
			<c:forEach items="${editLocation.locationImages}" var="image">
				<c:if test="${itemCount == 3}">
					</tr>
					<tr>
					<c:set var="itemCount" value="0"/>
				</c:if>
					<td>
					<table>
						<tr>
							<td colspan="2"><img alt="Location Photo" src="${image.relativeUrlPath}" height="300" width="400"/></td>
						</tr>
						<tr>
							<td><input type="radio" name="mainPhotoRadio" <c:if test="${image.isCoverPhoto() == true}">checked </c:if>value="${image.id}"/> Main Photo </td>
						
							<td>${image.photoPlanType}</td>
						</tr>
					</table>
					</td>
					
				<c:set var="itemCount" value="${itemCount + 1}"/>
			</c:forEach>
			</tr>
		</table>
		<br/>
		<input type="button" onClick="saveImages()" value="Finish Editing Location"/>
		<br/>
		<a href="./index.jsp">Home</a>
		
	</form:form>
</body>
</html>