//Admin has all the permission : View, edit, delete, update etc,

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class AdminTest extends JFrame {
    DefaultTableModel Adminmodel;
    JTable AdminTable;


    public AdminTest(){
        JFrame AdminFrame = new JFrame();
        JLabel AdminLabel = new JLabel("Welcome Admin !");//SwingConstants.CENTER);
        AdminLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        AdminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        AdminLabel.setVerticalAlignment(SwingConstants.CENTER);
        AdminLabel.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        AdminLabel.setOpaque(true);


        JLabel HomeScreenLabel = new JLabel("Welcometo the Job Application System");
        HomeScreenLabel.setBounds(400, 200, 550, 30);
        HomeScreenLabel.setFont(HomeScreenLabel.getFont().deriveFont(25f));
        HomeScreenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HomeScreenLabel.setVerticalAlignment(SwingConstants.CENTER);
        HomeScreenLabel.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        HomeScreenLabel.setOpaque(true);
        AdminFrame.add(HomeScreenLabel);



        JLabel Dashbaord = new JLabel("Dashboard");
        Dashbaord.setFont(new Font("Arial", Font.PLAIN, 15));
        Dashbaord.setBounds(20, 20, 100, 30);
        Dashbaord.setVisible(false);
        AdminFrame.add(Dashbaord);

        JButton UserButton = new JButton("Users");
        UserButton.setBounds(20, 30, 100, 50);
        AdminFrame.add(UserButton);

        //adding events to every buttons
UserButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e){
        DefaultTableModel Usermodel = new DefaultTableModel();
        JTable Usertable = new JTable(Usermodel);
        JScrollPane scrollPane = new JScrollPane(Usertable);
        scrollPane.setBounds(230, 60, 800, 600);
        AdminFrame.add(scrollPane);

        Usermodel.addColumn("User ID");
        Usermodel.addColumn("Usename");
        Usermodel.addColumn("Password");
        Usermodel.addColumn("Role");
        Usermodel.addColumn("Name");


        //Database connection
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root","");
            System.out.println("Connection Established");

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");

            String query = "SELECT * FROM User";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String userID = rs.getString("User_ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                String name = rs.getString("Name");

                Usermodel.addRow(new Object[]{userID, username, password, role, name});
            }
            rs.close();
            stmt.close();
        }catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception: "+ ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception: "+ ex.getMessage());
        }

    }
});

//open job applications
JButton Listed_Jobs = new JButton("Listed Jobs");
Listed_Jobs.setBounds(20, 90, 100, 50); 
AdminFrame.add(Listed_Jobs);

//Job_Applications
Listed_Jobs.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e)
    {
       

        DefaultTableModel Jobmodel = new DefaultTableModel();
        JTable Jobtable = new JTable(Jobmodel);
        
        JScrollPane scrollPane = new JScrollPane(Jobtable);
        scrollPane.setBounds(230, 60, 800, 600);
        AdminFrame.add(scrollPane);

        Jobmodel.addColumn("ID");
        Jobmodel.addColumn("Job_Title");
        Jobmodel.addColumn("Job_Description");
        Jobmodel.addColumn("Created_by");
        Jobmodel.addColumn("Status");
        Jobmodel.addColumn("Actions");
        ImageIcon icon = new ImageIcon("/images/edit_icon.png");
        JButton editButton = new JButton("Edit");
        editButton.setText("Edit");
        editButton.setBounds(20, 20, 100, 50);
        editButton.setVisible(true);

        //Database connection
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root","");
            System.out.println("Connection Established");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            String query = "SELECT * FROM Job_Applications";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String ID = rs.getString("ID");
                String Job_Title = rs.getString("Job_Title");
                String Job_Description = rs.getString("Job_Description");
                String Created_by = rs.getString("Created_by");
                String Status = rs.getString("Status");
                
                

                Jobmodel.addRow(new Object[]{ID, Job_Title, Job_Description, Created_by, Status,editButton});
            }
            rs.close();
            stmt.close();
        }catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception: "+ ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception: "+ ex.getMessage());
        }
    }
});


       
//Applicant's applications
JButton Job_Applications = new JButton("Applicant's Applications");
Job_Applications.setBounds(20, 150, 200, 50);
AdminFrame.add(Job_Applications);
        
