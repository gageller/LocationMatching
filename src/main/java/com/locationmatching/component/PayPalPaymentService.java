package com.locationmatching.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;

public class PayPalPaymentService implements PaymentService {

	@Override
	public String processPayment(CreditCardImpl primaryCreditCard) {
		try {
			Map<String, String> sdkConfig = new HashMap<String, String>();
			String accessToken;
			
			sdkConfig.put("mode", "sandbox");

			accessToken = new OAuthTokenCredential("EBWKjlELKMYqRNQ6sYvFo64FtaRLRR5BdHEESmha49TM", "EO422dn3gQLgDbuwqTjzrFgFtaRLRR5BdHEESmha49TM", sdkConfig).getAccessToken();
			
			APIContext apiContext = new APIContext(accessToken);
			Address billingAddress = new Address();
			CreditCard creditCard = new CreditCard();
			Details details = new Details();
			Amount amount = new Amount();
			Transaction transaction = new Transaction();
			List<Transaction>transactions = new ArrayList<Transaction>();
			FundingInstrument fundingInstrument = new FundingInstrument();
			List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
			Payer payer = new Payer();
			Payment payment = new Payment();
			
			apiContext.setConfigurationMap(sdkConfig);
			
			billingAddress.setCity(primaryCreditCard.getBillingCity());
			billingAddress.setState("CA");
			billingAddress.setLine1(primaryCreditCard.getBillingAddress());
			billingAddress.setLine2(primaryCreditCard.getBillingAddress2());
			billingAddress.setPostalCode(primaryCreditCard.getBillingZipcode());
			billingAddress.setCountryCode("US");
			
			creditCard.setBillingAddress(billingAddress);
			creditCard.setExpireMonth(12);
			creditCard.setExpireYear(2012);
			creditCard.setNumber("4094574594958608");
			creditCard.setCvv2("874");
			creditCard.setFirstName("Gregory");
			creditCard.setLastName("Geller");
			creditCard.setType("visa");
			
			fundingInstrument.setCreditCard(creditCard);
			fundingInstruments.add(fundingInstrument);
			
			payer.setFundingInstruments(fundingInstruments);
			payer.setPaymentMethod("credit_card");
			
			details.setFee("0.00");
			details.setShipping("0.00");
			details.setTax("0.00");
			details.setSubtotal("1.99");
			
			amount.setCurrency("USD");
			amount.setTotal("1.99");
			amount.setDetails(details);
			
			transaction.setDescription("Payment for photo");
			transaction.setAmount(amount);
			
			transactions.add(transaction);
			
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			payment.setIntent("sale");
			
			Payment createdPayment = null;
			try {
				 createdPayment = payment.create(apiContext);
			}
			catch(PayPalRESTException ex) {
				Throwable throwing = ex.getCause();
				System.out.println(createdPayment);
				String response = Payment.getLastResponse();
				System.out.println(response);
			}
			
			String url = "https://api.sandbox.paypal.com/v1/";
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "Success";
	}
}
