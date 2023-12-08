import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AdminLogin {
	
	Scanner din;
	void Login()
	{
		din = new Scanner(System.in);
		String username,password;
		System.out.println("Enter UserName");
		username = din.next(); // admin
		System.out.println("Enter Password");
		password = din.next(); // welcome
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/onlinebank", "root", "root");
			String sql="select * from admin";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				if(username.equals(rs.getString(2)) && password.equals(rs.getString(3)))
				{
					System.out.println("Welcome !! Admin ");
					process();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	void process()
	{
		String option="y";
		int choice;
		while(option.equalsIgnoreCase("y"))
		{
			System.out.println("1.View Customer Application");
			System.out.println("2.View Customer History");
			System.out.println("3.Logout");
			System.out.println("Select Your Option");
			choice=din.nextInt();
			switch(choice)
			{
			case 1:customer_application();
				break;
			case 2:
				try
				{
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/onlinebank", "root", "root");
					String sql1="select * from customer_activity";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql1);
					System.out.println("Cid\tCNumber\tBalance\tActivity\tDateAndTime");
					while(rs.next())
					{
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(4)+"\t"+rs.getString(5)+"\t"+rs.getDate(6));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			case 3: System.out.println("Admin Logout Success");
					
			}
			din=new Scanner(System.in);
			System.out.println("Do You Want to Continue Press Y or For Exit Press N");
			option=din.next();
		}
	}
	void customer_application()
	{
		int cid;
		String status;
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/onlinebank", "root", "root");
			String sql="select * from customer";
			System.out.println("List of Customer Application");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			System.out.println("Cid\tCname\tCuser\tCemail\t\tCmobile\t\tStatus");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
			}
			System.out.println("Hello Admin ... Please Select Any Customer to Approve");
			System.out.println("Enter Customer Id");
			cid = din.nextInt(); // for example 2 
			String sql1="select * from customer where cid='"+cid+"'";
			Statement st1 = con.createStatement();
			ResultSet rs1 = st1.executeQuery(sql1);
			if(rs1.next())
			{
				String cemail= rs1.getString(5);
				String cname = rs1.getString(2);
				String uname = rs1.getString(3);
				System.out.println("Customer id is Found Successfully for action");
				System.out.println("For Approve press Y or Reject Press N");
				status = din.next();
				if(status.equalsIgnoreCase("y"))
				{
					status="Yes";
				}
				else
				{
					status="No";
				}
				String sql2="update customer set status='"+status+"' where cid='"+cid+"'";
				PreparedStatement ps = con.prepareStatement(sql2);
				int i=ps.executeUpdate();
				if(i==1)
				{
					System.out.println("Customer Application is Approved");
					String host="smtp.gmail.com";   // Types of gmail sending hostname []
					  final String user="shivame2011@gmail.com";//change accordingly  
					  final String password="tbpn xwxx ewzg fesz";//change accordingly  
					    
					  String to=cemail;//change accordingly  
					  
					   //Get the session object  
					   Properties props = new Properties();  
					   props.put("mail.smtp.host",host);  
					   props.put("mail.smtp.auth", "true");  
					   props.put("mail.smtp.starttls.enable", "true");
					   Session session = Session.getDefaultInstance(props,  
					    new javax.mail.Authenticator() {  
					      protected PasswordAuthentication getPasswordAuthentication() {  
					    return new PasswordAuthentication(user,password);  
					      }  
					    });  
					  
					   //Compose the message  
					    try {  
					     MimeMessage message = new MimeMessage(session);  
					     message.setFrom(new InternetAddress(user));  
					     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
					     message.setSubject("SBI-Online NetBanking System -");  
					     message.setText("Dear,\n "+cname+" Welcome to SBI Online Banking System\n You're application is Verified Successfully\n Please Login with Your username-"+uname+" and You're Password Credentials \n"
					     		+ "Submit your Aadhar card, Pan card, Ration Card For Further Verfication.\n\n\n\n\n Thanks\n SBI-OnlineBanking System ");  
					       
					    //send the message  
					     Transport.send(message);  
					  
					     System.out.println("message sent successfully...");  
					   
					     } catch (MessagingException e) {e.printStackTrace();}
					// Twilio Api Integration For Sending SMS to Customer Future Enchancement
				}
				else
				{
					System.out.println("Customer Application is Rejected");
				}
			}
			else
			{
				System.out.println("Sorry !!! Customer id is not Found");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
