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


package eu.fp7.scase.restreviewsrefined.searchProduct;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
//Please add any needed imports here.

/* JavaAlgosearchProductController class is responsible to handle incoming http requests for the searchProduct resource. Since this one 
 is not automatable the developer has to manually define its RESTful API.*/
@Path("/AlgosearchProduct")
public class JavaAlgosearchProductController{

    @Context
    private UriInfo oApplicationUri;

	/* This function handles http  requests  
    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
	@Path("/")
	@GET
	@Produces("application/JSON")
    public JavaAlgosearchProductModel getsearchProduct(){

        //TODO add any code that uses the according HTTPHandler class in order to satisfy the client request.
		return new JavaAlgosearchProductModel(); //TODO change the return value appropriately.

    }
}
