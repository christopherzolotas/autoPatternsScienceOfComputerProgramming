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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import org.hibernate.Session;

import eu.fp7.scase.restreviewsrefined.utilities.HypermediaLink;
import eu.fp7.scase.restreviewsrefined.product.JavaproductModel;


/* This class models the data of a order resource. It is enhanced with JAXB annotations for automated representation
parsing/marshalling as well as with Hibernate annotations for ORM transformations.*/
@XmlRootElement
@Entity
@Table(name="\"order\"")
public class JavaorderModel{


    /* There follows a list with the properties that model the order resource, as prescribed in the service CIM*/
		@Column(name = "\"status\"")
		private String status;

		@Column(name = "\"cost\"")
		private double cost;

		@Id
		@GeneratedValue
		@Column(name = "\"orderid\"")
		private int orderId;

		// The Linklist property holds all the hypermedia links to be sent back to the client
		@Transient
		private List<HypermediaLink> linklist = new ArrayList<HypermediaLink>();

		// This property models the One to Many relationship between two resources as it is defined by the Hibernate syntax below.
		@OneToMany(fetch = FetchType.EAGER, mappedBy="order",orphanRemoval=true)
		@OnDelete(action=OnDeleteAction.CASCADE)
		private Set<JavaproductModel> SetOfJavaproductModel = new HashSet<JavaproductModel>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy="observableJavaorderModel",orphanRemoval=true)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Set<JavaorderObserver> SetOfJavaorderObserver = new HashSet<JavaorderObserver>();
	
	@Transient
	private int observerId;

	public int getobserverId(){
    	return this.observerId;
	}

	public void setobserverId(int id){
    	this.observerId = id;
	}

    /* There follows a list of setter and getter functions.*/
	    public void setstatus(String status){
        	this.status = status;
    	}

	    public void setcost(double cost){
        	this.cost = cost;
    	}

	    public void setorderId(int orderId){
        	this.orderId = orderId;
    	}

	    public void setlinklist(List<HypermediaLink> linklist){
        	this.linklist = linklist;
    	}

		@XmlTransient
	    public void setSetOfJavaproductModel(Set<JavaproductModel> SetOfJavaproductModel){
        	this.SetOfJavaproductModel = SetOfJavaproductModel;
    	}

	    public String getstatus(){
        	return this.status;
    	}

	    public double getcost(){
        	return this.cost;
    	}

	    public int getorderId(){
        	return this.orderId;
    	}

	    public List<HypermediaLink> getlinklist(){
        	return this.linklist;
    	}

	    public Set<JavaproductModel> getSetOfJavaproductModel(){
        	return this.SetOfJavaproductModel;
    	}


    /* This function deletes explicitly any collections of this resource that are stored in the database 
    and iteratively does so for any subsequent related resources.
    NOTE: this function is needed to handle erroneous handling of cascade delete of some hibernate versions.*/
    public void deleteAllCollections(Session hibernateSession){


        Iterator<JavaproductModel> JavaproductModelIterator = SetOfJavaproductModel.iterator();
        while(JavaproductModelIterator.hasNext()){
            JavaproductModelIterator.next().deleteAllCollections(hibernateSession);
        }
    }
}
