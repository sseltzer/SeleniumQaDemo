
package com.sseltzer.selenium.framework.utility.email;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

import com.google.common.base.Joiner;

/**
 * Email.java sends mail through the valpak.selenium gmail account. 
 * 
 * It is can be used to issue emails to the relevant parties when 
 * a Team City stack trace wouldn't be appropriate (for instance, 
 * alerting a BA to missing content on a page.) 
 *
 * Usage: 
 * 
 * <pre> 
 *   new Email()
 *     .to("email1@email.com", "email2@email.com", etc..)
 *     .subject("My subject")
 *     .body("My message")
 *     .send(); 
 * </pre>
 * 
 * @author ckiehl Sep 16, 2014
 */
public class Email {
	/*
	private Properties _props;
	private Session _session;
	
	private List<String> _to = null;
	private String _subject = null;
	private String _body = null;
	
	public Email() {
		this._props = new Properties();
		_props.put("mail.smtp.host", "smtp.gmail.com");
		_props.put("mail.smtp.socketFactory.port", "465");
		_props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		_props.put("mail.smtp.auth", "true");
		_props.put("mail.smtp.port", "465");
		
		this._session = Session.getDefaultInstance(_props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("valpak.selenium111","selenium111");
			}
		});
	}
	
	public Email to(String... emails) {
		this._to = Arrays.asList(emails);
		return this; 
	}
	
	public Email subject(String subject) {
		this._subject = subject;
		return this;
	}
	
	public Email body(String body) {
		this._body = body; 
		return this; 
	}
	
	public void send() {
		if (this._to == null 
			|| this._subject == null 
			|| this._body == null) {
			throw new IllegalArgumentException("\n\nFields not properly initiallized");	
		}
		try {
			Message message = new MimeMessage(this._session);
			message.setFrom(new InternetAddress("valpak.selenium111@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(this.formatEmails(this._to)));
			message.setSubject(this._subject);
			message.setText(this._body);
 
			Transport.send(message);
			System.out.println("Email sent.");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String formatEmails(List<String> emailList) {
		return Joiner.on(",").join(emailList);
	}*/
} 