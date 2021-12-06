package com.catering.msgsender;

import java.util.Hashtable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSender {
	


	public final static String JNDI_FACTORY = "com.sun.jndi.fscontext.RefFSContextFactory";
	private QueueConnectionFactory connectionFactory;
	private QueueConnection        connection;
	private QueueSession           session;
	private QueueSender			   sender;
	private TextMessage            message;
	private javax.jms.Queue	       destination;
	private InitialContext 		   ctx;
	@Getter@Setter
	private String bindFile;
	@Getter@Setter
	private String queueFact;
	private	TopicConnection topicCon = null;
	private Topic topic = null;
	
	@SuppressWarnings("unchecked")
	public boolean sendMessageToDestination(String sendMsg,String destnName,boolean isQueue) {
		boolean isMessageSent = false;
	    try {
	    	
	       	Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, bindFile);
			ctx = new InitialContext(env);
			connectionFactory = (QueueConnectionFactory) ctx.lookup(queueFact);
			if(isQueue){
				log.info("Get Queue connection..");
				log.info("Sending output xml to queue");
				connection = (QueueConnection) connectionFactory.createQueueConnection();
				session = (QueueSession) connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
				destination = (javax.jms.Queue) ctx.lookup(destnName);
				sender = (QueueSender) session.createSender(destination);
				sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				message = (TextMessage) session.createTextMessage();
				message.setText(sendMsg);
				sender.send(message);
				isMessageSent = true;
				log.info( "Message "+message+"sent to --->"+ destnName);
			}
			else {
				TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup(queueFact);
				topicCon = tcf.createTopicConnection();
			    TopicSession topicSession = topicCon.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
		      	topic = (Topic) ctx.lookup(destnName); 
		    	TopicPublisher publisher = topicSession.createPublisher( topic );
		      	Message msg = topicSession.createTextMessage(sendMsg);
		      	publisher.publish(msg);
		      	isMessageSent = true;
		      	log.info( "Message "+message+"sent to --->"+ destnName);
			}
	    }catch(JMSException e){
			log.info("Exception in JMS"+e);
			return false;
	    } catch(Exception e){	    	
	    	
	    	log.info("Exception occured while trying to send message to queue"+e.getMessage());
	    }
	    finally {
	    	if (ctx != null){
				try {
					ctx.close();
				} catch (NamingException e) {
					log.info("Failed to close context", e);
				}
			}
	       if( topicCon != null ) {
	        try {
				topicCon.close();
			} catch (JMSException e) {
				log.info("Exception occured while trying to topic a queue connection"+e.getMessage());
			}
	      }
	      if(connection != null){
	    	  try {	    	 
	    		  connection.close(); 
	    	  } catch (JMSException e) { 
	    		  log.info("Exception occured while trying to close a queue connection"+e.getMessage());
	    	  } 
	    }
	  }
		return isMessageSent;
   	
  }

	


}
