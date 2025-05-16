import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginWindow extends JFrame{


    //String Username, Password,Role;
    public LoginWindow()
    {
        JFrame F = new JFrame();
        JLabel l1 = new JLabel("Login As");
        JButton CB = new JButton("Candidate Registration Form");
        JLabel l3 = new JLabel("Username :");
        JLabel l4 = new JLabel("Password :");
        JButton B1 = new JButton("Login");

        //button to close the window
        JButton Close = new JButton("Close");
        Close.setBounds(300, 300, 100, 50);
        Close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                F.dispose();
            }
        });
        F.add(Close);

        JComboBox<String> LoginAs = new JComboBox<>();
        JTextField UsernameField = new JTextField();
        JTextField PasswordField = new JTextField(); //JPasswordField Password = new JPasswordField();

        //Setting bounds for the components
        l1.setBounds(100, 100, 200, 50); //Login as
        CB.setBounds(370, 20, 200, 50); //candidate registration form
        l3.setBounds(100, 150, 200, 50); //Username 
        l4.setBounds(100, 200, 200, 50);  //Password
        LoginAs.setBounds(300, 100, 200, 50); //Login as ComboBox
        UsernameField.setBounds(300, 150, 200, 50);
        PasswordField.setBounds(300, 200, 200, 50);
        B1.setBounds(100, 300, 100, 50); // Button


       
 
//Adding values to the combo box;
LoginAs.addItem("Select Your Designation");

//Adding action listener to the button
B1.addActionListener( new ActionListener ()
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String UserName = UsernameField.getText();
        String PassWord = PasswordField.getText();
        String Role = (String) LoginAs.getSelectedItem();
        // char[] passWordChar = PasswordField.getText();
        // String PassWord = String.valueOf(passWordChar);
        
String Username = null;
String Password = null;

try{
    Connection con = null;
    Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
    System.out.println("Driver Loaded");
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root","");
    System.out.println("Connection Established");
    //query to get the data from the database from User Table
    String query = "SELECT * FROM User WHERE Username = ? AND Password = ? AND Role= ?";
    PreparedStatement preparedStatement = con.prepareStatement(query);
    preparedStatement.setString(1, UserName);
    preparedStatement.setString(2, PassWord);
    preparedStatement.setString(3, Role);
    ResultSet resultSet = preparedStatement.executeQuery();

    while(resultSet.next())
    {
        Username = resultSet.getString("Username");
        Password = resultSet.getString("Password");
       Role = resultSet.getString("Role");
       // System.out.println("Role:"+ Role);
       System.out.println("Username: " + Username);
       System.out.println("Password: " + Password);
       JOptionPane.showMessageDialog(null, "Login Successful as " + Role);
       F.dispose();
       switch (Role) {
        case "Admin":
           // new Admin();
           new AdminTest();
            break;
        case "Human Resource":
            new HR();
            break;
        case "Interviewer":
            new Interviewer();
            break;
        default:
            JOptionPane.showMessageDialog(null, "Unknown role: " + Role);
            break;
    }
    return; // stop further execution
           // new Admin();
          //new HR();
        // new Interviewer();
     
    }

}catch(Exception e1)
{
    System.out.println("Exception of Validation: "+ e1.getMessage());
}

        //validating against Database
        if(UserName.equals(Username) && PassWord.equals(Password))
        {
            JOptionPane.showMessageDialog(null,"Login Successful");
            F.dispose(); //Close the current frame
            new ApplicationForm(); //Open the application form
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid Username or Password");
        }

        //adding mechnism to redirect to Admin Page and others
        // B1.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e)
        //     {
        //         Admin Admin = new Admin();
        //         Admin.setVisible(true);
        //     }
        // });
    }

    
});

CB.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new Applicant();  // Open Applicant window
        F.dispose();      // Optional: close current login window
    }
});


        //adding items to frame
        F.add(l1);
        F.add(CB);
        F.add(l3);
        F.add(l4);
        F.add(LoginAs);
        F.add(UsernameField);
        F.add(PasswordField);
        F.add(B1);
        F.setTitle("Login Window");
        F.setSize(600,400);
        F.setLayout(null);
        F.setLocationRelativeTo(null);
        F.setVisible(true);
        F.setResizable(true);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //loadind driver
            System.out.println("Driver Loaded");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
            System.out.println("Connection Established");

            //query to get the data from the database from User Table
            String query = "SELECT Role FROM User";
            Statement statement1 = con.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query);

           // int rowscount=0;
            while(resultSet1.next())
            {
                // rowscount++;
                // System.out.println("Row Count : "+rowscount);
                LoginAs.addItem(resultSet1.getString("Role"));
         
            }
        }catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Excpetion 1 : "+ e.getMessage());
        }catch(Exception e){
            System.out.println("Exception 2 : "+ e.getMessage());
        }

    }
    public static void main(String[] args) {
        new LoginWindow();

       

    }
}
