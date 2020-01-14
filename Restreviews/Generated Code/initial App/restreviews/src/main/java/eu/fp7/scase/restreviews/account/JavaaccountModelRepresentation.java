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

package eu.fp7.scase.restreviews.account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.Session;
import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.product.JavaproductModel;


@XmlRootElement
public class JavaaccountModelRepresentation{

	/* There follows a list with the properties that model the account resource, as prescribed in the service CIM*/
		private String representationName; // account_propertyi_propertyj_propertyk used as query parameter
		private String email;




		private String username;




		// The Linklist property holds all the hypermedia links to be sent back to the client
		private List<HypermediaLink> linklist = new ArrayList<HypermediaLink>();


		private Set<JavaproductModel> SetOfJavaproductModel = new HashSet<JavaproductModel>();

		private int accountId;

		/* There follows a list of setter, getter and initialization functions.*/
		public JavaaccountModelRepresentation(){
			this.representationName="account";
			this.email = null;

			this.username = null;


		}

		//constructors
		public JavaaccountModelRepresentation representationName(String representationName){
			this.representationName = representationName;
			return this;
		}

		public JavaaccountModelRepresentation build(){
			return new JavaaccountModelRepresentation(this);
		}

		private JavaaccountModelRepresentation(JavaaccountModelRepresentation oJavaaccountModelRepresentation){
			this.email = oJavaaccountModelRepresentation.email;

			this.username = oJavaaccountModelRepresentation.username;


		}

	    public void setemail(String email){
        	this.email = email;
    	}
		public JavaaccountModelRepresentation email(String email){
        	this.email = email;
			this.representationName = this.representationName+"_email";
			return this;
		}

	    public void setpassword(String password){
        	this.password = password;
    	}
		public JavaaccountModelRepresentation password(String password){
        	this.password = password;
			return this;
		}

	    public void setusername(String username){
        	this.username = username;
    	}
		public JavaaccountModelRepresentation username(String username){
        	this.username = username;
			this.representationName = this.representationName+"_username";
			return this;
		}

	    public void setaccountId(int accountId){
        	this.accountId = accountId;
    	}
		public JavaaccountModelRepresentation accountId(int accountId){
        	this.accountId = accountId;
			return this;
		}

	    public void setlinklist(List<HypermediaLink> linklist){
        	this.linklist = linklist;
    	}
		public JavaaccountModelRepresentation linklist(List<HypermediaLink> linklist){
        	this.linklist = linklist;
			return this;
		}  

	    public void setSetOfJavaproductModel(Set<JavaproductModel> SetOfJavaproductModel){
        	this.SetOfJavaproductModel = SetOfJavaproductModel;
    	}
		public JavaaccountModelRepresentation SetOfJavaproductModel(Set<JavaproductModel> SetOfJavaproductModel){
        	this.SetOfJavaproductModel = SetOfJavaproductModel;
			return this;
		}

	    public String getemail(){
        	return this.email;
    	}
	    public String getpassword(){
        	return this.password;
    	}
	    public String getusername(){
        	return this.username;
    	}
	    public int getaccountId(){
        	return this.accountId;
    	}
	    public List<HypermediaLink> getlinklist(){
        	return this.linklist;
    	}
	    public Set<JavaproductModel> getSetOfJavaproductModel(){
        	return this.SetOfJavaproductModel;
    	}

		//public int getaccountId(){
        //	return this.accountId;
    	//}
		
		public void setaccountRepresentationName(String representationName){
        	this.representationName = representationName;
    	} 
	
		public String getaccountRepresentationName(){
        	return this.representationName;
    	}

}


