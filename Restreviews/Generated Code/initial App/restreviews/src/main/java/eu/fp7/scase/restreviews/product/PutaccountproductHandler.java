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
import eu.fp7.scase.restreviews.account.JavaaccountModel;

/* This class processes PUT requests for product resources and creates the hypermedia to be returned to the client*/
public class PutaccountproductHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaproductModel oJavaproductModel;
    private JavaaccountModel oJavaaccountModel;


    public PutaccountproductHandler(int accountId, int productId, JavaproductModel oJavaproductModel, UriInfo oApplicationUri){
        this.oJavaproductModel = oJavaproductModel;
        this.oJavaproductModel.setproductId(productId);
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
        oJavaaccountModel = new JavaaccountModel();
        oJavaaccountModel.setaccountId(accountId);
        oJavaproductModel.setaccount(this.oJavaaccountModel);
    }

	public String calculateProperResourcePath(){
    	if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
        	return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
    	}
    	else{
        	return this.oApplicationUri.getPath();
    	}
	}

    public JavaproductModel putJavaproductModel(){


        return createHypermedia(oHibernateController.putproduct(oJavaproductModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaproductModel createHypermedia(JavaproductModel oJavaproductModel){

        /* Create hypermedia links towards this specific RESTParameter resource. These can be GET, PUT and/or delete depending on what was specified in the service CIM.*/
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get the product", "GET", "Sibling"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Update the product", "PUT", "Sibling"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Delete the product", "DELETE", "Sibling"));

        /* Calculate the relative path towards any related resources of this one. Then add each new hypermedia link with that relative path to the hypermedia linklist to be sent back to client.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath.substring(this.strResourcePath.indexOf("/product") + 1);
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%s", oApplicationUri.getBaseUri(), oRelativePath, "review"), "Get all the reviews of this product", "GET", "Child"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%s", oApplicationUri.getBaseUri(), oRelativePath, "review"), "Create a new review for this product", "POST", "Child"));

        /* Finally, truncate the current URI so as to point to the resource manager of which this resource is related.
        Then create the hypermedia links towards the parent resources.*/
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).lastIndexOf("/");
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).substring(0, iLastSlashIndex), "Create a new product", "POST", "Parent"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), String.format("%s", this.strResourcePath).replaceAll("multiproduct","multiproductManager")).substring(0, iLastSlashIndex), "Get all products of this account", "GET", "Parent"));
        return oJavaproductModel;
    }
}
