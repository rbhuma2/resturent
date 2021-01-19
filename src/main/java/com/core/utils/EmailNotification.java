
package com.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.core.mongo.data.entity.BookTableData;

@Component
public class EmailNotification {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from}")
    private String emailFrom;
    
    @Value("${email.owner}")
    private String ownerEmail;

    private static final Logger logger = LogManager.getLogger(EmailNotification.class);

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";

    public void notifyBookingStatus(String pathUrl, HttpHeaders httpHeaders, BookTableData bookTable) {
    	StringBuilder messageContent = new StringBuilder();
    	StringBuilder ownerContent = new StringBuilder();
    	
    	ownerContent.append("The booking details are below. Please check and give confirmation by clicking below Link");
    	
    	messageContent.append("Hi " + bookTable.getEmail().substring(0, bookTable.getEmail().indexOf("@")) +",<br>");
    	
    	messageContent.append("Please find the booking information as below :<br>");
    	messageContent.append("<HTML><Table border=0 cellpadding=5 cellspacing=0>");
    	ownerContent.append("<HTML><Table border=0 cellpadding=5 cellspacing=0>");
    	
        messageContent.append("<tr><td><b>Name:</b> </td><td> " + bookTable.getName() + "</td></tr>");
        messageContent.append("<tr><td><b>Booking Time:</b></td><td> " + bookTable.getDate() +" " +bookTable.getBookTime() + "</td></tr>");
        messageContent.append("<tr><td><b>No of Persons:</b></td><td> " + bookTable.getPersons() + "</td></tr>");
        messageContent.append("<tr><td><b>Status:</b></td><td>" + bookTable.getStatus() + "</td></tr>");
        messageContent.append("</table></html>");
        
        ownerContent.append("<tr><td><b>Name:</b> </td><td> " + bookTable.getName() + "</td></tr>");
        ownerContent.append("<tr><td><b>Booking Time:</b></td><td> " + bookTable.getDate() +" " +bookTable.getBookTime() + "</td></tr>");
        ownerContent.append("<tr><td><b>No of Persons:</b></td><td> " + bookTable.getPersons() + "</td></tr>");
        ownerContent.append("<tr><td><b>Status:</b></td><td>" + bookTable.getStatus() + "</td></tr>");
        ownerContent.append("</table></html>");
        
    	
    	String subject = "Refence Number " + bookTable.getIdentifier() + " " + DateRoutine.currentDateTimeAsStr();
        String notifyTo = bookTable.getEmail();

        sendEmailWithMime(subject, messageContent.toString(), notifyTo, "Table Reservation");
        
        if(httpHeaders !=null) {
        	String approveLink = "http://"+httpHeaders.getHost()+pathUrl+"/" + bookTable.getIdentifier() +"/approve" ;
            ownerContent.append("<a href='"+ approveLink  +"' target=\"_blank\">Approve</a>");
            ownerContent.append("     ");
            String rejectLink = "http://"+httpHeaders.getHost()+pathUrl+"/" + bookTable.getIdentifier() +"/reject" ;
            ownerContent.append("<a href='"+ rejectLink  +"' target=\"_blank\">Reject</a>");
            sendEmailWithMime(subject, ownerContent.toString(), ownerEmail, "Table Reservation");
        }
        
        
        
    }
        

    private void sendEmailWithMime(String subject, String content, String notifyTo, String notifyFrom) {

        String[] notifyToList = getEmailSubscriptionList(notifyTo);
        if (notifyToList == null) {
            return;
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(content, "text/html");
            messageHelper.setTo(notifyToList);
            messageHelper.setSubject(subject);
            messageHelper.setFrom(emailFrom, notifyFrom);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String[] getEmailSubscriptionList(String notifyTo) {
        if (notifyTo == null || notifyTo.isEmpty()) {
            logger.debug("Subscription List Empty. No Email sent");
            return new String[0];
        }
        List<String> notiyToList = new ArrayList<>();
        notiyToList.addAll(Arrays.asList(notifyTo.split(",")));

        /*
         * Validate Emails in list
         */
        for (Iterator<String> notifyToListIterator = notiyToList.listIterator(); notifyToListIterator.hasNext();) {
            String emailId = notifyToListIterator.next();
            if (!Formatter.isValidEmailAddress(emailId.trim())) {
                notifyToListIterator.remove();
            }
        }

        if (notiyToList.isEmpty()) {
            logger.debug("Subscription List has invalid emails. No Email sent");
            return new String[0];
        }
        return notiyToList.toArray(new String[0]);
    }

    @Bean
    public JavaMailSender javaMailSender(@Value("${email.host}") String host, @Value("${email.user}") String userName,
            @Value("${email.password}") String password, @Value("${email.protocol}") String protocol,
            @Value("${email.port}") String port, @Value("${email.smtp.auth}") String smtpAuth,
            @Value("${email.starttls.enable}") String enableStartTLS) {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();

        Properties mailProps = new Properties();
        mailProps.put(MAIL_SMTP_AUTH, Boolean.parseBoolean(smtpAuth));
        mailProps.put(MAIL_SMTP_STARTTLS, Boolean.parseBoolean(enableStartTLS));

        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setUsername(userName);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setProtocol(protocol);
        javaMailSenderImpl.setPort(port != null ? Integer.parseInt(port) : -1);
        javaMailSenderImpl.setJavaMailProperties(mailProps);
        
        return javaMailSenderImpl;
    }

}
