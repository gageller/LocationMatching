<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Location Request</title>
</head>

<body>
<script>
	<!--
		function processPhotosBttnClick(){
			document.forms("photoApprovalForm").action = "approveDeclineSkipPhotos.request";
			document.forms("photoApprovalForm").submit();
		}
	-->
</script>

	<form:form name="photoApprovalForm" modelAttribute="unapprovedPhotos" action="processProviderSearchUnapprovedPhotos.request" method="POST">
		<h2>Search For Photos Needing Approval By</h2>
		<table width="700">
			<tr>
				<td width="350"><input type="radio" name="searchByMethod" value="allPhotos"/><label>All Photos Needing Approval</label></td>
				<td width="350"><input type="radio" name="searchByMethod" value="byUserName" checked/><label>User Name </label><input type="text" name="userName"/></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Search For Photos"/>
		<br/>
		<br/>
		<c:forEach items="${unapprovedPhotosByUser}" var="user">
			<table width="1000">
				<tbody>
					<tr>
						<td colspan="3"><label class="boldText fontSize20">User Name: </label><label class="fontSize20">${user.userName}</label></td>
					</tr>
					<c:forEach items="${user.providerLocations}" var="location">
						<tr>
							<td colspan="3"><label class="boldText fontSize18">Location Name: </label><label class="fontSize18">${location.locationName}</label></td>
						</tr>
						<tr>
						<c:forEach items="${location.locationImages}" var="image" varStatus="iterationStatus">
							<c:if test="${image.status.value.equals(\"NOT_REVIEWED\") == true or image.status.value.equals(\"SKIPPED_ON_REVIEW\") == true}">
											<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/><br/>
												<input type="radio" name="approvePhotoRadio${image.id}" value="APPROVED"/><label>Approve </label>
												<input type="radio" name="approvePhotoRadio${image.id}" value="DECLINED"/><label>Decline  </label>
												<input type="radio" name="approvePhotoRadio${image.id}" value="SKIPPED_ON_REVIEW" checked/><label>Skip</label>	
												<br/><label>Notes:</label>
											<br/>
												<textarea rows="3" cols="37" name="adminNotes${image.id}"></textarea>
											</td>
			 	 				<c:if test="${iterationStatus.count % 3 == 0}">
				 					<!-- Only three photos per row -->
				 					</tr>
				 					<tr>
				 				</c:if>
							</c:if>
						</c:forEach>
						</tr>
						<tr>
							<!-- Add a blank row to separate locations -->
							<td height="5" colspan="3">&nbsp;</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<br/>
		</c:forEach>
		<br/>
		<input type="button" value="Process Photos" onClick="processPhotosBttnClick()"/>
	</form:form>
</body>
</html>