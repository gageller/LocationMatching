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
<script type="text/javascript">
	function deleteCreditCardClick(id) {
		var deleteCreditCard;
		
		deleteCreditCard = confirm("Are you sure you want to delete this Credit Card?");
		if(deleteCreditCard == true) {
			document.forms("manageCreditCardsForm").action = "deleteCreditCard.request";
			document.forms("manageCreditCardsForm").creditCardId.value = id;
			document.forms("manageCreditCardsForm").submit();
		}
	}
	
	function editCreditCardClick(id) {
		document.forms("manageCreditCardsForm").action = "setupEditCreditCard.request";
		document.forms("manageCreditCardsForm").creditCardId.value = id;
		document.forms("manageCreditCardsForm").submit();
	}
	
	function useDefaultAddress(useDefault) {
		var billingAddress, billingAddress2, billingCity, billingState, billingZipcode;
		
		billingAddress = document.getElementById("billingAddress");
		billingAddress2 = document.getElementById("billingAddress2");
		billingCity = document.getElementById("billingCity");
		billingState = document.getElementById("billingState");
		billingZipcode = document.getElementById("billingZipcode");
		
		if(useDefault == true) {
			// If using default, fill the fields with the default values.
			billingAddress.value = "${primaryCreditCard.billingAddress}";
			billingAddress2.value = "${primaryCreditCard.billingAddress2}";
			billingCity.value = "${primaryCreditCard.billingCity}";
			billingState.value = "${primaryCreditCard.billingState}";
			billingZipcode.value = "${primaryCreditCard.billingZipcode}";
		}
		else {
			// Selected new address so clear out the fields
			billingAddress.value = "";
			billingAddress2.value = "";
			billingCity.value = "";
			billingState.value = "";
			billingZipcode.value = "";
		}
	}
</script>
</head>
<body>
	<h2>Manage Your Credit Card Accounts</h2>
	<br/>
	<p class="errorMessage">${errorMessage}</p>
	<label class="boldText fontSize18">You may add a maximum of ${maxCreditCardsAllowed} credit cards for this account.</label>
	<br/>
	<br/>
	<label class="boldText fontSize18">Existing Credit Card Accounts</label>
	<form:form method="POST" modelAttribute="newCreditCard" name="manageCreditCardsForm" action="addCreditCard.request">
		<input type="hidden" name="creditCardId"/>
		<c:set var="hasCreditCards" scope="page" value="${locationProvider.creditCards.size()}"> </c:set>
		<c:if test="${locationProvider.creditCards.size() > 0}">
			<table width="1000">
				<c:forEach items="${locationProvider.creditCards}" var="creditCard">
					<tr>
						<td>
							<c:if test="${creditCard.primaryCreditCard == true}">
								<input type="radio" name="primaryCreditCard" value="creditCard.id" checked disabled/><label>Primary Credit Card</label>
							</c:if>
						</td>
						<!-- Display Last 5 Digits of Credit Card Number -->
						<td><label>Account Number</label><br/><label>xxxxxxxxxxxx${creditCard.decryptedAccountNumber.substring(creditCard.decryptedAccountNumber.length() - 5)}</label></td>
						<td><input type="button" name="editCard${creditCard.id}" value="Edit Credit Card" onClick="editCreditCardClick(${creditCard.id})">  <input type="button" name="deleteCard${creditCard.id}" value="Delete Credit Card" onClick="deleteCreditCardClick(${creditCard.id})"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<br/>
		<hr class="lineWidth2" />
		<br/>
		<label class="boldText fontSize18">Add New Credit Card</label>
		<table width="600">
			<tr>
				<td width="300" colspan="2"><label><spring:message code="common.field.firstNameOnCreditCard"/></label><br/><form:input path="cardHolderFirstName" size="35"/></td>
				<td width="400"><label><spring:message code="common.field.lastNameOnCreditCard"/></label><br/><form:input path="cardHolderLastName" size="35"/></td>
			</tr>
			<tr>
				<td width="225" colspan="3"><label><spring:message code="common.field.creditCardType"/></label><br/><form:select path="creditCardType" items="${creditCardTypesMap}"></form:select></td>
			</tr>
			<tr>
				<td width="275"><label><spring:message code="common.field.creditCardAccountNumber"/></label><br/><form:input path="accountNumber" size="20"/></td>
				<td width="225"><label><spring:message code="common.field.cvvNumber"/></label><br/><form:input path="cvvNumber" size="3" maxlength="4"/></td>
			</tr>
			<tr>
				<td width="275"><label><spring:message code="common.field.expirationMonth"/><br/><spring:message code="common.field.MMDateFormat"/></label><br/><form:input path="expirationMonth" size="2" maxlength="2"/>
				<td><label><spring:message code="common.field.expirationYear"/><br/><spring:message code="common.field.YYYYDateFormat"/></label><br/><form:input path="expirationYear" size="4" maxlength="4"/>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
		<table>
			<tr>
				<td width="275"><input type="radio" name="addressGroupRadio" value="currentAddress" onClick="useDefaultAddress(true)"
					<c:choose>
						<c:when test="${hasCreditCards > 0}">
							checked
						</c:when>
						<c:otherwise>
							disabled  
						</c:otherwise>
					</c:choose>
				 /><label><spring:message code="common.field.useDefaultBillingAddress"/></label></td>
				<td width="225"><input type="radio" name="addressGroupRadio" value="newAddress" onClick="useDefaultAddress(false)"
					<c:if test="${hasCreditCards == 0}"> checked</c:if>
				/><label><spring:message code="common.field.addBillingAddress"/></label></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.address"/></label><br/><form:input path="billingAddress" size="35"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.address2Optional"/></label><br/><form:input path="billingAddress2" size="20"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.city"/></label><br/><form:input path="billingCity" size="35"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.state"/></label><br/><form:select path="billingState" items="${stateSelectList}"/></td>
			</tr>
			<tr>
				<td colspan="2"><label class="boldText"><spring:message code="common.field.zipcode"/></label><br/><form:input path="billingZipcode" size="5"/></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Add Credit Card" <c:if test="${locationProvider.creditCards.size() == 3}">disabled</c:if> />
		
	</form:form>
</body>
</html>