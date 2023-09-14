package com.xceptance.loadtest.posters.actions.checkout;

import java.util.ArrayList;

import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;
import org.junit.Assert;
import java.util.List;

import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.checkout.OrderReviewPage;
import com.xceptance.loadtest.posters.models.pages.checkout.PaymentPage;
import com.xceptance.loadtest.posters.models.pages.checkout.ShippingAddressPage;

/**
 * Handles the payment page.
 * 
 * @author Xceptance Software Technologies
 */
public class CheckoutPayment extends PageAction<CheckoutPayment>
{
	private Account account;
	
	private CreditCard creditCard;
	
	public CheckoutPayment()
	{
		this(Context.get().data.getAccount().get(), Context.get().data.getAccount().get().getPrimaryCard());
	}
	
	public CheckoutPayment(Account account, CreditCard creditCard)
	{
		this.account = account;
		this.creditCard = creditCard;
	}
	
	@Override
	public void precheck()
	{
		super.precheck();
		
		Assert.assertTrue("Expected payment form", PaymentPage.paymentForm.exists());		
	}
	
	@Override
	protected void doExecute() throws Exception
	{
		// Fill payment form
				//PaymentPage.paymentForm.fillForm(account, creditCard);
				
				// Click continue button
				//loadPageByClick(PaymentPage.paymentForm.getContinueButton());
				String Csrf=ShippingAddressPage.shippingAddressForm.GetCsrf();
		        final List<NameValuePair> Paymentparms = new ArrayList<NameValuePair>();
		        
		        //Paymentparms.add(new NameValuePair("billing", "on"));
		        //Paymentparms.add(new NameValuePair("shipmentUUID", shipmetUId));
		        //Paymentparms.add(new NameValuePair("UUID", UId));
		        //Paymentparms.add(new NameValuePair("dwfrm_billing_contactInfoFields_email", account.email));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_firstName", account.firstname));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_lastName", account.lastname));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_address1", account.shippingAddress.addressLine1));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_address2", account.shippingAddress.addressLine2));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_city", account.shippingAddress.city));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_states_stateCode", account.shippingAddress.stateCode));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_postalCode", account.shippingAddress.zip));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_addressFields_country", account.shippingAddress.countryCode));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_contactInfoFields_phone", "+"+account.phone));
		        Paymentparms.add(new NameValuePair("localizedNewAddressTitle", "Add New Address"));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_paymentMethod", "CREDIT_CARD"));
		        Paymentparms.add(new NameValuePair("braintreeCardPaymentMethod", "CREDIT"));
		        Paymentparms.add(new NameValuePair("braintreeCreditCardList", "sessioncard"));
		        
//		        Paymentparms.add(new NameValuePair("dwfrm_billing_adyenPaymentFields_adyenStateData", account.PaymentStatedata));
//		        Paymentparms.add(new NameValuePair("csrf_token",Csrf));
//		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardNumber", "************4305"));
//		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardType", "visa"));
		        
		        //Paymentparms.add(new NameValuePair("dwfrm_billing_adyenPaymentFields_adyenStateData", creditCard.PaymentStatedata));
		        Paymentparms.add(new NameValuePair("csrf_token",Csrf));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardOwner", account.getFullName()));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardNumber", creditCard.number));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardType", creditCard.type));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_expirationYear", "30"));
		        Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_expirationMonth", "03"));
		        Paymentparms.add(new NameValuePair("braintreeIs3dSecureRequired", "false"));
		        
		        
		        
		        
		        //Paymentparms.add(new NameValuePair("adyenPaymentMethod", "Credit Card"));
		        //Paymentparms.add(new NameValuePair("brandCode", "scheme"));
		        HttpRequest req3 = new HttpRequest()
							.XHR().header("sec-fetch-dest", "empty").header("sec-fetch-mode", "cors").header("sec-fetch-site", "same-origin")
							.url("/on/demandware.store/Sites-fireMountainGems-Site/default/CheckoutServices-SubmitPayment")
		                   .POST()             
						    .postParams(Paymentparms);
		        WebResponse response3=req3.fire();

	}

	@Override
	protected void postValidate() throws Exception
	{
        Validator.validatePageSource();

        // Validate that it is the order review (place order) page
        OrderReviewPage.instance.validate();
	}
}