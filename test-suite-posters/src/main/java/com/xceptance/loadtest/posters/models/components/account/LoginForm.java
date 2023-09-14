package com.xceptance.loadtest.posters.models.components.account;

import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlForm;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.hpu.HPU;
import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;
import com.xceptance.loadtest.api.models.pages.Page;
import com.xceptance.loadtest.api.util.FormUtils;

/**
 * LoginForm component.
 * 
 * @author Xceptance Software Technologies
 */
public class LoginForm implements Component
{
	public static final LoginForm instance = new LoginForm();

    @Override
    public LookUpResult locate()
    {
        //return Page.find().byId("formLogin");
    	return Page.find().byCss("div.login-title");
        
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }
    
    public HtmlElement getSignInButton()
    {
    	//return locate().byCss("#btnSignIn").asserted().single();
    	return locate().byCss(".login>button.btn").asserted().single();
    }

    public HtmlForm fillLoginForm(final Account account)
    {
        final HtmlForm form = locate().byCss("form.login").single();
        //final HtmlForm form = locate().asserted().single().by("form.login").single();

        //FormUtils.setInputValue(HPU.find().in(form).byId("email"), account.email);
        FormUtils.setInputValue(HPU.find().in(form).byId("login-form-email"), account.email);
        
        FormUtils.setInputValue(HPU.find().in(form).byId("login-form-password"), account.password);

        return form;

    }
    public String GetCsrf()
    {
    	 final HtmlElement  element = HPU.find().in(locate().single()).byXPath("//input[@type='hidden'][@name='csrf_token']").first();
    	 return element.getAttribute("value");
    }
}