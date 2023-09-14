package com.xceptance.loadtest.posters.actions.checkout;

import java.util.ArrayList;

import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;
import org.json.JSONObject;
import org.junit.Assert;
import java.util.List;

import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.checkout.PaymentPage;
import com.xceptance.loadtest.posters.models.pages.checkout.ShippingAddressPage;

/**
 * Handles the shipping address page.
 * 
 * @author Xceptance Software Technologies
 */
public class CheckoutShippingAddress extends PageAction<CheckoutShippingAddress>
{
	private Account account;
	
	public CheckoutShippingAddress()
	{
		this(Context.get().data.getAccount().get());
	}
	
	public CheckoutShippingAddress(Account account)
	{
		this.account = account;
	}
	
	@Override
	public void precheck()
	{
		super.precheck();

		// Validate that we have the shipping address form
		Assert.assertTrue("Expected shipping address form", ShippingAddressPage.shippingAddressForm.exists());
	}
	
	@Override
	protected void doExecute() throws Exception
	{
		//Fill type of checkout
				ShippingAddressPage.shippingAddressForm.EmailfillForm(account);
				
				 String Csrf=ShippingAddressPage.shippingAddressForm.GetCsrf();
				 final List<NameValuePair> EmailParams = new ArrayList<NameValuePair>();
			 EmailParams.add(new NameValuePair("csrf_token", Csrf));
				 EmailParams.add(new NameValuePair("dwfrm_coCustomer_email", account.email));
		        HttpRequest req = new HttpRequest()
							.XHR()
							.url("/on/demandware.store/Sites-fireMountainGems-Site/default/CheckoutServices-SubmitCustomer")
		                   .POST()             
						    .postParams(EmailParams);
		        WebResponse response1=req.fire();
		        //Assert.assertEquals("Responce expected",response1.getContentAsString());
		        String shipmetUId=new JSONObject(response1.getContentAsString()).getJSONObject("order").getJSONObject("items").getJSONArray("items").getJSONObject(0).getString("shipmentUUID");
		        String UId=new JSONObject(response1.getContentAsString()).getJSONObject("order").getJSONObject("items").getJSONArray("items").getJSONObject(0).getString("UUID");
		      
				// Fill shipping address form
				ShippingAddressPage.shippingAddressForm.fillForm(account);
				final List<NameValuePair> parms = new ArrayList<NameValuePair>();
				 parms.add(new NameValuePair("originalShipmentUUID", shipmetUId));
				 parms.add(new NameValuePair("shipmentUUID", shipmetUId));
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
				 //parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_phone", "+"+account.mobile));
				 //parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_addressFields_addressType", "shipment"));
				 parms.add(new NameValuePair("shippingRequestCatalog", "on"));
				 parms.add(new NameValuePair("shipmentSelector", "true"));
				 parms.add(new NameValuePair("dwfrm_shipping_shippingAddress_shippingMethodID", "001"));
				 parms.add(new NameValuePair("csrf_token",Csrf));
		        HttpRequest req2 = new HttpRequest()
							.XHR()
							.url("/on/demandware.store/Sites-fireMountainGems-Site/default/CheckoutShippingServices-SubmitShipping")
		                   .POST()             
						    .postParams(parms);
		        WebResponse response2=req2.fire();
				
				// Click continue button
				//loadPageByClick(ShippingAddressPage.shippingAddressForm.getContinueButton());
		        
		        

	}

	@Override
	protected void postValidate() throws Exception
	{
        Validator.validatePageSource();

        // Validate that it is the payment page
        PaymentPage.instance.validate();
	}
}