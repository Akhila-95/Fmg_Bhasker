package com.xceptance.loadtest.posters.models.components.general;

import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;

/**
 * User menu component.
 * 
 * @author Xceptance Software Technologies
 */
public class UserMenu implements Component
{
	public static final UserMenu instance = new UserMenu();

    @Override
    public LookUpResult locate()
    {
        //return Header.instance.locate().byCss("#userMenu");
        return Header.instance.locate().byCss("div.user");
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }

    public LookUpResult getLoginLink()
    {
        //return locate().byCss("a.goToLogin");
        return locate().byCss("a.login-button");
    }
    
    public LookUpResult getCreateAccountLink()
    {
    	//return locate().byCss("a.goToRegistration");
    	return locate().byCss("a.create-button");
    }

    public LookUpResult getMyAccountLink()
    {
        //return locate().byCss("a.goToAccountOverview");
        return locate().byXPath("//div[@class='icon-block']//a[@href='https://utsf.firemountain.org/account']");
        //return locate().byXPath("//div[@class='icon-block']//a[@href='https://sfccstage.firemountain.org/account/']");
        
    }

    public LookUpResult getLogoutLink()
    {
        //return locate().byCss("a.goToLogout");
        return locate().byXPath(".//div[@class='text-right']/descendant::a[@href='https://utsf.firemountain.org/on/demandware.store/Sites-fireMountainGems-Site/default/Login-Logout']");
        //return locate().byXPath(".//div[@class='text-right']/descendant::a[@href='https://sfccstage.firemountain.org/on/demandware.store/Sites-fireMountainGems-Site/default/Login-Logout']");
    }
    
    public boolean isLoggedIn()
    {
        return getLogoutLink().exists();
    }

    public boolean isNotLoggedIn()
    {
        return !isLoggedIn();
    }
}