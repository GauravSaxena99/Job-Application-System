//HR can view the Application Table.
//HR only have :Create (create new Table using appropriate job applications), view, Update.

import javax.swing.*;
import java.sql.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;


public class HR extends JFrame{
    JTable Apptable;
    DefaultTableModel Appmodel;

    JTable INtable2;
    DefaultTableModel INmodel2;
    public HR(){
        JFrame HRFrame = new JFrame();
        JLabel HRLabel = new JLabel("Welcome HR !");//SwingConstants.CENTER);
        HRLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JLabel ApplicationTableLabel = new JLabel("Application Table");
        ApplicationTableLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JLabel InterviewerDetailsLabel = new JLabel("Interviewer Details Table");
        InterviewerDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 15));


        //Adding Buttons For Edit, Delete, Update, Create, Feedback below Application Table
        JButton EditButtonForApp = new JButton("Edit");
        JButton DeleteButtonForApp = new JButton("Delete");
        JButton UpdateButtonForApp = new JButton("Update");
        JButton CreateButtonForApp = new JButton("Create");   
        JButton FeedbackButtonForApp = new JButton("Feedback");

        //Adding buttons for Interviewer Details Table
        JButton EditButtonForInterviewer = new JButton("Edit");
        JButton DeleteButtonForInterviewer = new JButton("Delete");
        JButton UpdateButtonForInterviewer = new JButton("Update");
        JButton CreateButtonForInterviewer = new JButton("Create");
        JButton FeedbackButtonForInterviewer = new JButton("Feedback");

        //adding Back button at the very bottom
        JButton BackButton = new JButton("<- Back");
        //adding logOut Button
        JButton LogOutButton = new JButton("Log Out");

// Create a table to display the data
String[] columns = {"Job_ID", "Application_ID","Name","PhoneNumber","Email","Address","Position"};
Appmodel = new DefaultTableModel(columns,0); 
Apptable = new JTable(Appmodel);
JScrollPane scrollPane = new JScrollPane(Apptable);
scrollPane.setBounds(50, 120, 900, 200);
HRFrame.add(scrollPane);


String[] columns2 = {"Applicant Name", "Phone Number","Email","Address","Position","Interviewer"}; //Add column headers
INmodel2 = new DefaultTableModel(columns2,0);
INtable2 = new JTable(INmodel2);
JScrollPane HR_scrollPane = new JScrollPane(INtable2);
HR_scrollPane.setBounds(50, 420, 900, 200);
HRFrame.add(HR_scrollPane);

        // Another way to add column headers
        // model2.addColumn("Applicant Name");
        // model2.addColumn("Phone Number");
        // model2.addColumn("Email");
        // model2.addColumn("Address");
        // model2.addColumn("Position");  
        // model2.addColumn("Interviewer");


        HRFrame.setLayout(null);
        HRFrame.add(HRLabel);
        HRFrame.add(ApplicationTableLabel);
        HRFrame.add(LogOutButton);  //log out button
        HRFrame.add(EditButtonForApp);
        HRFrame.add(DeleteButtonForApp);
        HRFrame.add(UpdateButtonForApp);
        HRFrame.add(CreateButtonForApp);
        HRFrame.add(FeedbackButtonForApp);
        FeedbackButtonForApp.setVisible(false); // Initially hidden

        HRFrame.add(InterviewerDetailsLabel);
        HRFrame.add(EditButtonForInterviewer);
        HRFrame.add(DeleteButtonForInterviewer);
        HRFrame.add(UpdateButtonForInterviewer);
        HRFrame.add(CreateButtonForInterviewer);
        HRFrame.add(FeedbackButtonForInterviewer);
        HRFrame.add(BackButton);
        BackButton.setVisible(false); // Initially hidden
        FeedbackButtonForInterviewer.setVisible(false); // Initially hidden
        //HRFrame.add(table);
        HRLabel.setBounds(270,20,120,50);
        ApplicationTableLabel.setBounds(50, 70, 220, 50);

        LogOutButton.setBounds(820, 20, 100, 50); //log out button
       
        EditButtonForApp.setBounds(50, 330, 100, 50);
        DeleteButtonForApp.setBounds(200, 330, 100, 50);
        UpdateButtonForApp.setBounds(350, 330, 100, 50);
        CreateButtonForApp.setBounds(500, 330, 100, 50);
        FeedbackButtonForApp.setBounds(650, 330, 100, 50);

        InterviewerDetailsLabel.setBounds(50, 370, 220, 50);
        EditButtonForInterviewer.setBounds(50, 630, 100, 50);
        DeleteButtonForInterviewer.setBounds(200, 630, 100, 50);
        UpdateButtonForInterviewer.setBounds(350, 630, 100, 50);
        CreateButtonForInterviewer.setBounds(500, 630, 100, 50);
        FeedbackButtonForInterviewer.setBounds(650, 630, 100, 50);
        BackButton.setBounds(820, 700, 100, 50);

        HRFrame.setTitle("HR Panel");
        HRFrame.setSize(1000,1200);
        HRFrame.setVisible(true);
        HRFrame.setLocationRelativeTo(null);
        HRFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HRFrame.setResizable(true);
   
        load_HR_Permission(EditButtonForApp, DeleteButtonForApp, UpdateButtonForApp, CreateButtonForApp, FeedbackButtonForApp);
        load_HR_Permission_IN_Table(EditButtonForInterviewer,DeleteButtonForInterviewer, UpdateButtonForInterviewer, CreateButtonForInterviewer, FeedbackButtonForInterviewer);

        //FOR APplicaion table
        // showPermissionAlertIfDisabled(EditButtonForApp, "Can_Edit");
        // showPermissionAlertIfDisabled(DeleteButtonForApp, "Can_Delete");
        // showPermissionAlertIfDisabled(UpdateButtonForApp, "Can_Update");
        // showPermissionAlertIfDisabled(CreateButtonForApp, "Can_Create");
        // showPermissionAlertIfDisabled(FeedbackButtonForApp, "Can_Feedback");


