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

/* This class processes POST requests for product resources and creates the hypermedia to be returned to the client*/

public class PostaccountproductHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaproductModel oJavaproductModel;
    private JavaaccountModel oJavaaccountModel;

    public PostaccountproductHandler(int accountId, JavaproductModel oJavaproductModel, UriInfo oApplicationUri){
        this.oJavaproductModel = oJavaproductModel;
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

    public JavaproductModel postJavaproductModel(){


        return createHypermedia(oHibernateController.postproduct(oJavaproductModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaproductModel createHypermedia(JavaproductModel oJavaproductModel){

        /* Create hypermedia links towards this specific product resource. These must be GET and POST as it is prescribed in the meta-models.*/
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get all products of this account", "GET", "Sibling"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Create a new product", "POST", "Sibling"));

        /* Then calculate the relative path to any resources that are related of this one and add the according hypermedia links to the Linklist.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath.replaceAll("multiproductManager/", "multiproduct/");
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oJavaproductModel.getproductId()), String.valueOf(oJavaproductModel.gettitle()), "GET", "Child", oJavaproductModel.getproductId()));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oJavaproductModel.getproductId()), String.valueOf(oJavaproductModel.gettitle()), "PUT", "Child", oJavaproductModel.getproductId()));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oJavaproductModel.getproductId()), String.valueOf(oJavaproductModel.gettitle()), "DELETE", "Child", oJavaproductModel.getproductId()));

        /* Finally, calculate the relative path towards the resources of which this one is related.
        Then add hypermedia links for each one of them*/
		oRelativePath = this.strResourcePath.replaceAll("multiproductManager/", "");
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).lastIndexOf("/");
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Delete the parent account", "DELETE", "Parent"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Get the parent account", "GET", "Parent"));
        oJavaproductModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Update the account", "PUT", "Parent"));
        return oJavaproductModel;
    }
}
