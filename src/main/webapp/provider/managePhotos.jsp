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
			document.forms("managePhotos").action="returnFromFileUpload.request";
			document.forms("managePhotos").submit();
		}
	-->
</script>
<title>Manage Existing Photos</title>
</head>
<body>
	<h2>Manage Your Existing Photos</h2>
	<form:form name="managePhotos" method="POST" action="manageExistingPhotos.request" modelAttribute="location">

			<label class="boldText fontSize18">Number of Remaining Free Photos is ${numberOfRemainingFreePhotos}</label>
			<br/>
			<br/>
<!-- 			<br/>
			<br/>
			<table>
				<tr>
					<td><input name="multipartFile" type="file" size="75"/></td>
					<td width="10">&nbsp;</td>
					<td><input type="submit" value="Add Photo"/></td>
				</tr>
			</table>
			<input type="hidden" name="nextPage" value="managePhotosPage"/>
			<br/>
			<p class="errorMessage">${errorMessage}</p>
			<p class="successMessage">${fileuploadSuccessMessage}</p>
			<br/>
			<br/>
-->			
		<!-- Only show label if there are 1 or more free photos -->
		<c:if test="${location.numberOfFreePhotos > 0}">
			<label class="boldText fontSize18">Free Photos</label>
		</c:if>

			<table cellspacing="10px" width="100%">
				<tr>
				
			<!-- Free Photos -->
			<c:forEach items="${location.locationImages}" var="image" varStatus="iterationFreeStatus">
				<c:if test="${image.status != PhotoStatus.DELETED && image.status != PhotoStatus.DECLINED && image.photoPlanType.value.equals(\"FREE_PHOTO\") == true}">
								<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/><br/>
										<input type="radio" name="mainPhotoRadio" <c:if test="${image.isCoverPhoto() == true}">checked </c:if>value="${image.id}"/><label>Main Photo</label><br/>
								
									<input type="radio" name="imageHideUnhideRadio${image.id}" value="false" <c:if test="${image.hidden == false}">checked</c:if>><label>Show Photo</label>
									&nbsp;&nbsp;
									<input type="radio" name="imageHideUnhideRadio${image.id}" value="true" <c:if test="${image.hidden == true}">checked</c:if>><label>Hide Photo</label> 
								</td>
 	 				<c:if test="${iterationFreeStatus.count % 3 == 0}">
	 					<!-- Only three photos per row -->
	 					</tr>
	 					<tr>
	 				</c:if>
				</c:if>
			</c:forEach>
			</tr>
		</table>
		<br/>
		<br/>
		<!-- Only show label if there are 1 or more paid photos -->
		<c:if test="${location.numberOfPaidPhotos > 0}">
			<label class="boldText fontSize18">Paid Photos</label>
		</c:if>
		<table>
			<tr>
			<!-- Paid Photos -->
				<c:forEach items="${location.locationImages}" var="image" varStatus="iterationPaidStatus">
				<c:if test="${image.status != PhotoStatus.DELETED && image.status != PhotoStatus.DECLINED && image.photoPlanType.value.equals(\"PAID_PHOTO\")}">
								<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/><br/>
									<input type="radio" name="mainPhotoRadio" <c:if test="${image.isCoverPhoto() == true}">checked </c:if>value="${image.id}"/>Main Photo<br/>
								
									<input type="radio" name="imageHideUnhideRadio${image.id}" value="false" <c:if test="${image.hidden == false}">checked</c:if>><label>Show Photo</label>
									&nbsp;&nbsp;
									<input type="radio" name="imageHideUnhideRadio${image.id}" value="true" <c:if test="${image.hidden == true}">checked</c:if>><label>Hide Photo</label> 
								</td>
 	 				<c:if test="${iterationPaidStatus.count % 3 == 0}">
	 					<!-- Only three photos per row -->
	 					</tr>
	 					<tr>
	 				</c:if>
				</c:if>
			</c:forEach>
			</tr>
		</table>
		<br/>
	<!-- 	<input type="button" onClick="saveImages()" value="Save Photos for this Location"/> -->
	
	<input type="submit" value="Update Photos" <c:if test="${location.numberOfFreePhotos == 5}">disabled='true'</c:if>/>
	</form:form>
</body>
</html>