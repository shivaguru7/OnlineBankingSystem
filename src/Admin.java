import java.sql.SQLException;
import java.util.Scanner;

public class Admin {

	public static void main(String[] args) throws SQLException {
		int choice;
		boolean connection = DbConnect.getConnection();
		if(connection==true)
		{
			System.out.println("Connection Success");
		}
		System.out.println("*********************************");
		System.out.println("Welcome To SBI NetBanking System");
		System.out.println("*********************************");
		System.out.println("Please Choose Your Login");
		Scanner din = new Scanner(System.in);
		System.out.println("1.Admin Login");
		System.out.println("2.Customer Login");
		choice=din.nextInt();
		switch(choice)
		{
		case 1: System.out.println("Admin Login Here");
				AdminLogin al = new AdminLogin();
				al.Login();
		break;
		case 2: System.out.println("Customer Login or Register Here");
				CustomerLogin cl = new CustomerLogin();
				cl.LogType();
		break;
		}

	}

}
