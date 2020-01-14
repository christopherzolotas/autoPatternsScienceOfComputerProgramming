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

package eu.fp7.scase.restreviews.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Entity
@Table(name="\"reviewObserver\"")
public class JavareviewObserver{

	@Id
	@Column(name = "\"observerId\"")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int observerId;
	
	@Column(name = "\"HTTPAction\"")
	private String strHTTPAction;
	
	@Column(name = "\"name\"")
    private String name;
	
	@Column(name = "\"type\"")
	private String type; //verb or resource instance
	
	@Column(name = "\"action\"")
	private String action;
	
	@Column(name = "\"actionDone\"")
	private boolean actionDone; //verb or resource instance
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reviewId")
	private JavareviewModel observableJavareviewModel = new JavareviewModel();

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public JavareviewObserver(int Id){
		this.observerId = Id;
	}
	
	public JavareviewObserver(){
		
	}

	//This function can/will maybe be replaced with a callback function
    public void update(String msg){
        //Do something
		System.out.println("----------------------------------------------");
        System.out.println("HTTPAction was fired: " + strHTTPAction);
		System.out.println("Observer Type: " + type);
        System.out.println("Message from Observable Activity: " + msg);
		System.out.println("----------------------------------------------");
		setActionDone(true);
		if(!action.equals("none")){
			try{
				generateAndSendEmail(this.getAction(),this.getObserverHTTPAction(),Integer.toString(getJavareviewModel().getreviewId()));
				System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
			}
			catch(Exception e){
				
			}
		}
		
    }
	
	public static void generateAndSendEmail(String email, String oHTTPAction, String id) throws AddressException, MessagingException {
	 
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject("Notification - " + oHTTPAction + " was fired");
		String emailBody ;
		if(oHTTPAction.equals("POST")){
			emailBody = "Hello, <br> A " + oHTTPAction + " HTTP request was fired at the review resource. <br><br> Regards, <br>MDE Admin <br><br> </small>This is an automated mail service. Please do not respond to this email.</small>";
		}else{
			emailBody = "Hello, <br> A " + oHTTPAction + " HTTP request was fired at the review resource with id " + id + ". <br><br> Regards, <br>MDE Admin <br><br> <small>This is an automated mail service. Please do not respond to this email.</small>";
		}
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "dontsiosrestfulservice@gmail.com", "restfulservicepass");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

    public void setObserverHTTPAction(String HTTPAction){
        this.strHTTPAction = HTTPAction;
    }

	public String getObserverHTTPAction(){
        return this.strHTTPAction;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
	
	public int getobserverId(){
        return this.observerId;
    }

    public void setobserverId(int observerId){
        this.observerId = observerId;
    }
	
	public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }
	
	public String getAction(){
        return this.action;
    }

    public void setAction(String action){
        this.action = action;
    }
	
	public boolean getActionDone(){
        return this.actionDone;
    }

    public void setActionDone(boolean actionDone){
        this.actionDone = actionDone;
    }
	
	
	public JavareviewModel getJavareviewModel(){
        return this.observableJavareviewModel;
    }

    public void setJavareviewModel(JavareviewModel oJavareviewModel){
        this.observableJavareviewModel = oJavareviewModel;
    }

}


