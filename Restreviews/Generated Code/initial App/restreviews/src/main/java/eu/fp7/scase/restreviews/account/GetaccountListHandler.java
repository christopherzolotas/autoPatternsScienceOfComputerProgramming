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


package eu.fp7.scase.restreviews.account;


import javax.ws.rs.core.UriInfo;

import java.util.Iterator;
import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import java.util.Set;
import java.util.HashSet;

/* This class processes GET requests for account resources and creates the hypermedia to be returned to the client*/

public class GetaccountListHandler{

    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private Set<JavaaccountModel> SetOfaccountModel = new HashSet<JavaaccountModel>(); // Since this resource is not related of any other, this HashSet will hold the list to be sent back to client.

    public GetaccountListHandler(UriInfo oApplicationUri){
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

    public JavaaccountModelManager getJavaaccountModelManager(){


        this.SetOfaccountModel = oHibernateController.getaccountList(this.SetOfaccountModel);
        return createHypermedia();
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaaccountModelManager createHypermedia(){
        JavaaccountModelManager oJavaaccountModelManager = new JavaaccountModelManager();

        /* Create hypermedia links towards this specific account resource. These must be GET and POST as it is prescribed in the meta-models.*/
        oJavaaccountModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get all accounts", "GET", "Sibling"));
        oJavaaccountModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Create a new account", "POST", "Sibling"));

        /* Then calculate the relative path to any related resource of this one and add for each one a hypermedia link to the Linklist.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath;
        Iterator<JavaaccountModel> setIterator = this.SetOfaccountModel.iterator();
        while(setIterator.hasNext()){
            JavaaccountModel oNextJavaaccountModel = new JavaaccountModel();
            oNextJavaaccountModel = setIterator.next();
            oJavaaccountModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oNextJavaaccountModel.getaccountId()), String.valueOf(oNextJavaaccountModel.getusername()), "GET", "Child", oNextJavaaccountModel.getaccountId()));
        }
        return oJavaaccountModelManager;
    }
}
