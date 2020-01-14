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
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import eu.fp7.scase.restreviewsrefined.utilities.HypermediaLink;
import eu.fp7.scase.restreviewsrefined.utilities.HibernateController;


/* This class processes PUT requests for order resources and creates the hypermedia to be returned to the client*/
public class ObservablePutorderHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaorderModel oJavaorderModel;

	//Observers List
    private ArrayList<JavaorderObserver> Observers = new ArrayList<JavaorderObserver>();


    public ObservablePutorderHandler(int orderId, JavaorderModel oJavaorderModel, UriInfo oApplicationUri){
        this.oJavaorderModel = oJavaorderModel;
        this.oJavaorderModel.setorderId(orderId);
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
		try{
			this.Observers = oHibernateController.getorderObservers(Observers);
		}catch(Exception e){
			System.out.println("No Observers Found. Creating or updating table");
			
		}
    }

	public String calculateProperResourcePath(){
    	if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
        	return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
    	}
    	else{
        	return this.oApplicationUri.getPath();
    	}
	}

	public int registerToVerb(String observerReaction){
		JavaorderObserver oObserver;
		
		oObserver = new JavaorderObserver();
		oObserver.setObserverHTTPAction("PUT");
		oObserver.setType("VERB");
		oObserver.setJavaorderModel(this.oJavaorderModel);
		oObserver.setAction(observerReaction);
		this.oHibernateController.createJavaorderObserver(oObserver);

		return oObserver.getobserverId();
	}
	
	public int registerToInstance(String observerReaction){
		JavaorderObserver oObserver;

		oObserver = new JavaorderObserver();
		oObserver.setObserverHTTPAction("PUT");
		oObserver.setType("INSTANCE");
		oObserver.setJavaorderModel(this.oJavaorderModel);
		oObserver.setAction(observerReaction);
		this.oHibernateController.createJavaorderObserver(oObserver);

		return oObserver.getobserverId();
	}
	
	public void unregister(String observerId){
		try{
			JavaorderObserver oObserver;
			for(JavaorderObserver observer : this.Observers){
				if(observer.getobserverId()==Integer.parseInt(observerId)){
					this.Observers.remove(this.Observers.indexOf(observer));
					this.oHibernateController.deleteJavaorderObserver(observer);
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Observer not found");
		}
	}
	

	public void notifyObservers(){
        for(JavaorderObserver o: Observers){
			if(o.getType().equals("VERB") && o.getObserverHTTPAction().equals("PUT")){
				o.update("updating observer");
				this.oHibernateController.updateJavaorderObserver(o);
			}else{
				/* System.out.println(this.oJavaorderModel.getorderId());
				System.out.println(o.getJavaorderModel().getorderId()); */
				if(o.getJavaorderModel().getorderId() == this.oJavaorderModel.getorderId() && o.getObserverHTTPAction().equals("PUT")){
					o.update("This is a cool instance message");
					this.oHibernateController.updateJavaorderObserver(o);
				}
			}
        }
    
	}

    public JavaorderModel putJavaorderModel(){
		
		notifyObservers();

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
