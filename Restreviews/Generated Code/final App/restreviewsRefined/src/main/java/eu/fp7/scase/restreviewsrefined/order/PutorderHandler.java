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


package eu.fp7.scase.restreviewsrefined.order;


import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviewsrefined.utilities.HypermediaLink;
import eu.fp7.scase.restreviewsrefined.utilities.HibernateController;


/* This class processes PUT requests for order resources and creates the hypermedia to be returned to the client*/
public class PutorderHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaorderModel oJavaorderModel;


    public PutorderHandler(int orderId, JavaorderModel oJavaorderModel, UriInfo oApplicationUri){
        this.oJavaorderModel = oJavaorderModel;
        this.oJavaorderModel.setorderId(orderId);
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
    }

	public String calculateProperResourcePath(){
    	if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
        	return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
    	}
    	else{
        	return this.oApplicationUri.getPath();
    	}
	}

    public JavaorderModel putJavaorderModel(){


        return createHypermedia(oHibernateController.putorder(oJavaorderModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaorderModel createHypermedia(JavaorderModel oJavaorderModel){

        /* Create hypermedia links towards this specific order resource. These can be GET, PUT and/or delete depending on what was specified in the service CIM.*/
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get the order", "GET", "Sibling"));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Update the order", "PUT", "Sibling"));

        /* Calculate the relative path towards any related resources of this one. Then add each new hypermedia link with that relative path to the hypermedia linklist to be sent back to client.*/
        String oRelativePath;
		oRelativePath = this.strResourcePath;
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%s/%s", oApplicationUri.getBaseUri(), "multiproductManager", oRelativePath, "product"), "Get all the products of this order", "GET", "Child"));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%s/%s", oApplicationUri.getBaseUri(), "multiproductManager", oRelativePath, "product"), "Create a new product for this order", "POST", "Child"));

        /* Finally, truncate the current URI so as to point to the resource manager of which this resource is related.
        Then create the hypermedia links towards the parent resources.*/
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("order","orderManager")).lastIndexOf("/");
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("order","orderManager")).substring(0, iLastSlashIndex), "Create a new order", "POST", "Parent"));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("order","orderManager")).substring(0, iLastSlashIndex), "Get all orders", "GET", "Parent"));
        return oJavaorderModel;
    }
}
