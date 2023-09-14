package com.xceptance.loadtest.posters.models.components.checkout;

import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlForm;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.hpu.HPU;
import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;
import com.xceptance.loadtest.api.models.pages.Page;
import com.xceptance.loadtest.api.util.FormUtils;

/**
 * Shipping address form component.
 *  
 * @author Xceptance Software Technologies
 */
public class ShippingAddressForm implements Component
{
    public final static ShippingAddressForm instance = new ShippingAddressForm();
    
    @Override
    public LookUpResult locate()
    {
        //return Page.find().byId("formAddDelAddr");
        return Page.find().byCss("div.shipping-section");
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }
    
    public void EmailfillForm(final Account account)
    { 
        final HtmlForm form = HPU.find().in(locate().asserted("Expected single Main").single()).byId("guest-customer").single();
        FormUtils.setInputValue(HPU.find().in(form).byId("email-guest"), account.email);
       // FormUtils.checkRadioButton(HPU.find().in(form).byCss("#billEqualShipp-Yes"));
    }
    
    public String GetShipmentUUid()
    {
    	final HtmlElement  element = HPU.find().in(locate().asserted("Expected single Main").single()).byXPath("//input[@type='hidden'][@name='shipmentUUID']").first();
   	 return element.getAttribute("value");
    }
    
    public String GetCsrf()
    {
    	 final HtmlElement  element = HPU.find().in(locate().asserted("Expected single Main").single()).byXPath("//input[@type='hidden'][@name='csrf_token']").first();
    	 return element.getAttribute("value");
    }
    
    public void fillForm(final Account account)
    {
    	//final HtmlForm form = locate().asserted("Expected single shipping address form").single();
        final HtmlForm form =  HPU.find().in(locate().asserted("Expected single Main").single()).byId("dwfrm_shipping").asserted("Expected single Form").single();

        //FormUtils.setInputValue(HPU.find().in(form).byCss("#fullName"), account.getFullName());
        FormUtils.setInputValue(HPU.find().in(form).byCss("#shippingFirstNamedefault"), account.firstname);
        FormUtils.setInputValue(HPU.find().in(form).byCss("#shippingLastNamedefault"), account.lastname);
        
        //FormUtils.setInputValue(HPU.find().in(form).byCss("#addressLine"), account.shippingAddress.addressLine1);
        FormUtils.setInputValue(HPU.find().in(form).byId("shippingAddressOnedefault"), account.shippingAddress.addressLine1);
        FormUtils.setInputValue(HPU.find().in(form).byId("shippingAddressCitydefault"), account.shippingAddress.city);
        //FormUtils.setInputValue(HPU.find().in(form).byId("shippingStatedefault"), "Indiana");
        FormUtils.select(HPU.find().in(form).byId("shippingCountrydefault"), account.shippingAddress.countryCode);
        //FormUtils.select(HPU.find().in(form).byId("shippingStatedefault"), "MA");
        FormUtils.setInputValue(HPU.find().in(form).byId("shippingZipCodedefault"), account.shippingAddress.zip);
        //FormUtils.select(HPU.find().in(form).byId("shippingCountrydefault"), account.shippingAddress.country);
        FormUtils.setInputValue(HPU.find().in(form).byId("shippingPhoneNumberdefault"), account.shippingAddress.phone);
        //FormUtils.checkRadioButton(HPU.find().in(form).byCss("#billEqualShipp-Yes"));
    }
    
    public HtmlElement getContinueButton()
    {
    	//return HPU.find().in(locate().asserted("Expected single shipping address form").single()).byId("btnAddDelAddr").asserted("Expected single continue button").single();
    	return HPU.find().in(locate().asserted("Expected single shipping address form").single()).byCss("button.submit-shipping").asserted("Expected single continue button").single();
    }
}