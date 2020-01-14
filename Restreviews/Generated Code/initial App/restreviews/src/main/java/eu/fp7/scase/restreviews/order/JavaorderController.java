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


package eu.fp7.scase.restreviews.order;

import javax.ws.rs.Consumes;
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

/* This class defines the web API of the individual order resource. It may handle PUT, GET and/or DELETE requests 
   depending on the specific CIM of the service.*/

@Path("/order/{orderId}")
public class JavaorderController{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles http GET requests  
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@GET
		@Produces("application/JSON")
	    public JavaorderModel getorder(@PathParam("orderId") int orderId, @DefaultValue("0") @QueryParam("takeMemento") int mementoNum){
	        if( mementoNum > 0 ){
				JavaorderMemento oMemento = new JavaorderMemento();
				return oMemento.getorderMementoHanlder(orderId,mementoNum);
			}
			GetorderHandler oGetorderHandler = new GetorderHandler(orderId, oApplicationUri);
			return oGetorderHandler.getJavaorderModel();
	    }

		/* This function handles http PUT requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@PUT
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaorderModel putorder(@PathParam("orderId") int orderId,  JavaorderModel oJavaorderModel, @DefaultValue("keepMemento") @QueryParam("action") String action, @DefaultValue("0") @QueryParam("mNum") int mementoNum){
	        if(action.equalsIgnoreCase("keepMemento")){
				JavaorderMemento oMemento = new JavaorderMemento();
				oMemento.createMemento();
				oMemento.defineMemento(orderId);
			}else if(action.equalsIgnoreCase("restoreState")){
				JavaorderMemento oMemento = new JavaorderMemento();
				oJavaorderModel = oMemento.putRestoreorderFromMementoHandler(orderId,mementoNum);
				PutorderHandler oPutorderHandler = new PutorderHandler(oJavaorderModel.getorderId(), oJavaorderModel, oApplicationUri);
	       	 	return oPutorderHandler.putJavaorderModel();
			}
			PutorderHandler oPutorderHandler = new PutorderHandler(orderId, oJavaorderModel, oApplicationUri);
			return oPutorderHandler.putJavaorderModel();
	    }

}

/*
 what are the representations?	account
 what is the name of the parent?	order
 does it have builder pattern?	DesignPatternsLayerPSM.impl.JavaBuilderPatternImpl@78e52e87

*/

