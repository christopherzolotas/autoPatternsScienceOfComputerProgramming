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


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import eu.fp7.scase.restreviewsrefined.review.JavareviewModel;
import eu.fp7.scase.restreviewsrefined.product.JavaproductModel;
import eu.fp7.scase.restreviewsrefined.account.JavaaccountModel;
import eu.fp7.scase.restreviewsrefined.order.JavaorderModel;
import eu.fp7.scase.restreviewsrefined.account.JavaaccountModelRepresentation;
import eu.fp7.scase.restreviewsrefined.product.JavaproductObserver;
import eu.fp7.scase.restreviewsrefined.order.JavaorderObserver;



/* This class follows the singleton pattern in order to build once and provide a unique hibernate session instance*/

public class HibernateUtil{

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try {
        /* Create the unique hibernate session. All resource models should be added here.*/
            return new AnnotationConfiguration().configure()
					.addAnnotatedClass(JavareviewModel.class)
					.addAnnotatedClass(JavaproductModel.class)
					.addAnnotatedClass(JavaproductObserver.class)
					.addAnnotatedClass(JavaaccountModel.class)
					.addAnnotatedClass(JavaorderModel.class)
					.addAnnotatedClass(JavaorderObserver.class)
                    .buildSessionFactory();
        }
        catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
