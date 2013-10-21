<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
</head>
<body>
	<h2>Manage Your Credit Card Accounts</h2>
	<br/>
	<p class="errorMessage">${errorMessage}</p>
	<label class="boldText fontSize18">You may add a maximum of ${maxCreditCardsAllowed} credit cards for this account.</label>
	<br/>
	<br/>
	<label class="boldText fontSize18">Existing Credit Card Accounts</label>
	<form:form method="POST" modelAttribute="locationProvider" name="manageCreditCardsForm" action="addCreditCard.request">
		<c:if test="${locationProvider.creditCards.size() > 0}">
			<table width="1000">
				<c:forEach items="${locationProvider.creditCards}" var="creditCard">
					<tr>
						<td><input type="radio" name="primaryCreditCard" value="creditCard.id"/><label>Primary Credit Card</label>
						<!-- Display Last 4 Digits of Credit Card Number -->
						<td><label>xxxxxxxxxxxx${creditCard.accountNumber.substring(creditCard.length() - 5)}</label></td>
						<td><input type="button" name="deleteCard${creditCard.id}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<br/>
		<label class="boldText fontSize18">Add New Credit Card</label>
		<table width="500">
			<tr>
				<td width="275"><label>Name as it appears on the credit card</label><br/><form:input path="${creditCard.cardHolderName}" size="35"/></td>
				<td width="225"><label>Credit Card Type</label><br/><form:select path="${newCreditCard.creditCardType}" items="${creditCardTypesMap}"></form:select></td>
			</tr>
			<tr>
				<td width="275"><label>Account Number</label><br/><form:input path="${newCreditCard.accountNumber}" size="20"/></td>
				<td width="225"><label>CVV Number</label><br/><form:input path="${newCreditCard.cvvNumber}" size="3"/></td>
			</tr>
			<tr>
				<td colspan="2"><label>Expiration Date (MM/DD/YYYY)</label><br/><form:input path="${newCreditCard.expirationDate}"/>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td width="275"><input type="radio" name="addressGroupRadio" value="currentAddress"/><label>Use default billing address</label></td>
				<td width="225"><input type="radio" name="addressGroupRadio" value="newAddress"/><label>Add new billing address</label></td>
			</tr>
			<tr>
				<td colspan="2"><label>Street Address</label><br/><form:input path="${newCreditCard.billingAddress}" size="35" name="newBillingAddress"/></td>
			</tr>
			<tr>
				<td colspan="2"><label>Street Address 2(Optional)</label><br/><form:input path="${newCreditCard.billingAddress2}" size="20" name="newBillingAddress2"/></td>
			</tr>
			<tr>
				<td colspan="2"><label>City</label><br/><form:input path="${newCreditCard.billingCity}" size="35" name="newBillingCity"/></td>
			</tr>
			<tr>
				<td colspan="2"><label>State</label><br/><form:select path="${newCreditCard.billingState}" items="${stateSelectList}" name="newBillingState"/></td>
			</tr>
			<tr>
				<td colspan="2"><label>Zip Code</label><br/><form:input path="${newCreditCard.billingZipcode}" size="5" name="newBillingZipcode"/></td>
			</tr>
		</table>
		<br/>
		<input type="submit" value="Add Credit Card"/>
	</form:form>
</body>
</html>