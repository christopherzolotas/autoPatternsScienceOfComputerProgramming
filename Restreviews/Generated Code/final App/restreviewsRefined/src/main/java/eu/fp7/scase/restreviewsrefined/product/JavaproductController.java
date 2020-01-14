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
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas/ Design patterns implementations created by Dontsios Dimitrios
* Contact			   : christopherzolotas@issel.ee.auth.gr / dontsios@gmail.com
*/


package eu.fp7.scase.restreviewsrefined.product;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

/* This class defines the web API of the individual product resource. It may handle PUT, GET and/or DELETE requests 
   depending on the specific CIM of the service.*/

@Path("/multiproduct")
public class JavaproductController{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles http GET requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/account/{accountId}/product/{productId}")
		@GET
		@Produces("application/JSON")
	    public JavaproductModel getaccountproduct(@PathParam("productId") int productId){
	        GetaccountproductHandler oGetaccountproductHandler = new GetaccountproductHandler(productId, oApplicationUri);

			return oGetaccountproductHandler.getJavaproductModel();
	    }
		//mpike sto 1

		/* This function handles http GET requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/order/{orderId}/product/{productId}")
		@GET
		@Produces("application/JSON")
	    public JavaproductModel getorderproduct(@PathParam("productId") int productId){
	        GetorderproductHandler oGetorderproductHandler = new GetorderproductHandler(productId, oApplicationUri);

			return oGetorderproductHandler.getJavaproductModel();
	    }
		//mpike sto 1

		/* This function handles http PUT requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/account/{accountId}/product/{productId}")
		@PUT
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaproductModel putaccountproduct(@PathParam("accountId") int accountId, @PathParam("productId") int productId,  JavaproductModel oJavaproductModel){
	        PutaccountproductHandler oPutaccountproductHandler = new PutaccountproductHandler(accountId, productId, oJavaproductModel, oApplicationUri);
	        int newObserverId = 0;
			ObservablePostproductHandler oObservablePostproductHandler = new ObservablePostproductHandler(oJavaproductModel, oApplicationUri);
			if(observerAction.toLowerCase().equals("unregister")){
                oObservablePostproduct.unregister(observerId);
        	}else if(observerAction.toLowerCase().equals("registertoverb")){
				newObserverId = oObservablePostproduct.registerToVerb(observerReaction);
			}
			return oPutaccountproductHandler.putJavaproductModel();
	    }

		/* This function handles http PUT requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/order/{orderId}/product/{productId}")
		@PUT
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaproductModel putorderproduct(@PathParam("orderId") int orderId, @PathParam("productId") int productId,  JavaproductModel oJavaproductModel){
	        PutorderproductHandler oPutorderproductHandler = new PutorderproductHandler(orderId, productId, oJavaproductModel, oApplicationUri);
	        int newObserverId = 0;
			ObservablePostproductHandler oObservablePostproductHandler = new ObservablePostproductHandler(oJavaproductModel, oApplicationUri);
			if(observerAction.toLowerCase().equals("unregister")){
                oObservablePostproduct.unregister(observerId);
        	}else if(observerAction.toLowerCase().equals("registertoverb")){
				newObserverId = oObservablePostproduct.registerToVerb(observerReaction);
			}
			return oPutorderproductHandler.putJavaproductModel();
	    }

	    /* This function handles http DELETE requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/account/{accountId}/product/{productId}")
		@DELETE
		@Produces("application/JSON")
	    public JavaproductModel deleteaccountproduct(@PathParam("productId") int productId){
	        DeleteaccountproductHandler oDeleteaccountproductHandler = new DeleteaccountproductHandler(productId, oApplicationUri);
			return oDeleteaccountproductHandler.deleteJavaproductModel();
	    }

	    /* This function handles http DELETE requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/order/{orderId}/product/{productId}")
		@DELETE
		@Produces("application/JSON")
	    public JavaproductModel deleteorderproduct(@PathParam("productId") int productId){
	        DeleteorderproductHandler oDeleteorderproductHandler = new DeleteorderproductHandler(productId, oApplicationUri);
			return oDeleteorderproductHandler.deleteJavaproductModel();
	    }
}

/*
 what are the representations?	account
 what is the name of the parent?	product
 does it have builder pattern?	DesignPatternsLayerPSM.impl.JavaBuilderPatternImpl@63828988

*/