//for innterview table
// showPermissionAlertIfDisabled(EditButtonForInterviewer, "edit interviewer");
// showPermissionAlertIfDisabled(DeleteButtonForInterviewer, "delete interviewer");
// showPermissionAlertIfDisabled(UpdateButtonForInterviewer, "update interviewer");
// showPermissionAlertIfDisabled(CreateButtonForInterviewer, "create interviewer");
// showPermissionAlertIfDisabled(FeedbackButtonForInterviewer, "give feedback");
        
LogOutButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        HRFrame.dispose(); // Close the current frame
        new LoginWindow(); // Open the login window
    }
});


        //Connect to the database
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
            System.out.println("Connection Established");

            Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
            System.out.println("Driver Loaded");
            
            //query about geting the Applcation table
            String Query_HR = "Select * from Application";
            PreparedStatement preparedStatementHR = con.prepareStatement(Query_HR);
            ResultSet resultSetHR = preparedStatementHR.executeQuery();


            while(resultSetHR.next())
            {
                int Job_Id = resultSetHR.getInt("Job_ID");
                int Applcation_ID = resultSetHR.getInt("Application_ID");
                String Name = resultSetHR.getString("Name");
                String PhoneNumber = resultSetHR.getString("PhoneNumber");
                String Email = resultSetHR.getString("Email");
                String Address = resultSetHR.getString("Address");
                String Position = resultSetHR.getString("Position");
   


                //adding row to table model
                Appmodel.addRow(new Object[]{Job_Id, Applcation_ID, Name,PhoneNumber,Email, Address, Position});
            }
            //adding Left Join table
            String HR_query2 = """
                    SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
                    LEFT JOIN User ON User.User_ID = Application.InterviewerID;
                    """;
                    PreparedStatement HR_statement2 = con.prepareStatement(HR_query2);
                    ResultSet HR_resultSet2 = HR_statement2.executeQuery();

                    while (HR_resultSet2.next()) {
                        INmodel2.addRow(new Object[]{
                            HR_resultSet2.getString("Name"),
                            HR_resultSet2.getString("PhoneNumber"),
                            HR_resultSet2.getString("Email"),
                            HR_resultSet2.getString("Address"),
                            HR_resultSet2.getString("Position"),
                            HR_resultSet2.getString("Interviewer")
                        });
                        
                    }
        }catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Exception_HR 1: "+ e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception_HR 2: "+ e.getMessage());
        }
        //Adding Action Listeners to the Delete button
        DeleteButtonForApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int selectedRow = Apptable.getSelectedRow();
                //check if row is selected or not
                if (selectedRow != -1) {
                    // Get the Application_ID of the selected row
                    int applicationId = (int) Apptable.getValueAt(selectedRow, 1);
                    // Delete the row from the table model
                    Appmodel.removeRow(selectedRow);
                    // Delete the row from the database
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                        String deleteQuery = "DELETE FROM Application WHERE Application_ID = ?";
                        PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);
                        preparedStatement.setInt(1, applicationId);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(HRFrame, "Application deleted successfully");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(HRFrame, "Please select a row to delete");
                }
            }


        });

    }
    //loading permission from DB 
    private void load_HR_Permission_IN_Table(
        JButton EditButtonForInterviewer, JButton DeleteButtonForInterviewer, JButton UpdateButtonForInterviewer, JButton CreateButtonForInterviewer, JButton FeedbackButtonForInterviewer) {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                Class.forName("com.mysql.cj.jdbc.Driver");
        
                String query = "SELECT * FROM Permissions WHERE Role = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, "HR");
        
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    EditButtonForInterviewer.setEnabled(rs.getBoolean("Can_Edit"));
                    DeleteButtonForInterviewer.setEnabled(rs.getBoolean("Can_Delete"));
                    UpdateButtonForInterviewer.setEnabled(rs.getBoolean("Can_Update"));
                    CreateButtonForInterviewer.setEnabled(rs.getBoolean("Can_Create"));
                    FeedbackButtonForInterviewer.setEnabled(rs.getBoolean("Can_Feedback"));
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // private void showPermissionAlertIfDisabled(JButton button, String permissionName) {
        //     button.addActionListener(new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent e) {
        //             if (!button.isEnabled()) {
        //                 JOptionPane.showMessageDialog(null, "You don't have permission to " + permissionName, "Permission Denied", JOptionPane.WARNING_MESSAGE);
        //             }
        //         }
        //     });
        // }

        private void load_HR_Permission(
            JButton EditButtonForApp, JButton DeleteButtonForApp, JButton UpdateButtonForApp, JButton CreateButtonForApp, JButton FeedbackButtonForApp) {
    
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                    Class.forName("com.mysql.cj.jdbc.Driver");
            
                    String query = "SELECT * FROM Permissions WHERE Role = ?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, "HR");
            
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        EditButtonForApp.setEnabled(rs.getBoolean("Can_Edit"));
                        DeleteButtonForApp.setEnabled(rs.getBoolean("Can_Delete"));
                        UpdateButtonForApp.setEnabled(rs.getBoolean("Can_Update"));
                        CreateButtonForApp.setEnabled(rs.getBoolean("Can_Create"));
                        FeedbackButtonForApp.setEnabled(rs.getBoolean("Can_Feedback"));
                    }
            
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    
    public static void main(String[] args) {
        new HR();
    }
}
