/*
 * ARISTOSTLE UNIVERSITY OF THESSALONIKI
 * Copyright (C) 2015
 * Aristotle University of Thessaloniki
 * Department of Electrical & Computer Engineering
 * Division of Electronics & Computer Engineering
 * Intelligent Systems & Software Engineering Lab
 *
 * Project             : restreviews
 * WorkFile            : 
 * Compiler            : 
 * File Description    : 
 * Document Description: 
* Related Documents	   : 
* Note				   : 
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas/ Design patterns implementations created by Dontsios Dimitrios
* Contact			   : christopherzolotas@issel.ee.auth.gr / dontsios@gmail.com
*/


package eu.fp7.scase.restreviews.review;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

/* This class defines the web API of the individual review resource. It may handle PUT, GET and/or DELETE requests 
   depending on the specific CIM of the service.*/

@Path("/product/{productId}/review/{reviewId}")
public class JavareviewController{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles http GET requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@GET
		@Produces("application/JSON")
	    public JavareviewModel getreview(@PathParam("reviewId") int reviewId){
	        GetreviewHandler oGetreviewHandler = new GetreviewHandler(reviewId, oApplicationUri);

			return oGetreviewHandler.getJavareviewModel();
	    }
		//mpike sto 1

		/* This function handles http PUT requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@PUT
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavareviewModel putreview(@PathParam("productId") int productId, @PathParam("reviewId") int reviewId,  JavareviewModel oJavareviewModel){
	        PutreviewHandler oPutreviewHandler = new PutreviewHandler(productId, reviewId, oJavareviewModel, oApplicationUri);
	        int newObserverId = 0;
			ObservablePostreviewHandler oObservablePostreviewHandler = new ObservablePostreviewHandler(oJavareviewModel, oApplicationUri);
			if(observerAction.toLowerCase().equals("unregister")){
                oObservablePostreview.unregister(observerId);
        	}else if(observerAction.toLowerCase().equals("registertoverb")){
				newObserverId = oObservablePostreview.registerToVerb(observerReaction);
			}
			return oPutreviewHandler.putJavareviewModel();
	    }

	    /* This function handles http DELETE requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@DELETE
		@Produces("application/JSON")
	    public JavareviewModel deletereview(@PathParam("reviewId") int reviewId){
	        DeletereviewHandler oDeletereviewHandler = new DeletereviewHandler(reviewId, oApplicationUri);
			return oDeletereviewHandler.deleteJavareviewModel();
	    }
}

/*
 what are the representations?	account
 what is the name of the parent?	review
 does it have builder pattern?	DesignPatternsLayerPSM.impl.JavaBuilderPatternImpl@78e52e87

*/

