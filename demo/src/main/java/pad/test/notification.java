package pad.test;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.application.Platform;

public class notification {

    public static void sendNotification(){
        //check which radio button is marked, 0 = Popup, 1 = Email, 2 = Text Message
        int notificationType = App.controller.getNotificationMethod();
        if (notificationType == 0){ //popup selected 
            //jump to the main javafx GUI thread to summon the popup window
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    //summon the popup window using 
                    App.controller.popup();   
                }
            });
                
                String logEntry = monitor.getCurrentTime() + " Popup Notification Delivered";
                log.writeLog(logEntry,true);
        }
        else{ //email selected
            sendEmail();
            String logEntry = monitor.getCurrentTime() + " Email Notification Delivered";
            log.writeLog(logEntry,true);
        }


    }


    /* Sends an email to notify the user that their item's price has dropped
     * 
     * NOTE: As of this build, this function can only deliver messages
     *  to amazonmonitor989@gmail.com, a burner account created for this demo
     * 
     *  Access this account with password "rustyNail@Apple"
     * 
     */

    private static void sendEmail(){
    
        //provide recipient's email ID
        String to = "amazonmonitor989@gmail.com";
        //provide sender's email ID
        String from = "mailtrap@demomailtrap.com";
        //sender username
        final String username = "api";
        //sender password
        final String password = "d44902c5088e4ff786371fa956d571b7";
        //provide Mailtrap's host address
        String host = "live.smtp.mailtrap.io";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object
        Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);

        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //set email subject field
            message.setSubject("Your Amazon Shopping just got cheaper!");
            //set the content of the email message
            message.setText("The Amazon item you have been monitoring has decreased in price! Check it out! " + scrape.getItemUrl());
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } 
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}