Job_Applications.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e)
    {
        DefaultTableModel Jobmodel = new DefaultTableModel();
        JTable Jobtable = new JTable(Jobmodel);
        JScrollPane scrollPane = new JScrollPane(Jobtable);
        scrollPane.setBounds(230, 60, 800, 600);
        AdminFrame.add(scrollPane);

       // String[] columns = {"Applicant Name", "Phone Number","Email","Address","Position","Interviewer"}; //Add column headers
        Jobmodel.addColumn("Application Name");
        Jobmodel.addColumn("Phone Number");
        Jobmodel.addColumn("Email");
        Jobmodel.addColumn("Address");
        Jobmodel.addColumn("Position");
        Jobmodel.addColumn("Interviewer");


        //Database connection
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root","");
            System.out.println("Connection Established");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            String Admin_query2 = """
                    SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
                    LEFT JOIN User ON User.User_ID = Application.InterviewerID;
                    """;
                    PreparedStatement Admin_statement2 = con.prepareStatement(Admin_query2);
                    ResultSet Admin_resultSet2 = Admin_statement2.executeQuery();
    
                    while (Admin_resultSet2.next()) {
                        Jobmodel.addRow(new Object[]{
                           Admin_resultSet2.getString("Name"),
                           Admin_resultSet2.getString("PhoneNumber"),
                           Admin_resultSet2.getString("Email"),
                           Admin_resultSet2.getString("Address"),
                           Admin_resultSet2.getString("Position"),
                           Admin_resultSet2.getString("Interviewer")
                        });
                        
                    }
        }catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception: "+ ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception: "+ ex.getMessage());
        }
    }
});

      //Manage Permissions button
  
      JButton ManagePermissions = new JButton("Manage Permissions");
         ManagePermissions.setBounds(20, 210, 200, 50);
         AdminFrame.add(ManagePermissions);
      
    
      //adding action listener to Manage Permissions button
      ManagePermissions.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
         
            JLabel Permission = new JLabel("Permission :");
            JFrame PermissionFrame = new JFrame();
            PermissionFrame.setTitle("Manage Permissions");
            PermissionFrame.setSize(1200, 1200);
            PermissionFrame.setVisible(true);
            PermissionFrame.setLayout(null);
            PermissionFrame.setLocationRelativeTo(null);
           // PermissionFrame.setVisible(true);
         
            JLabel HR_Permissions = new JLabel("HR Permissions :");
            JCheckBox HR_Create = new JCheckBox("Create");
            JCheckBox HR_View = new JCheckBox("View");
            JCheckBox HR_Edit = new JCheckBox("Edit");
             JCheckBox HR_Delete = new JCheckBox("Delete");
             JCheckBox HR_Update = new JCheckBox("Update");
             JCheckBox HR_Feedback = new JCheckBox("Feedback");
            JLabel Interviewer_Permissions = new JLabel("Interviewer Permissions :");
            JCheckBox I_Create = new JCheckBox("Create");
            JCheckBox I_View = new JCheckBox("View");
             JCheckBox I_Edit = new JCheckBox("Edit");
             JCheckBox I_Delete = new JCheckBox("Delete");   
             JCheckBox I_Update = new JCheckBox("Update");
             JCheckBox I_Feedback = new JCheckBox("Feedback");
             JButton Save_Button = new JButton("Save Changes");
             JButton Reset_Permissions = new JButton("Reset Permissions");
             JButton Reset_Button = new JButton("Reset Selections");
     
             PermissionFrame.add(Permission);
             PermissionFrame.add(HR_Permissions);
            PermissionFrame.add(HR_Create);
             PermissionFrame.add(HR_View);
             PermissionFrame.add(HR_Edit);
             PermissionFrame.add(HR_Delete);  
             PermissionFrame.add(HR_Update);
             PermissionFrame.add(HR_Feedback);
            // PermissionFrame.setVisible(false);//initially hidden
             PermissionFrame.add(Interviewer_Permissions);
             PermissionFrame.add(I_Create);
            PermissionFrame.add(I_View);
             PermissionFrame.add(I_Edit);
       PermissionFrame.add(I_Delete);
           PermissionFrame.add(I_Update);
            PermissionFrame.add(I_Feedback);
             I_Feedback.setVisible(false);//initially hidden
             PermissionFrame.add(Save_Button);
             PermissionFrame.add(Reset_Permissions);
         PermissionFrame.add(Reset_Button);
     
            
        
             Permission.setBounds(100,100,120,50);
         HR_Permissions.setBounds(100,150,120,50);
        HR_Create.setBounds(300,150,120,50);
        HR_View.setBounds(400,150,120,50);
        HR_Edit.setBounds(500,150,120,50);
        HR_Delete.setBounds(600,150,120,50);
        HR_Update.setBounds(750,150,120,50);
        HR_Feedback.setBounds(900,150,120,50);

        Interviewer_Permissions.setBounds(100,200,180,50);
        I_Create.setBounds(300,200,120,50);
        I_View.setBounds(400,200,120,50);
        I_Edit.setBounds(500,200,120,50);
        I_Delete.setBounds(600,200,120,50);
        I_Update.setBounds(750,200,120,50);
        I_Feedback.setBounds(900,200,120,50);

        Save_Button.setBounds(400,300,120,50);
        Reset_Permissions.setBounds(540,300,130,50);
        Reset_Button.setBounds(700,300,120,50);

       
       
               //Loading Permissions method
        loadPermissionsFromDatabase(HR_View, HR_Create, HR_Update, HR_Delete, HR_Feedback, HR_Edit,
        I_View, I_Create, I_Update, I_Delete, I_Feedback, I_Edit);

