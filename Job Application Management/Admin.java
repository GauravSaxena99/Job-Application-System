//Admin has all the permission : View, edit, delete, update etc,

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class Admin extends JFrame {
    DefaultTableModel Adminmodel;
    JTable AdminTable;


    public Admin(){
        JFrame AdminFrame = new JFrame();
        JLabel AdminLabel = new JLabel("Welcome Admin !");//SwingConstants.CENTER);

       JLabel Permission = new JLabel("Permission :");

       JLabel AdminTableLabel = new JLabel("Inerviewer Details Table"); //Table label
       JButton LogOutButton = new JButton("Log Out");


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

        //Adding Join Table after Buttons
        String[] columns = {"Applicant Name", "Phone Number","Email","Address","Position","Interviewer"}; //Add column headers
        Adminmodel = new DefaultTableModel(columns,0);
        AdminTable = new JTable(Adminmodel);
        JScrollPane AdminscrollPane = new JScrollPane(AdminTable);
        AdminscrollPane.setBounds(50, 420, 900, 200);
        AdminFrame.add(AdminscrollPane);
 


       
        
        AdminFrame.setLayout(null);
        AdminFrame.add(AdminLabel);
        AdminFrame.add(LogOutButton);
        AdminFrame.add(Permission);
        AdminFrame.add(HR_Permissions);
        AdminFrame.add(HR_Create);
        AdminFrame.add(HR_View);
        AdminFrame.add(HR_Edit);
        AdminFrame.add(HR_Delete);  
        AdminFrame.add(HR_Update);
        AdminFrame.add(HR_Feedback);
        HR_Feedback.setVisible(false);//initially hidden
        AdminFrame.add(Interviewer_Permissions);
        AdminFrame.add(I_Create);
        AdminFrame.add(I_View);
        AdminFrame.add(I_Edit);
        AdminFrame.add(I_Delete);
        AdminFrame.add(I_Update);
        AdminFrame.add(I_Feedback);
        I_Feedback.setVisible(false);//initially hidden
        AdminFrame.add(Save_Button);
        AdminFrame.add(Reset_Permissions);
        AdminFrame.add(Reset_Button);

        AdminFrame.add(AdminTableLabel);
   
      
        


       AdminLabel.setBounds(500,50,120,50);
       LogOutButton.setBounds(1000, 50, 100, 50);
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

        AdminTableLabel.setBounds(50, 370, 220, 50);
       


        AdminFrame.setTitle("Admin Panel");
        AdminFrame.setSize(1200,1200);
        AdminFrame.setVisible(true);
     
        AdminFrame.setLocationRelativeTo(null);
        AdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AdminFrame.setResizable(true);

        //adding LogOut button functionality
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminFrame.dispose(); // Close the current frame
                new LoginWindow(); // Open the login window
            }
        });

        //Loading Permissions method
        loadPermissionsFromDatabase(HR_View, HR_Create, HR_Update, HR_Delete, HR_Feedback, HR_Edit,
        I_View, I_Create, I_Update, I_Delete, I_Feedback, I_Edit);


                   //adding Left Join table
                   try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
                    System.out.println("Connection Established For Table 1");
                    Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
                    System.out.println("Driver Loaded For Table 1");
                   String Admin_query2 = """
                    SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
                    LEFT JOIN User ON User.User_ID = Application.InterviewerID;
                    """;
                    PreparedStatement Admin_statement2 = con.prepareStatement(Admin_query2);
                    ResultSet Admin_resultSet2 = Admin_statement2.executeQuery();
    
                    while (Admin_resultSet2.next()) {
                        Adminmodel.addRow(new Object[]{
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
                    System.out.println("Exception of Table 1: "+ ex.getMessage());
                }
                catch(Exception ex){
                    System.out.println("Exception of Table 2: "+ ex.getMessage());
                }


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

            }
        });

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
        new Admin();
    }
}
