<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Location Photos</title>
</head>
<h2>Location Photos</h2>
<body>
	<h2>${alertLocation.locationName}</h2>
	<!-- Location Info -->
	<table width="750">
		<tr>
			<td colspan="3"><label class="boldText fontSize18">Location Name: </label><label class="fontSize18">${alertLocation.locationName}</label></td>
		</tr>
		<tr>
			<td width="350"><label class="boldText fontSize18">Address</label></td>
			<td width="300"><label class="boldText fontSize18">Address 2</label></td>
			<td width="50"></td>
		</tr>
		<tr>
			<td width="350"><label class="fontSize18">${alertLocation.locationAddress}</label></td>
			<td width="300"><label class="fontSize18">${alertLocation.locationAddress2}</label></td>
			<td width="100"></td>
		</tr>
		<tr>
			<td width="350"><label class="boldText fontSize18">City</label></td>
			<td width="300"><label class="boldText fontSize18">State</label></td>
			<td width="100"><label class="boldText fontSize18">Zip Code</label></td>
		</tr>
		<tr>
			<td width="350"><label class="fontSize18">${alertLocation.locationCity}</label></td>
			<td width="300"><label class="fontSize18">${alertLocation.locationState}</label></td>
			<td width="100"><label class="fontSize18">${alertLocation.locationZipcode}</label></td>
		</tr>
	</table>
	
	<!-- Photos -->
	<table cellspacing="10px" width="1000">
		<c:set var="itemCount" value="0"/>
			<tr>
		<c:forEach items="${alertLocation.locationImages}" var="image">
		 	<c:if test="${image.status.value.equals(\"APPROVED\") == true && image.isHidden == false}"> 
				<c:if test="${itemCount == 3}">
					</tr>
					<tr>
					<c:set var="itemCount" value="0"/>
				</c:if>
					<td><img alt="Location Photo" src="${image.relativeUrlPath}" height="250" width="330"/></td>
					
				<c:set var="itemCount" value="${itemCount + 1}"/>
	 		</c:if>
		</c:forEach>
		</tr>
	</table>
	<br/>
</body>
</html>