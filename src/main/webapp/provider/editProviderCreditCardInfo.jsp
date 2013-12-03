<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
<title>Insert title here</title>
</head>
<body>
	<form:form name="editCreditCardForm" action="editCreditCard.request" modelAttribute="editCreditCard">
		<h2><spring:message code="common.field.editCreditCardPageTitle"/></h2>
		<br/>
		<br/>
		<table width="600">
			<tr>
				<td width="300"><label class="boldText"><spring:message code="common.field.firstNameOnCreditCard"/></label><br/><form:input path="cardHolderFirstName" size="35"/></td>
				<td width="300"><Label class="boldText"><spring:message code="common.field.lastNameOnCreditCard"/></Label><br/><form:input path="cardHolderLastName" size="35"/></td>
			</tr>
			<tr>
				<td width="225"><label class="boldText"><spring:message code="common.field.creditCardType"/></label><br/><form:select path="creditCardType" items="${creditCardTypesMap}"></form:select></td>
			</tr>
			<tr>
				<td width="275"><label class="boldText"><spring:message code="common.field.creditCardAccountNumber"/></label><br/><form:input path="accountNumber" size="20"/></td>
				<td width="225"><label class="boldText"><spring:message code="common.field.cvvNumber"/></label><br/><form:input path="cvvNumber" size="3" maxlength="4"/></td>
			</tr>
			<tr>
				<td><label class="boldText"><spring:message code="common.field.expirationMonth"/><br/><spring:message code="common.field.MMDateFormat"/></label><br/><form:input path="expirationMonth" size="2" maxlength="2"/></td>
				<td><label class="boldText"><spring:message code="common.field.expirationYear"/><br/><spring:message code="common.field.YYYYDateFormat"/></label><br/><form:input path="expirationYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><form:checkbox path="primaryCreditCard"/><label class="boldText"><spring:message code="common.field.primaryCreditCard"/></label></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.address"/></label><br/><form:input path="billingAddress" size="35" name="newBillingAddress"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.address2Optional"/></label><br/><form:input path="billingAddress2" size="20" name="newBillingAddress2"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.city"/></label><br/><form:input path="billingCity" size="35" name="newBillingCity"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.state"/></label><br/><form:select path="billingState" items="${stateSelectList}" name="newBillingState"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.zipcode"/></label><br/><form:input path="billingZipcode" size="5" name="newBillingZipcode"/></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Update Credit Card"/>
	</form:form>
</body>
</html>