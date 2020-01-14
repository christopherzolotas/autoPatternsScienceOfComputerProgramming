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
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas
* Contact			   : christopherzolotas@issel.ee.auth.gr
*/


package eu.fp7.scase.restreviews.product;


import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;

/* This class processes DELETE requests for product resources and creates the hypermedia to be returned to the client*/
public class DeleteaccountproductHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaproductModel oJavaproductModel;

    public DeleteaccountproductHandler(int productId, UriInfo oApplicationUri){
        oJavaproductModel = new JavaproductModel();
        oJavaproductModel.setproductId(productId);
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

    public JavaproductModel deleteJavaproductModel(){
 

        return createHypermedia(oHibernateController.deleteproduct(oJavaproductModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaproductModel createHypermedia(JavaproductModel oJavaproductModel){

 	   /* Since both this resource and the related of it are deleted, there are created hypermedia links only towards the resources of which this one is related.*/
    	int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).lastIndexOf("/");
    	oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).substring(0, iLastSlashIndex), "Create a new product", "POST", "Parent"));
	    oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).substring(0, iLastSlashIndex), "Get all products of this account", "GET", "Parent"));
	    return oJavaproductModel;
	}
}
