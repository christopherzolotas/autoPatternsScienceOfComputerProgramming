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


package eu.fp7.scase.restreviewsrefined.utilities;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import eu.fp7.scase.restreviewsrefined.review.JavareviewController;
import eu.fp7.scase.restreviewsrefined.product.JavaproductController;
import eu.fp7.scase.restreviewsrefined.account.JavaaccountController;
import eu.fp7.scase.restreviewsrefined.order.JavaorderController;
import eu.fp7.scase.restreviewsrefined.review.JavareviewControllerManager;
import eu.fp7.scase.restreviewsrefined.product.JavaproductControllerManager;
import eu.fp7.scase.restreviewsrefined.account.JavaaccountControllerManager;
import eu.fp7.scase.restreviewsrefined.order.JavaorderControllerManager;
import eu.fp7.scase.restreviewsrefined.payOrder.JavaAlgopayOrderController;
import eu.fp7.scase.restreviewsrefined.searchProduct.JavaAlgosearchProductController;

/* This class is responsible to publish any resource controllers that can handle incoming HTTP requests
to the web service container (Jetty etc)*/

@ApplicationPath("/api/")
public class JAXRSPublisher extends Application{

    public JAXRSPublisher(){}

    /* This function returns to the container (Jetty, Tomcat etc) the classes that expose any web API*/
    @Override
    public Set<Class<?>> getClasses(){
        HashSet<Class<?>> SetOfClasses = new HashSet<Class<?>>();
		SetOfClasses.add(JavareviewController.class);
		SetOfClasses.add(JavaproductController.class);
		SetOfClasses.add(JavaaccountController.class);
		SetOfClasses.add(JavaorderController.class);
		SetOfClasses.add(JavareviewControllerManager.class);
		SetOfClasses.add(JavaproductControllerManager.class);
		SetOfClasses.add(JavaaccountControllerManager.class);
		SetOfClasses.add(JavaorderControllerManager.class);
		SetOfClasses.add(JavaAlgopayOrderController.class);
		SetOfClasses.add(JavaAlgosearchProductController.class);

        return SetOfClasses;
    }

    @Override
    public Set<Object> getSingletons(){
        return new HashSet<Object>();
    }
}
