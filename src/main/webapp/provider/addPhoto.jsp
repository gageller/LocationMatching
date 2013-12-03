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
			document.forms("addPhoto").addingPhoto.value = "true";
			document.forms("addPhoto").action="returnFromFileUpload.request";
			document.forms("addPhoto").submit();
		}
	/*
	if(document.addEventListener) {
		document.addEventListener('click', function(e){
			window.event=e;
		}, true);
	}
	function linkClicked() {
		var src = event.target || event.srcElement;
		alert(src.href);
	}
	*/
		var processPhotos = ${processPhotos};
	
		window.onbeforeunload = function() {
			if(document.forms("addPhoto").addingPhoto.value == "false") {
				if(processPhotos == true) {
					var savePhotos = confirm("Your newly uploaded photos still need to be processed.\r\nIf you want to finish processing the photos, select \"OK\" otherwise select \"Cancel\".")
					if(savePhotos == true) {
						document.forms("addPhoto").action="addPhotoPageReturn.request";
						document.forms("addPhoto").submit();
					}
				}
			}
		}
	-->
</script>
<title>Add Photo</title>
</head>
<body>
	<h2>Add Photo</h2>
	<form:form name="addPhoto" method="POST" action="uploadFile.request" enctype="multipart/form-data">
			<input name="multipartFile" type="file" size="75"/>
			<input type="submit" value="Add Photo"/>
			<input type="hidden" name="nextPage" value="addPhotoPage"/>
			<input type="hidden" name="addingPhoto" value="false"/>
			<br/>
			<br/>
			<label class="boldText fontSize18">Number of Remaining Free Photos is ${numberOfRemainingFreePhotos}</label>
			<br/>
			<br/>
			<p class="errorMessage">${errorMessage}</p>
			<p class="successMessage">${fileuploadSuccessMessage}</p>
			<p class="errorMessage">${payForPhotosMessage}</p>
			<table cellspacing="10px" width="100%">
				<tr>
			<c:forEach items="${location.locationImages}" var="image" varStatus="iterationStatus">
				<c:if test="${image.status != PhotoStatus.DELETED && image.status != PhotoStatus.DECLINED}">
								<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/><br/>
								<input type="radio" name="mainPhotoRadio" <c:if test="${image.isCoverPhoto() == true}">checked </c:if>value="${image.id}"/> Main Photo&nbsp;&nbsp;
							
								${image.photoPlanType}</td>
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
		<input type="button" onClick="saveImages()" value="Save Photos for this Location"/>
	</form:form>
</body>
</html>