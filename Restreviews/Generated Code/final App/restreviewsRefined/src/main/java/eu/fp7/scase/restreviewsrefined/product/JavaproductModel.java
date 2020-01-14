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


package eu.fp7.scase.restreviewsrefined.product;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.ForeignKey;

import eu.fp7.scase.restreviewsrefined.utilities.HypermediaLink;
import eu.fp7.scase.restreviewsrefined.review.JavareviewModel;
import eu.fp7.scase.restreviewsrefined.account.JavaaccountModel;
import eu.fp7.scase.restreviewsrefined.order.JavaorderModel;


/* This class models the data of a product resource. It is enhanced with JAXB annotations for automated representation
parsing/marshalling as well as with Hibernate annotations for ORM transformations.*/
@XmlRootElement
@Entity
@Table(name="\"product\"")
public class JavaproductModel{


    /* There follows a list with the properties that model the product resource, as prescribed in the service CIM*/
		@ElementCollection(fetch = FetchType.EAGER)
		@CollectionTable(name="productcategory", joinColumns=@JoinColumn(name="productId"))
		@Column(name = "\"category\"")
		@ForeignKey(name = "fk_product_category")
		private Set<String> category = new HashSet<String>();

		@Column(name = "\"title\"")
		private String title;

		@Column(name = "\"image\"")
		private String image;

		@Column(name = "\"description\"")
		private String description;

		@Column(name = "\"status\"")
		private String status;

		@Id
		@GeneratedValue
		@Column(name = "\"productid\"")
		private int productId;

		// The Linklist property holds all the hypermedia links to be sent back to the client
		@Transient
		private List<HypermediaLink> linklist = new ArrayList<HypermediaLink>();

		// This property models the Many to One relationship between two resources as it is defined by the Hibernate syntax below.
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="accountId")
		@ForeignKey(name = "fk_account_product")
		private JavaaccountModel account;

		// This property models the Many to One relationship between two resources as it is defined by the Hibernate syntax below.
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="orderId")
		@ForeignKey(name = "fk_order_product")
		private JavaorderModel order;

		// This property models the One to Many relationship between two resources as it is defined by the Hibernate syntax below.
		@OneToMany(fetch = FetchType.EAGER, mappedBy="product",orphanRemoval=true)
		@OnDelete(action=OnDeleteAction.CASCADE)
		private Set<JavareviewModel> SetOfJavareviewModel = new HashSet<JavareviewModel>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy="observableJavaproductModel",orphanRemoval=true)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Set<JavaproductObserver> SetOfJavaproductObserver = new HashSet<JavaproductObserver>();
	
	@Transient
	private int observerId;

	public int getobserverId(){
    	return this.observerId;
	}

	public void setobserverId(int id){
    	this.observerId = id;
	}

    /* There follows a list of setter and getter functions.*/
	    public void setcategory(Set<String> category){
        	this.category = category;
    	}

	    public void settitle(String title){
        	this.title = title;
    	}

	    public void setimage(String image){
        	this.image = image;
    	}

	    public void setdescription(String description){
        	this.description = description;
    	}

	    public void setstatus(String status){
        	this.status = status;
    	}

	    public void setproductId(int productId){
        	this.productId = productId;
    	}

	    public void setlinklist(List<HypermediaLink> linklist){
        	this.linklist = linklist;
    	}

		@XmlTransient
	    public void setaccount(JavaaccountModel account){
        	this.account = account;
    	}

		@XmlTransient
	    public void setorder(JavaorderModel order){
        	this.order = order;
    	}

		@XmlTransient
	    public void setSetOfJavareviewModel(Set<JavareviewModel> SetOfJavareviewModel){
        	this.SetOfJavareviewModel = SetOfJavareviewModel;
    	}

	    public Set<String> getcategory(){
        	return this.category;
    	}

	    public String gettitle(){
        	return this.title;
    	}

	    public String getimage(){
        	return this.image;
    	}

	    public String getdescription(){
        	return this.description;
    	}

	    public String getstatus(){
        	return this.status;
    	}

	    public int getproductId(){
        	return this.productId;
    	}

	    public List<HypermediaLink> getlinklist(){
        	return this.linklist;
    	}

	    public JavaaccountModel getaccount(){
        	return this.account;
    	}

	    public JavaorderModel getorder(){
        	return this.order;
    	}

	    public Set<JavareviewModel> getSetOfJavareviewModel(){
        	return this.SetOfJavareviewModel;
    	}


    /* This function deletes explicitly any collections of this resource that are stored in the database 
    and iteratively does so for any subsequent related resources.
    NOTE: this function is needed to handle erroneous handling of cascade delete of some hibernate versions.*/
    public void deleteAllCollections(Session hibernateSession){

        Query productcategoryQuery = hibernateSession.createSQLQuery(String.format("DELETE FROM %s where %sId = %d","productcategory".toLowerCase(),"product",this.getproductId()));
        productcategoryQuery.executeUpdate();

    }
}
