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


package eu.fp7.scase.restreviews.account;

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
import eu.fp7.scase.restreviews.account.JavaaccountModelRepresentation;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

/* This class defines the web API of the individual account resource. It may handle PUT, GET and/or DELETE requests 
   depending on the specific CIM of the service.*/

@Path("/account/{accountId}")
public class JavaaccountController{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles http GET requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@GET
		@Produces("application/JSON")
		public JavaaccountModelRepresentation getaccount(@PathParam("accountId") int accountId, @DefaultValue("account_email_username") @QueryParam("representationName") String representationName){
	        GetaccountHandler oGetaccountHandler = new GetaccountHandler(accountId, representationName, oApplicationUri);
	        return oGetaccountHandler.getJavaaccountModelRepresentation(representationName);
	    }

		/* This function handles http PUT requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@PUT
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaaccountModel putaccount(@PathParam("accountId") int accountId,  JavaaccountModel oJavaaccountModel){
	        PutaccountHandler oPutaccountHandler = new PutaccountHandler(accountId, oJavaaccountModel, oApplicationUri);
			return oPutaccountHandler.putJavaaccountModel();
	    }

	    /* This function handles http DELETE requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@DELETE
		@Produces("application/JSON")
	    public JavaaccountModel deleteaccount(@PathParam("accountId") int accountId){
	        DeleteaccountHandler oDeleteaccountHandler = new DeleteaccountHandler(accountId, oApplicationUri);
			return oDeleteaccountHandler.deleteJavaaccountModel();
	    }
}

/*
 what are the representations?	account
 what is the name of the parent?	account
 does it have builder pattern?	DesignPatternsLayerPSM.impl.JavaBuilderPatternImpl@78e52e87

*/

