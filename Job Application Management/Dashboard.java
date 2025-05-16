import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard {

    public Dashboard(){
      
        JFrame DashbaordFrame = new JFrame("Dashboard");
        DashbaordFrame.setSize(1200, 1000);

        //HomeScreen
        JLabel HomeScreenLabel = new JLabel("Welcometo the Application System");
        HomeScreenLabel.setBounds(400, 100, 500, 30);
        HomeScreenLabel.setFont(HomeScreenLabel.getFont().deriveFont(20f));
        HomeScreenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HomeScreenLabel.setVerticalAlignment(SwingConstants.CENTER);
        HomeScreenLabel.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        HomeScreenLabel.setOpaque(true);
        DashbaordFrame.add(HomeScreenLabel);




        JButton LoginButton = new JButton("Already Member? Log In");
        LoginButton.setBounds(500, 700, 200, 30);
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your login logic here
                new LoginWindow();
                System.out.println("Login button clicked");
            }
        });

        //Dashbaord
        JLabel DashboardLabel = new JLabel("Dashboard");
        DashboardLabel.setBounds(20, 20, 100, 30);

        //Users 
        JButton UserButton = new JButton("Users");
        UserButton.setBounds(20, 60, 100, 30);
       
        //Job_Applications
        JButton Job_Applications = new JButton("Job Applications");
        Job_Applications.setBounds(20, 100, 150, 30);

        //ManagePermissions
        JButton ManagePermissions = new JButton("Manage Permissions");
        ManagePermissions.setBounds(20, 140, 170, 30);

        //LogOut
        JButton LogOut = new JButton("Log Out");
        LogOut.setBounds(20, 180, 100, 30);
        LogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Close the current window
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LogOut);
                    frame.dispose();
                    

                    //add another window if needed
                }
            }
        });



        //Adding to frame
        DashbaordFrame.add(DashboardLabel);
        DashbaordFrame.add(UserButton);
        DashbaordFrame.add(Job_Applications);
        DashbaordFrame.add(ManagePermissions);
        DashbaordFrame.add(LogOut);
        DashbaordFrame.add(LoginButton);
        DashbaordFrame.setLayout(null);
        DashbaordFrame.setVisible(true);
        DashbaordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DashbaordFrame.setLocationRelativeTo(null); // Center the frame on the screen
        DashbaordFrame.setResizable(true); // Disable resizing
        DashbaordFrame.setTitle("Dashboard");
       

       
        

//adding events to every buttons
UserButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e){
        DefaultTableModel Usermodel = new DefaultTableModel();
        JTable Usertable = new JTable(Usermodel);
        JScrollPane scrollPane = new JScrollPane(Usertable);
        scrollPane.setBounds(200, 60, 800, 600);
        DashbaordFrame.add(scrollPane);

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


//Job_Applications
Job_Applications.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e)
    {
        DefaultTableModel Jobmodel = new DefaultTableModel();
        JTable Jobtable = new JTable(Jobmodel);
        JScrollPane scrollPane = new JScrollPane(Jobtable);
        scrollPane.setBounds(200, 60, 800, 600);
        DashbaordFrame.add(scrollPane);

        Jobmodel.addColumn("ID");
        Jobmodel.addColumn("Job_Title");
        Jobmodel.addColumn("Job_Description");
        Jobmodel.addColumn("Created_by");
        Jobmodel.addColumn("Status");

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

                Jobmodel.addRow(new Object[]{ID, Job_Title, Job_Description, Created_by, Status});
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


    //ManagePermissions
    ManagePermissions.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {

         // JOptionPane.showMessageDialog(DashbaordFrame, "Login As Admin to Manage Permissions", "Manage Permissions", JOptionPane.INFORMATION_MESSAGE);

            JFrame PermissionFrame = new JFrame("Manage Permissions");
            PermissionFrame.setSize(600, 600);
            JLabel PermissionLabel = new JLabel("Login As Admin to Manage Permissions");
            PermissionLabel.setBounds(400, 300, 500, 30);
            PermissionLabel.setFont(PermissionLabel.getFont().deriveFont(20f));
            PermissionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            PermissionLabel.setVerticalAlignment(SwingConstants.CENTER);
            PermissionLabel.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
            PermissionLabel.setOpaque(true);
            PermissionFrame.add(PermissionLabel);
            PermissionFrame.setLayout(null);

            DefaultTableModel PermissionModel = new DefaultTableModel();
            JTable PermissionTable = new JTable(PermissionModel);
            JScrollPane scrollPane = new JScrollPane(PermissionTable);
            scrollPane.setBounds(200, 60, 800, 600);
            DashbaordFrame.add(scrollPane);

            

           ImageIcon ViewIcon = new ImageIcon("/Users/gauravsaxena/Desktop/Job Application Management/ViewPNG.png");
            JButton ViewButton = new JButton("View", ViewIcon);
         
            ViewButton.setBounds(180, 60, 100, 30);
            DashbaordFrame.add(ViewButton);

            ImageIcon EditIcon = new ImageIcon("/Users/gauravsaxena/Desktop/Job Application Management/PencilIconPNG.png");
            JButton EditButton = new JButton("Edit", EditIcon);
            EditButton.setBounds(160, 100, 100, 30);
            DashbaordFrame.add(EditButton);

            ImageIcon DeleteIcon = new ImageIcon("/Users/gauravsaxena/Desktop/Job Application Management/DeleteIconPNG.png");
            JButton DeleteButton = new JButton("Delete", DeleteIcon);
            DeleteButton.setBounds(130, 140, 100, 30);
            DashbaordFrame.add(DeleteButton);

          
    
        }
    });

    }



    public static void main(String[] args) {

        new Dashboard();
    }
}
