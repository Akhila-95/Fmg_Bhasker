package com.xceptance.loadtest.posters.models.components.general;

import com.xceptance.loadtest.api.hpu.LookUpResult;
import com.xceptance.loadtest.api.models.components.Component;

/**
 * Search component.
 * 
 * @author Xceptance Software Technologies
 */
public class Search implements Component
{
	public static final Search instance = new Search();

    @Override
    public LookUpResult locate()
    {
        //return Header.instance.locate().byCss("#header-menu-search");
        return Header.instance.locate().byCss("div.search");
    }

    @Override
    public boolean exists()
    {
        return locate().exists();
    }
    
    public LookUpResult getSearchForm()
    {
    	//return locate().byCss("#searchForm");
    	return locate().byCss("div.site-search");
    }

    public LookUpResult getSearchField()
    {
        //return getSearchForm().byCss("input#s");
        return getSearchForm().byCss("input.search-field");
    }
    
    public LookUpResult getSearchButton()
    {
        //return getSearchForm().byCss("#btnSearch");
        return getSearchForm().byCss("button.fa-search"); 
    }
}