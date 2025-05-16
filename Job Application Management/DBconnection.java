import java.sql.Connection;
import java.sql.DriverManager; 


public class DBconnection
{
    public static void main(String[] args) {
       
        try{
             Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
             System.out.println("Driver Loaded");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root","");
             System.out.println("Connection Established");
        }catch(ClassNotFoundException e)
        {
            System.out.println("Exception 1: "+ e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception 2: "+ e.getMessage());
        }
    }

} 