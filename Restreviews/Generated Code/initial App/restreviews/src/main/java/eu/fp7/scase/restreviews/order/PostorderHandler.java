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


package eu.fp7.scase.restreviews.order;


import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;


/* This class processes POST requests for order resources and creates the hypermedia to be returned to the client*/

public class PostorderHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaorderModel oJavaorderModel;

    public PostorderHandler(JavaorderModel oJavaorderModel, UriInfo oApplicationUri){
        this.oJavaorderModel = oJavaorderModel;
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

    public JavaorderModel postJavaorderModel(){


        return createHypermedia(oHibernateController.postorder(oJavaorderModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaorderModel createHypermedia(JavaorderModel oJavaorderModel){

        /* Create hypermedia links towards this specific order resource. These must be GET and POST as it is prescribed in the meta-models.*/
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get all orders", "GET", "Sibling"));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Create a new order", "POST", "Sibling"));

        /* Then calculate the relative path to any resources that are related of this one and add the according hypermedia links to the Linklist.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath;
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oJavaorderModel.getorderId()), String.valueOf(oJavaorderModel.getstatus()), "GET", "Child", oJavaorderModel.getorderId()));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oJavaorderModel.getorderId()), String.valueOf(oJavaorderModel.getstatus()), "PUT", "Child", oJavaorderModel.getorderId()));
        return oJavaorderModel;
    }
}
