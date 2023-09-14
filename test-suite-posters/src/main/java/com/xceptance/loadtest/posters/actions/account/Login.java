package com.xceptance.loadtest.posters.actions.account;

import java.util.ArrayList;
import java.util.List;

import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;
import org.json.JSONObject;
import org.junit.Assert;

import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.account.LoginPage;
import com.xceptance.loadtest.posters.models.pages.general.HomePage;

/**
 * Logs in with the given account.
 * 
 * @author Xceptance Software Technologies
 */
public class Login extends PageAction<Login>
{
    private final Account account;

    public Login(final Account account)
    {
        this.account = account;
    }

    @Override
    protected void doExecute() throws Exception
    {
        //LoginPage.instance.loginForm.fillLoginForm(account);
        
        //loadPageByClick(LoginPage.instance.loginForm.getSignInButton());
    	String csrf=LoginPage.instance.loginForm.GetCsrf();
        final List<NameValuePair> parm = new ArrayList<NameValuePair>();
     	 parm.add(new NameValuePair("loginEmail", "bhaskarrao122.s@etg.digital"));
     	 parm.add(new NameValuePair("loginPassword", "Bhaskaretg@123"));
     	 parm.add(new NameValuePair("g-recaptcha-action", "login"));
     	 parm.add(new NameValuePair("csrf_token", csrf));
     	HttpRequest req1 = new HttpRequest()
  				.XHR()
  				.url("/on/demandware.store/Sites-fireMountainGems-Site/default/Account-Login")
                  .POST()             
  			    .postParams(parm);
       WebResponse response=req1.fire();
       String url = "";
       if(response.getStatusCode()==200)
   	 url=new JSONObject(response.getContentAsString()).getString("redirectUrl");
       else
    	 Assert.fail(response.getStatusMessage());
    loadPageByUrlClick(url);
    
    }

    @Override
    protected void postValidate() throws Exception
    {
        Validator.validatePageSource();
        
        Assert.assertTrue("User is not logged in", HomePage.instance.user.isLoggedIn());
    }
}