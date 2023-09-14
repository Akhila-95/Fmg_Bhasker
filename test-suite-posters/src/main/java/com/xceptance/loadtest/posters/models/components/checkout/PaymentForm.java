package com.xceptance.loadtest.posters.models.components.checkout;

import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlForm;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.hpu.HPU;
import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;
import com.xceptance.loadtest.api.models.pages.Page;
import com.xceptance.loadtest.api.util.FormUtils;

/**
 * Payment form component.
 *  
 * @author Xceptance Software Technologies
 */
public class PaymentForm implements Component
{
    public final static PaymentForm instance = new PaymentForm();
    
    @Override
    public LookUpResult locate()
    {
        //return Page.find().byId("formAddPayment");
        return Page.find().byCss(".tab-content>div.braintree-creditcard-content");
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }
    
    public void fillForm(final Account account, CreditCard creditCard)
    {
    	//final HtmlForm form = locate().asserted("Expected single payment form").single();
        final HtmlForm form = HPU.find().in(locate().asserted("Expected single Main").single()).byId("div.billing-nav").single();

        //FormUtils.setInputValue(HPU.find().in(form).byCss("#creditCardNumber"), creditCard.number);
        FormUtils.setInputValue(HPU.find().in(form).byCss("#credit-card-number"), creditCard.number);
        FormUtils.setInputValue(HPU.find().in(form).byCss("#cardholder-name"), account.getFullName());
        //FormUtils.select(HPU.find().in(form).byCss("#expirationDateMonth"), creditCard.expirationMonth);
        //FormUtils.select(HPU.find().in(form).byCss("#expirationDateYear"), creditCard.expirationYear);
        FormUtils.select(HPU.find().in(form).byCss("#expiration"), creditCard.expiration);

    }
    
    public HtmlElement getContinueButton()
    {
    	//return HPU.find().in(locate().asserted("Expected single payment form").single()).byId("btnAddPayment").asserted("Expected single continue button").single();
    	return HPU.find().in(locate().asserted("Expected single payment form").single()).byCss("button.submit-payment").asserted("Expected single continue button").single();
    }
}