JButton Close = new JButton("Close");
Close.setBounds(20, 20, 100, 50);
PermissionFrame.add(Close);
Close.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        PermissionFrame.dispose(); // Close the current frame
    }
});

 //Applying actionListern to Buttons to save permissions
 Save_Button.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
            System.out.println("Connection Established for save button");
            Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
            System.out.println("Driver Loaded for Save button");
            //query to get the data from the database from User Table
            String updateHR = "UPDATE Permissions SET Can_View = ?, Can_Edit = ?, Can_Delete = ?, Can_Update = ?, Can_Create = ?, Can_Feedback = ? WHERE Role = ?";
            PreparedStatement hrStmt = con.prepareStatement(updateHR);
            hrStmt.setBoolean(1, HR_View.isSelected());
            hrStmt.setBoolean(2, HR_Edit.isSelected());
            hrStmt.setBoolean(3, HR_Delete.isSelected());
            hrStmt.setBoolean(4, HR_Update.isSelected());
            hrStmt.setBoolean(5, HR_Create.isSelected());
            hrStmt.setBoolean(6, HR_Feedback.isSelected());
            hrStmt.setString(7, "HR");
            hrStmt.executeUpdate();

            String updateInt = "UPDATE Permissions SET Can_View = ?, Can_Edit = ?, Can_Delete = ?, Can_Update = ?, Can_Create = ?, Can_Feedback = ? WHERE Role = ?";
            PreparedStatement intStmt = con.prepareStatement(updateInt);
            intStmt.setBoolean(1, I_View.isSelected());
            intStmt.setBoolean(2, I_Edit.isSelected());
            intStmt.setBoolean(3, I_Delete.isSelected());
            intStmt.setBoolean(4, I_Update.isSelected());
            intStmt.setBoolean(5, I_Create.isSelected());
            intStmt.setBoolean(6, I_Feedback.isSelected());
            intStmt.setString(7, "Interviewer");
            intStmt.executeUpdate();

            // String query = "UPDATE Permissions SET column_name = ?, Last_modified = CURRENT_TIMESTAMP WHERE id = (SELECT MAX(id) FROM Permissions)";
            // PreparedStatement pstmt = con.prepareStatement(query);
            // pstmt.setString(1, "your_value");
            // pstmt.executeUpdate();
            // JOptionPane.showMessageDialog(null, "Permissions Updated Successfully!");

            JOptionPane.showMessageDialog(null, "Permissions Updated Successfully!");
        }catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception 1: "+ ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception 2: "+ ex.getMessage());
        }


         //reset button
         Reset_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Reset all checkboxes
                HR_Create.setSelected(false);
                HR_View.setSelected(false);
                HR_Edit.setSelected(false);
                HR_Delete.setSelected(false);
                HR_Update.setSelected(false);
                HR_Feedback.setSelected(false);

                I_Create.setSelected(false);
                I_View.setSelected(false);
                I_Edit.setSelected(false);
                I_Delete.setSelected(false);
                I_Update.setSelected(false);
                I_Feedback.setSelected(false);

                JOptionPane.showMessageDialog(null, "Selections Reset Successfully!");
            }
        });

    }
});

 //Reset permissions buttons
 Reset_Permissions.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // Uncheck all checkboxes
        HR_View.setSelected(false);
        HR_Create.setSelected(false);
        HR_Update.setSelected(false);
        HR_Delete.setSelected(false);
        HR_Feedback.setSelected(false);
        HR_Edit.setSelected(false);

        I_View.setSelected(false);
        I_Create.setSelected(false);
        I_Update.setSelected(false);
        I_Delete.setSelected(false);
        I_Feedback.setSelected(false);
        I_Edit.setSelected(false);

        //removing all permissions (set all to false)
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Reset HR Permissions
            String resetHR = "REPLACE INTO Permissions (Role, Can_View, Can_Edit, Can_Delete, Can_Update, Can_Create, Can_Feedback) VALUES ('HR', false, false, false, false, false, false)";
            PreparedStatement HRStatement2= con.prepareStatement(resetHR);
            HRStatement2.executeUpdate();

            // Reset Interviewer Permissions
            String resetI = "REPLACE INTO Permissions (Role, Can_View, Can_Edit, Can_Delete, Can_Update, Can_Create, Can_Feedback) VALUES ('Interviewer', false, false, false, false, false, false)";
            PreparedStatement InterviewerStatement2 = con.prepareStatement(resetI);
            InterviewerStatement2.executeUpdate();

            JOptionPane.showMessageDialog(AdminFrame, "Permissions reset successfully!");

        } catch (Exception ex) {
            System.out.println("Error resetting permissions: " + ex.getMessage());
        }
    }
});


        }
      });


      JButton LogOutButton = new JButton("Log Out");
        //Adding Join Table after Buttons
       // String[] columns = {"Applicant Name", "Phone Number","Email","Address","Position","Interviewer"}; //Add column headers
        // Adminmodel = new DefaultTableModel(columns,0);
        // AdminTable = new JTable(Adminmodel);
        // JScrollPane AdminscrollPane = new JScrollPane(AdminTable);
        // AdminscrollPane.setBounds(50, 520, 900, 200);
        // AdminFrame.add(AdminscrollPane);
 


       
        
        AdminFrame.setLayout(null);
        AdminFrame.add(AdminLabel);
        AdminFrame.add(LogOutButton);
        
      
        


       AdminLabel.setBounds(500,50,150,50);
       LogOutButton.setBounds(20, 270, 100, 50);
      


        AdminFrame.setTitle("Admin Panel");
        AdminFrame.setSize(1200,1200);
        AdminFrame.setVisible(true);
     
        AdminFrame.setLocationRelativeTo(null);
        AdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AdminFrame.setResizable(true);

        //adding LogOut button functionality
        // LogOutButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         AdminFrame.dispose(); // Close the current frame
        //         new LoginWindow(); // Open the login window
        //     }
        // });

       // LogOutButton.setBounds(20, 180, 100, 30);
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Close the current window
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LogOutButton);
                    frame.dispose();
                    new LoginWindow();
                    

                    //add another window if needed
                }
            }
        });


     


                //    //adding Left Join table
                //    try{
                //     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
                //     System.out.println("Connection Established For Table 1");
                //     Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
                //     System.out.println("Driver Loaded For Table 1");
                //    String Admin_query2 = """
                //     SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
                //     LEFT JOIN User ON User.User_ID = Application.InterviewerID;
                //     """;
                //     PreparedStatement Admin_statement2 = con.prepareStatement(Admin_query2);
                //     ResultSet Admin_resultSet2 = Admin_statement2.executeQuery();
    
                //     while (Admin_resultSet2.next()) {
                //         Adminmodel.addRow(new Object[]{
                //            Admin_resultSet2.getString("Name"),
                //            Admin_resultSet2.getString("PhoneNumber"),
                //            Admin_resultSet2.getString("Email"),
                //            Admin_resultSet2.getString("Address"),
                //            Admin_resultSet2.getString("Position"),
                //            Admin_resultSet2.getString("Interviewer")
                //         });
                        
                //     }
                // }catch(ClassNotFoundException | SQLException ex)
                // {
                //     System.out.println("Exception of Table 1: "+ ex.getMessage());
                // }
                // catch(Exception ex){
                //     System.out.println("Exception of Table 2: "+ ex.getMessage());
                // }


       

       


       
    }
    private void loadPermissionsFromDatabase(JCheckBox HR_View, JCheckBox HR_Create, JCheckBox HR_Update, JCheckBox HR_Delete,
    JCheckBox HR_Feedback, JCheckBox HR_Edit,
    JCheckBox I_View, JCheckBox I_Create, JCheckBox I_Update, JCheckBox I_Delete,
    JCheckBox I_Feedback, JCheckBox I_Edit) {
try {
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
Class.forName("com.mysql.cj.jdbc.Driver");

// HR Permissions
String hrQuery = "SELECT * FROM Permissions WHERE Role = 'HR'";
PreparedStatement hrStmt = con.prepareStatement(hrQuery);
ResultSet hrRs = hrStmt.executeQuery();
if (hrRs.next()) {
HR_View.setSelected(hrRs.getBoolean("Can_View"));
HR_Create.setSelected(hrRs.getBoolean("Can_Create"));
HR_Update.setSelected(hrRs.getBoolean("Can_Update"));
HR_Delete.setSelected(hrRs.getBoolean("Can_Delete"));
HR_Feedback.setSelected(hrRs.getBoolean("Can_Feedback"));
HR_Edit.setSelected(hrRs.getBoolean("Can_Edit"));
}

// Interviewer Permissions
String iQuery = "SELECT * FROM Permissions WHERE Role = 'Interviewer'";
PreparedStatement iStmt = con.prepareStatement(iQuery);
ResultSet iRs = iStmt.executeQuery();
if (iRs.next()) {
I_View.setSelected(iRs.getBoolean("Can_View"));
I_Create.setSelected(iRs.getBoolean("Can_Create"));
I_Update.setSelected(iRs.getBoolean("Can_Update"));
I_Delete.setSelected(iRs.getBoolean("Can_Delete"));
I_Feedback.setSelected(iRs.getBoolean("Can_Feedback"));
I_Edit.setSelected(iRs.getBoolean("Can_Edit"));
}

} catch (Exception ex) {
System.out.println("Exception in loadPermissionsFromDatabase: " + ex.getMessage());
    }
}    
public static void main(String[] args) {
        new AdminTest();
    }
}
