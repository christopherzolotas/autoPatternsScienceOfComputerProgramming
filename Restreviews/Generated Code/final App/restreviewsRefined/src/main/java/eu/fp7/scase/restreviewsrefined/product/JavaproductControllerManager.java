/*
 * ARISTOSTLE UNIVERSITY OF THESSALONIKI
 * Copyright (C) 2015
 * Aristotle University of Thessaloniki
 * Department of Electrical & Computer Engineering
 * Division of Electronics & Computer Engineering
 * Intelligent Systems & Software Engineering Lab
 *
 * Project             : restreviewsRefined
 * WorkFile            : 
 * Compiler            : 
 * File Description    : 
 * Document Description: 
* Related Documents	   : 
* Note				   : 
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas
* Contact			   : christopherzolotas@issel.ee.auth.gr
*/


package eu.fp7.scase.restreviewsrefined.product;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;


/* This class defines the web API of the manager product resource. It handles POST and GET HTTP requests, as it is prescribed by the meta-models.*/
@Path("/multiproductManager")
public class JavaproductControllerManager{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles POST requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
		@Path("/account/{accountId}/product/")
		@POST
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaproductModel postaccountproduct(@PathParam("accountId")int accountId, JavaproductModel oJavaproductModel,@DefaultValue("none") @QueryParam("action") String observerAction,@DefaultValue("none") @QueryParam("reaction") String observerReaction,@DefaultValue("none") @QueryParam("observerId") String observerId){
	        ObservablePostaccountproductHandler oObservablePostaccountproductHandler = new ObservablePostaccountproductHandler(accountId, oJavaproductModel, oApplicationUri);
	        int newObserverId = 0;
			JavaproductModel orJavaproductModel = oObservablePostaccountproductHandler.postJavaproductModel();
			return orJavaproductModel;
	    }

		/* This function handles POST requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
		@Path("/order/{orderId}/product/")
		@POST
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaproductModel postorderproduct(@PathParam("orderId")int orderId, JavaproductModel oJavaproductModel,@DefaultValue("none") @QueryParam("action") String observerAction,@DefaultValue("none") @QueryParam("reaction") String observerReaction,@DefaultValue("none") @QueryParam("observerId") String observerId){
	        ObservablePostorderproductHandler oObservablePostorderproductHandler = new ObservablePostorderproductHandler(orderId, oJavaproductModel, oApplicationUri);
	        int newObserverId = 0;
			JavaproductModel orJavaproductModel = oObservablePostorderproductHandler.postJavaproductModel();
			return orJavaproductModel;
	    }

    /* This function handles GET requests  
     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
	@Path("/account/{accountId}/product/")
	@GET
	@Produces("application/JSON")
    public JavaproductModelManager getaccountproductList(@PathParam("accountId")int accountId){
        GetaccountproductListHandler oGetaccountproductListHandler = new GetaccountproductListHandler(accountId, oApplicationUri);
        return oGetaccountproductListHandler.getJavaproductModelManager();
    }

    /* This function handles GET requests  
     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
	@Path("/order/{orderId}/product/")
	@GET
	@Produces("application/JSON")
    public JavaproductModelManager getorderproductList(@PathParam("orderId")int orderId){
        GetorderproductListHandler oGetorderproductListHandler = new GetorderproductListHandler(orderId, oApplicationUri);
        return oGetorderproductListHandler.getJavaproductModelManager();
    }

}
