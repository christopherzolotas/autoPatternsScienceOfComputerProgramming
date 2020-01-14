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


package eu.fp7.scase.restreviews.review;


import javax.ws.rs.core.UriInfo;

import java.util.Iterator;
import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.product.JavaproductModel;

/* This class processes GET requests for review resources and creates the hypermedia to be returned to the client*/
public class GetreviewListHandler{

    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaproductModel oJavaproductModel;

    public GetreviewListHandler(int productId, UriInfo oApplicationUri){
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
        oJavaproductModel = new JavaproductModel();
        oJavaproductModel.setproductId(productId);
    }

	public String calculateProperResourcePath(){
    	if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
        	return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
    	}
    	else{
        	return this.oApplicationUri.getPath();
    	}
	}

    public JavareviewModelManager getJavareviewModelManager(){


        oJavaproductModel = oHibernateController.getreviewList(oJavaproductModel);
        return createHypermedia(oJavaproductModel);
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavareviewModelManager createHypermedia(JavaproductModel oJavaproductModel){
        JavareviewModelManager oJavareviewModelManager = new JavareviewModelManager();

        /* Create hypermedia links towards this specific review resource. These must be GET and POST as it is prescribed in the meta-models.*/
        oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get all reviews of this product", "GET", "Sibling"));
        oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Create a new review", "POST", "Sibling"));

        /* Then calculate the relative path to any related resource of this one and add for each one a hypermedia link to the Linklist.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath;
        Iterator<JavareviewModel> setIterator = oJavaproductModel.getSetOfJavareviewModel().iterator();
        while(setIterator.hasNext()){
            JavareviewModel oNextJavareviewModel = new JavareviewModel();
            oNextJavareviewModel = setIterator.next();
            oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oNextJavareviewModel.getreviewId()), String.valueOf(oNextJavareviewModel.gettitle()), "GET", "Child", oNextJavareviewModel.getreviewId()));
        }

        /* Finally calculate the relative path towards the resources of which this one is related and add one hypermedia link for each one of them in the Linklist.*/
		if(this.oJavaproductModel.getaccount() != null){
        	oRelativePath = String.format("multiproduct/account/%d/%s", this.oJavaproductModel.getaccount().getaccountId(), this.strResourcePath);
		}

        else		if(this.oJavaproductModel.getorder() != null){
        	oRelativePath = String.format("multiproduct/order/%d/%s", this.oJavaproductModel.getorder().getorderId(), this.strResourcePath);
		}
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).lastIndexOf("/");
        oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Delete the parent JavaproductModel", "DELETE", "Parent"));
        oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Get the parent JavaproductModel", "GET", "Parent"));
        oJavareviewModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Update the JavaproductModel", "PUT", "Parent"));
        return oJavareviewModelManager;
    }
}
