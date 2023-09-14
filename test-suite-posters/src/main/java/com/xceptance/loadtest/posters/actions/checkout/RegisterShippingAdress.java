package com.xceptance.loadtest.posters.actions.checkout;

import java.util.ArrayList;
import java.util.List;

import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;
import org.json.JSONObject;
import org.junit.Assert;

//import com.gargoylesoftware.htmlunit.WebResponse;
//import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.configuration.EnumConfigList;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.checkout.ShippingAddressPage;
import com.xceptance.xlt.api.util.XltRandom;

public class RegisterShippingAdress extends PageAction<RegisterShippingAdress>
{
	private Account account;
	private CreditCard creditCard;
	private int Cardindex;
	
	public RegisterShippingAdress()
	{
		this(Context.get().data.getAccount().get(),Context.get().data.getAccount().get().creditCards);
	}
	
	public RegisterShippingAdress(Account account,EnumConfigList<CreditCard> cards)
	{
		this.account = account;
		Cardindex = XltRandom.nextInt(0, cards.size() - 1);
		this.creditCard=cards.get(Cardindex);
		
	}
	
	@Override
	public void precheck()
	{
		//this.getWebClient().getOptions().setJavaScriptEnabled(true);
		super.precheck();

		// Validate that we have the shipping address form
		Assert.assertTrue("Expected shipping address form", ShippingAddressPage.shippingAddressForm.exists());
	}
	
	@Override
	protected void doExecute() throws Exception
	{
				String ShipmentUuid=ShippingAddressPage.shippingAddressForm.GetShipmentUUid();
				 String Csrf=ShippingAddressPage.shippingAddressForm.GetCsrf();
                     // Assert.assertEquals("Responce expected",new JSONObject(response.getContentAsString()));
					 final List<NameValuePair> parms = new ArrayList<NameValuePair>();
					 parms.add(new NameValuePair("originalShipmentUUID", ShipmentUuid));
					 parms.add(new NameValuePair("shipmentUUID", ShipmentUuid));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_companyName", account.companyname));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_firstName", account.firstname));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_lastName", account.lastname));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_phone", "+"+account.phone));
					 //parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_title", account.addressTitile));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_address1", account.shippingAddress.addressLine1));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_address2", account.shippingAddress.addressLine2));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_city", account.shippingAddress.city));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_states_stateCode", account.shippingAddress.stateCode));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_postalCode", account.shippingAddress.zip));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_country", account.shippingAddress.countryCode));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_phone", "+"+account.phone));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_addressType", "shipment"));
					 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_shippingMethodID", "001"));
					 parms.add(new NameValuePair("csrf_token",Csrf));
			         HttpRequest req2 = new HttpRequest()
								.XHR()
								.url("/on/demandware.store/Sites-fireMountainGems-Site/default/CheckoutShippingServices-SubmitShipping")
			                    .POST()             
							    .postParams(parms);
			         WebResponse response2=req2.fire();
			          //Assert.assertEquals("Responce expected",new JSONObject(response2.getContentAsString()));
			        
			         //Assert.assertEquals("Responce expected",new JSONObject(shipparmsresponse3.getContentAsString()));
			         
			         final List<NameValuePair> Paymentparms = new ArrayList<NameValuePair>();
			         
			         //Paymentparms.add(new NameValuePair("billing", "on"));
			         //Paymentparms.add(new NameValuePair("shipmentUUID", shipmetUId));
			         //Paymentparms.add(new NameValuePair("UUID", UId));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_contactInfoFields_email", account.email));
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
//			         Paymentparms.add(new NameValuePair("dwfrm_billing_adyenPaymentFields_adyenStateData", account.PaymentStatedata));
//			         Paymentparms.add(new NameValuePair("csrf_token",Csrf));
//			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardNumber", "************4305"));
//			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardType", "visa"));
			         
			         //Paymentparms.add(new NameValuePair("dwfrm_billing_adyenPaymentFields_adyenStateData", creditCard.PaymentStatedata));
			         Paymentparms.add(new NameValuePair("csrf_token",Csrf));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardNumber", creditCard.number));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardType", creditCard.type));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_cardOwner", account.getFullName()));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_expirationYear", "30"));
			         Paymentparms.add(new NameValuePair("dwfrm_billing_creditCardFields_expirationMonth", "03"));
			         Paymentparms.add(new NameValuePair("braintreeIs3dSecureRequired", "false"));
			         Paymentparms.add(new NameValuePair("braintreeSaveCreditCard", "true"));
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
		ShippingAddressPage.instance.validate();
		//this.getWebClient().getOptions().setJavaScriptEnabled(false);
        Validator.validatePageSource();
        // Validate that it is the payment page
        //PaymentPage.instance.validate();
	}
}