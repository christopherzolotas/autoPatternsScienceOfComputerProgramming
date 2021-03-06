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
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas / Design patterns implementations created by Dontsios Dimitrios
* Contact			   : christopherzolotas@issel.ee.auth.gr / dontsios@gmail.com
*/


package eu.fp7.scase.restreviews.order;


import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;

/* This class processes GET requests for order resources and creates the hypermedia to be returned to the client*/
public class GetorderHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaorderModel oJavaorderModel;

    public GetorderHandler(int orderId,  UriInfo oApplicationUri){
        oJavaorderModel = new JavaorderModel();
        oJavaorderModel.setorderId(orderId);
        
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


    



	
	public JavaorderModel getJavaorderModel(){


        return createHypermedia(oHibernateController.getorder(oJavaorderModel));
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
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).lastIndexOf("/");
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).substring(0, iLastSlashIndex), "Create a new order", "POST", "Parent"));
        oJavaorderModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).substring(0, iLastSlashIndex), "Get all orders", "GET", "Parent"));
        return oJavaorderModel;
    }
}

/*
1st try
DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@16594479DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@2811cde7DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@11eafb54DesignPatternsLayerPSM.impl.AnnJavaAlgoResourceModelImpl@2f33a2e3DesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@1ea786bdDesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@23f37949DesignPatternsLayerPSM.impl.AnnJavaHTTPActivityHandlerImpl@82b778b

2nd try
DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@16594479

wtf is going on here
DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@16594479

wtf is going on here 2
true

wtf is going on here 3
DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@2811cde7

wtf is going on here 4
false


3rd
DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@11eafb54DesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@1ea786bdDesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@2811cde7DesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@23f37949DesignPatternsLayerPSM.impl.AnnJavaResourceModelImpl@16594479DesignPatternsLayerPSM.impl.AnnJavaHTTPActivityHandlerImpl@82b778bDesignPatternsLayerPSM.impl.AnnJavaAlgoResourceModelImpl@2f33a2e3

Does it recognise AnnPSMCOMPONENTPROPERTY
true

AnnPSMComponentProperty list?
DesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@1ea786bdDesignPatternsLayerPSM.impl.AnnPSMComponentPropertyImpl@23f37949

test
DesignPatternsLayerPSM.impl.JavaBuilderPatternImpl@78e52e87

test 2 correct
1

test 3 correct
DesignPatternsLayerPSM.impl.JavaConcreteBuilderImpl@1ebe17c9

test 4 correct
DesignPatternsLayerPSM.impl.JavaConcreteBuilderImpl@1ebe17c9

test 5 correct
DesignPatternsLayerPSM.impl.JavaConcreteBuilderImpl@1ebe17c9

test6 correct
	account

test7 correct
	RESTfulServicePSM.impl.PSMComponentPropertyImpl@21ec86e6 (name: email, type: String, bIsUnique: true, bIsNamingProperty: false, bIsPrimaryIdentifier: false)RESTfulServicePSM.impl.PSMComponentPropertyImpl@2908fc20 (name: username, type: String, bIsUnique: true, bIsNamingProperty: true, bIsPrimaryIdentifier: false)

test8
	emailusername

test9 corrext
		email
		username

*/

