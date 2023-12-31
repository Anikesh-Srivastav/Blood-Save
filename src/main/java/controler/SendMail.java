package controler;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import bean.Donaterchoise;



@WebServlet(urlPatterns = "/SendMail")
public class SendMail extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String demail = req.getParameter("id");
		System.out.println(demail);
		
		HttpSession httpSession = req.getSession();
		String uemail =  (String) httpSession.getAttribute("uemail");
		
		System.out.println("Demail: "+demail);
		System.out.println("UEmail: "+uemail);
		
		Donaterchoise ad = new Donaterchoise();
		ad.setCustomer_email(uemail);
		ad.setDonater_email(demail);
	
		
		try {
			
			int response = ad.addRequest();
			
			if(response == 1)
			{
				try {
				
					sendMessage(demail);
				
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
			
	}
	
	public void sendMessage(String email) throws SQLException
	{
		
		
	
        String to = email;
       
        String from = "anup@euphoriagenx.in";
        // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
        String host = "smtp.gmail.com";
        
       
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        
      
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("anikeshsrivastab@gmail.com", "qepzzxbvwvzawani");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            
            MimeMessage message1 = new MimeMessage(session);
            
            message1.setFrom(new InternetAddress(from));
           
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
           
            message1.setSubject("CHECK DASHBOARD");
            
            message1.setText("http://localhost:8181/New/Donater.jsp");
            
            System.out.println("sending...");
            
            Transport.send(message1);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}
