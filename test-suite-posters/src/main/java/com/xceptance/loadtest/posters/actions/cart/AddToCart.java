package com.xceptance.loadtest.posters.actions.cart;

import org.json.JSONObject;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import org.htmlunit.WebResponse;
import org.htmlunit.util.NameValuePair;

import com.xceptance.loadtest.api.actions.AjaxAction;
import com.xceptance.loadtest.api.util.AjaxUtils;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.HttpRequest;
import com.xceptance.loadtest.posters.models.pages.catalog.ProductDetailPage;
import com.xceptance.loadtest.posters.models.pages.general.GeneralPages;

/**
 * Adds the product items of the current product page to the cart.
 * 
 * @author Xceptance Software Technologies
 */
public class AddToCart extends AjaxAction<AddToCart>
{
    private int previousCartQuantity;
    
    private String productId;
    
    private String size;
    
    private String finish;

    @Override
    public void precheck()
    {
        // Retrieve current quantity
        previousCartQuantity = GeneralPages.instance.miniCart.getQuantity();

        // Retrieve PID
        productId = ProductDetailPage.instance.getProductId();
        
        // Retrieve selected size
        //size = ProductDetailPage.instance.getSelectedSize();
        
        // Retrieve selected finish
        //finish = ProductDetailPage.instance.getSelectedFinish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() throws Exception
    {
    	final List<NameValuePair> addToCartParams = new ArrayList<NameValuePair>();
        addToCartParams.add(new NameValuePair("pid",  productId));
        addToCartParams.add(new NameValuePair("quantity", "1"));
        HttpRequest req = new HttpRequest()
					.url("/on/demandware.store/Sites-fireMountainGems-Site/default/Cart-AddProduct")
                   .POST()          
				    .postParams(addToCartParams);
        WebResponse response1=req.fire();
		
    	// Send add to cart request
    	//WebResponse response = new HttpRequest()
    		//.XHR()
    		//.GET()
    		//.url("/posters/addToCartSlider")
    		//.param("productId", productId)
    		//.param("finish", finish)
    		//.param("size", size)
    		//.assertJSONObject("Expected product information to be contained in add to cart response", true, json -> json.has("product"))
    		//.fire();
    	
    	// Safely convert the response to JSON
    	JSONObject addToCartJson = AjaxUtils.convertToJson(response1.getContentAsString());
    	
    	//Assert.assertEquals("Cart quantity did not change",addToCartJson );

//        // Handle error in add to cart response
//    	if(!addToCartJson.has("error"))
//    	{
//    		
//    	}
   	var error = addToCartJson.get("error").toString();
    	boolean isError = Boolean.parseBoolean(error);
    	if(!isError) {
    		Context.get().data.totalAddToCartCount++;
    	}
    	//Assert.assertEquals("Cart quantity did not change",error );
        // Validate the item count in the add to cart response (headerCartOverview = itemsInMiniCart)
        //Assert.assertTrue("Cart quantity did not change", addToCartJson.has("headerCartOverview") && (addToCartJson.getInt("headerCartOverview") > previousCartQuantity));
    	
        // Update the mini cart item count
    	//GeneralPages.instance.miniCart.updateQuantity(addToCartJson.getInt("headerCartOverview"));

    	// Increase total add to cart count if successful
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void postValidate() throws Exception
    {
    	// Nothing to validate
    }
}