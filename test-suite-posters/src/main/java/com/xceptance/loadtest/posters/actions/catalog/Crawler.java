package com.xceptance.loadtest.posters.actions.catalog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.xceptance.loadtest.api.actions.PageAction;
import com.xceptance.loadtest.api.validators.Validator;
import com.xceptance.loadtest.posters.models.pages.catalog.ProductListingPage;
import com.xceptance.loadtest.posters.models.pages.general.GeneralPages;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Array;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

public class Crawler extends PageAction<Crawler> {
	
	   private final Set<URL> links;
	    private final long startTime;
	    private final URL urlString; 
	    private int count=0;
	  public Crawler(final URL startURL)
	    {
		  this.links = new HashSet<>();
	        this.startTime = System.currentTimeMillis();
	        this.urlString = startURL;
	        links.clear();
	        crawl(initURLS(startURL));
	    }
	    private Set<URL> initURLS(final URL startURL) {
	        return Collections.singleton(startURL);
	    }
	    private void crawl(final Set<URL> urls) {
	        urls.removeAll(this.links);
	        String strUserId = "storefront";
            String strPasword = "FMG2023";
            /*
             * User id, password string needs to be in
             * userid:password format with no space 
             * in between them
             */
            String authString = strUserId + ":" + strPasword;
            //encode the authString using base64
            String encodedString = 
                new String( Base64.encodeBase64(authString.getBytes()) );
	        if(!urls.isEmpty()) {
	            final Set<URL> newURLS = new HashSet<>();
	            try {
	                this.links.addAll(urls);
	                for(final URL url : urls) {
	                    System.out.println("time = "
	                        +(System.currentTimeMillis() - this.startTime)+ " connected to : " + url);
	                    try {
	                    	 final Document document = Jsoup.connect(url.toString()) .header("Authorization", "Basic " + encodedString)
	 	                            .get();
	                    	   count++;
	    	                   final Elements linksOnPage = document.select("a[href]");
	    	                 
	    	                   //System.out.println("counted elements"
	        	                        //+(System.currentTimeMillis() - this.startTime)+ " size of : " + linksOnPage.size());
	    	                    for(final Element element : linksOnPage) {
	    	                    	
	    	                         String urlText = element.attr("href");
	    	                        
	    	                      String[] arraysplit = urlText.split("/");
	    	                       if(arraysplit.length>1 && !urlText.contains(".jpg")) {
	    	                        	if(urlText!="" && !urlText.contains("https:")) {
	    	                        		urlText = "https://sfccstage.firemountain.org" + urlText;
	    	                        	
	    	                        	}
	    	                        	//System.out.println("finded elements : " + urlText);
	                                    final URL discoveredURL = new URL(urlText);
	    		                        newURLS.add(discoveredURL);
	    	                        }
	    	                        
	    	                    }
	                    }  catch (final Error e){
	                    	 System.out.println("-------------------------------------------------------exception msg " + e.getMessage());
	                    }
	                   
	                }
	            }
	            
	             catch(final Exception | Error ignored) {
	            }
	            System.out.println("-------------------------------------------------------finded elements count : " + newURLS.size());
	            //if (newURLS.size() == 0) {
	            	//crawl(urls);
	            //} else {	            	
	            	crawl(newURLS);
	            }
	        }
	      
	    //}
	   /**
	     * {@inheritDoc}
	     */
	   
	    @Override
	    protected void doExecute() throws Exception
	    {  
	    	System.out.print(" The Number of Links found "+ this.count);
	        // In case this is does not really end up somewhere useful, open a category
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void postValidate() throws Exception
	    {
	        Validator.validatePageSource();

	        GeneralPages.instance.validate();
	    }

}
