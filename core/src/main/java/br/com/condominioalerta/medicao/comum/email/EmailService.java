package br.com.condominioalerta.medicao.comum.email;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import br.com.condominioalerta.medicao.comum.helper.ArquivoHelper;

@Stateless
public class EmailService {

	@Resource(name = "java:/condominioMail")
	private Session session;

	public void send(String addresses, String subject, String textMessage) {
		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			message.setSubject(subject);
			message.setText(textMessage);

			Transport.send(message);
		} catch (MessagingException me) {
			Logger.getLogger(EmailService.class.getName()).log(Level.WARNING, "Cannot send mail", me);
		}
	}

	public void send(String addresses, String subject, String content, String contentType) {
		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			message.setSubject(subject);
			message.setContent(content, contentType);

			Transport.send(message);
		} catch (MessagingException me) {
			Logger.getLogger(EmailService.class.getName()).log(Level.WARNING, "Cannot send mail", me);
		}
	}
	
	public void sendGrid(String addresses, String subject, String content, String contentType, File attachment, String contentId) {
		Email from = new Email("condominioalerta@gmail.com");
		Email to = new Email(addresses);
		Content c = new Content(contentType, content);
		Mail mail = new Mail(from, subject, to, c);
		
		byte[] fileData = ArquivoHelper.converteToByteArray(attachment);
        String contentFile = new String(Base64.getEncoder().encodeToString(fileData));

        Attachments attachments = new Attachments();    
        attachments.setContent(contentFile);
	    attachments.setType("text/csv");
	    attachments.setFilename(attachment.getName());
	    attachments.setDisposition("attachment");
	    attachments.setContentId(contentId);
	    mail.addAttachments(attachments);
		
		SendGrid sendGrid = new SendGrid(System.getProperty("SENDGRID_API_KEY"));

		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			sendGrid.api(request);
		} catch (IOException ioe) {
			Logger.getLogger(EmailService.class.getName()).log(Level.WARNING, "Cannot send mail", ioe);
		}
	}

	public void sendGrid(String addresses, String subject, String content, String contentType) {
		Email from = new Email("condominioalerta@gmail.com");
		Email to = new Email(addresses);
		Content c = new Content(contentType, content);
		Mail mail = new Mail(from, subject, to, c);

		SendGrid sendGrid = new SendGrid(System.getProperty("SENDGRID_API_KEY"));

		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sendGrid.api(request);
			System.out.println(response.statusCode);
			System.out.println(response.body);
			System.out.println(response.headers);
		} catch (IOException ioe) {
			Logger.getLogger(EmailService.class.getName()).log(Level.WARNING, "Cannot send mail", ioe);
		}
	}
}
