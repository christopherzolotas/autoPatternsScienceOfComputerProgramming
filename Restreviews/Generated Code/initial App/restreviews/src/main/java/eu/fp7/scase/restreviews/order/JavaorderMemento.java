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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

import javax.ws.rs.core.UriInfo;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;

import java.util.Iterator;

import eu.fp7.scase.restreviews.product.JavaproductModel;

import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.utilities.HypermediaLink;

@Entity
@Table(name="\"orderMemento\"")
public class JavaorderMemento{

	@Column(name = "\"oldness\"")
	private int oldness;

	@Id
	@GeneratedValue
	@Column(name = "\"mementoid\"")
	private int mementoId;

	public void setmementoId(int mementoId){
    	this.mementoId = mementoId;
	}

    public int getmementoId(){
    	return this.mementoId;
	}

	public void setoldness(int oldness){
    	this.oldness = oldness;
	}

    public int getoldness(){
    	return this.oldness;
	}

	public void createMemento(){
		HibernateController oHibernateController = HibernateController.getHibernateControllerHandle();
		oHibernateController.createJavaorderMemento(this);
		System.out.println("Memento was created successfully");
	}
	
	public void defineMemento(int orderId){
			HibernateController oHibernateController = HibernateController.getHibernateControllerHandle();
			System.out.println("Defining Memento");
			JavaorderMemento oMemento = oHibernateController.getorderMemento(this);
			JavaorderModel oJavaorderModel = new JavaorderModel();
			oJavaorderModel.setorderId(orderId);
			oJavaorderModel = oHibernateController.getorder(oJavaorderModel);

			oMemento.setorderId(oJavaorderModel.getorderId());
			oMemento.setstatus(oJavaorderModel.getstatus());
			oMemento.setcost(oJavaorderModel.getcost());


			oMemento.setoldness(0);

			System.out.println("Trying to update mememto with memento id: "+Integer.toString(oMemento.getmementoId()));
			oHibernateController.updateJavaorderMemento(oMemento);

			updateMementos(oMemento);
			System.out.println("Mementos were updated successfully");
		}

		private void updateMementos(JavaorderMemento oMemento){
			HibernateController oHibernateController = HibernateController.getHibernateControllerHandle();
			ArrayList<JavaorderMemento> mementos = new ArrayList<JavaorderMemento>();
			try{
				mementos = oHibernateController.getorderMementos(mementos);
			}catch(Exception e){
				System.out.println("No Mementos Found. Creating or updating table");
			}
			for( JavaorderMemento m : mementos ){
				System.out.println("Memento "+Integer.toString(m.getmementoId())+" linked to order "+ Integer.toString(m.getorderId()));
				if( m.getorderId() == oMemento.getorderId()){
					System.out.println("Updating memento oldness");
					if(m.getoldness() < 5){
						m.setoldness(m.getoldness()+1);
						oHibernateController.updateJavaorderMemento(m);
					}else{
						oHibernateController.deleteJavaorderMemento(m);
					}
				}
			}
		}

		public JavaorderModel getorderMementoHanlder(int orderId, int oldness){
			HibernateController oHibernateController = HibernateController.getHibernateControllerHandle();
			JavaorderModel oJavaorderModel = new JavaorderModel();
			
			ArrayList<JavaorderMemento> mementos = new ArrayList<JavaorderMemento>();
			try{
				mementos = oHibernateController.getorderMementos(mementos);
			}catch(Exception e){
				System.out.println("No Mementos Found. Creating or updating table");
			}
			
			//Add memento items to the returning model
			for( JavaorderMemento m : mementos ){
				if(m.getoldness() == oldness & m.getorderId() == orderId){
					oJavaorderModel.setstatus(m.getstatus());

					oJavaorderModel.setcost(m.getcost());



					oJavaorderModel.setorderId(orderId);
					break;
				}
			}
        	return oJavaorderModel;
		}

		public JavaorderModel putRestoreorderFromMementoHandler(int orderId, int oldness){
			HibernateController oHibernateController = HibernateController.getHibernateControllerHandle();
			JavaorderModel oJavaorderModel = new JavaorderModel();
			ArrayList<JavaorderMemento> mementos = new ArrayList<JavaorderMemento>();
			oJavaorderModel.setorderId(orderId);
			try{
				mementos = oHibernateController.getorderMementos(mementos);
			}catch(Exception e){
				System.out.println("No Mementos Found. Creating or updating table");
			}
			//check if the order exists in the database. If it exists add add the properties. If not create it again.
			try{
				//this works.
				oJavaorderModel = oHibernateController.getorder(oJavaorderModel);
				System.out.println("Resource found on DB");
					//Add memento items to the returning model
				for( JavaorderMemento m : mementos ){
					if(m.getoldness() == oldness & m.getorderId() == orderId){
						oJavaorderModel.setstatus(m.getstatus());

						oJavaorderModel.setcost(m.getcost());



						break;
					}
				}
				oHibernateController.putorder(oJavaorderModel);
				return oJavaorderModel;
			}catch(Exception e){
				System.out.println("Resource was not found on DB. Posting it again");
				mementos = oHibernateController.getorderMementos(mementos);
				JavaorderModel newoJavaorderModel = new JavaorderModel();
				newoJavaorderModel.setorderId(orderId);
				for( JavaorderMemento m : mementos ){
					if(m.getoldness() == oldness & m.getorderId() == orderId){
						newoJavaorderModel.setstatus(m.getstatus());

						newoJavaorderModel.setcost(m.getcost());



						break;
					}
				}
				newoJavaorderModel = oHibernateController.postorder(newoJavaorderModel);
				this.setorderId(newoJavaorderModel.getorderId());
				System.out.println(Integer.toString(this.getorderId()));
				return newoJavaorderModel;
			}
		}

	//there folows a list of the order properties 
		@Column(name = "\"status\"")
		private String status;

		@Column(name = "\"cost\"")
		private double cost;


		// The Linklist property holds all the hypermedia links to be sent back to the client
		@Transient
		private List<HypermediaLink> linklist = new ArrayList<HypermediaLink>();

		// This property models the One to Many relationship between two resources as it is defined by the Hibernate syntax below.
		@Transient
		private Set<JavaproductModel> SetOfJavaproductModel = new HashSet<JavaproductModel>();
	
	@Column(name = "\"orderid\"")
	private int orderId;

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
		    public String getstatus(){
	        	return this.status;
	    	}
		    public double getcost(){
	        	return this.cost;
	    	}
		    public int getorderId(){
	        	return this.orderId;
	    	}

}


