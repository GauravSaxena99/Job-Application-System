//Interviewer : View and (can give feedback over applications).

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interviewer extends JFrame {
    public Interviewer()
    {

        JFrame I_Frame = new JFrame();
        JLabel I_Label = new JLabel("Welcome Interviewer !");//SwingConstants.CENTER);
        JLabel InterviewerDetailsLabel = new JLabel("Interviewer Details Table");
        InterviewerDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        //Adding //Adding Buttons For Edit, Delete, Update, Create, Feedback below Interviwers Details Table
        JButton EditButtonForInterviewer = new JButton("Edit");
        JButton DeleteButtonForInterviewer = new JButton("Delete");
        JButton UpdateButtonForInterviewer = new JButton("Update");
        JButton CreateButtonForInterviewer = new JButton("Create");
        JButton FeedbackButtonForInterviewer = new JButton("Feedback");

        //adding A JTextField to enter Rating under Buttons
        JLabel RatingLabel = new JLabel("Rating :");
        JTextField RatingField = new JTextField(20);

        //adding LogOut button
        JButton LogOutButton = new JButton("Log Out");

        I_Frame.setTitle("Interviewer Panel");
        I_Frame.setSize(1200,1200);
        I_Frame.add(I_Label);
        I_Frame.add(InterviewerDetailsLabel);
        I_Frame.add(LogOutButton);
        I_Frame.setLayout(null);
        I_Frame.setVisible(true);

        load_HR_Permission_IN_Table(EditButtonForInterviewer,DeleteButtonForInterviewer, UpdateButtonForInterviewer, CreateButtonForInterviewer, FeedbackButtonForInterviewer);

        I_Frame.setLocationRelativeTo(null);
        I_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        I_Frame.setResizable(true);

       
        I_Label.setBounds(400,50,200,50);
        InterviewerDetailsLabel.setBounds(50, 70, 220, 50);
        LogOutButton.setBounds(1000, 50, 100, 50);
        

//adding LogOut button functionality    
LogOutButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        I_Frame.dispose(); // Close the current frame
        new LoginWindow(); // Open the login window
    }
});

        //DefaultTableModel model 
        DefaultTableModel I_model = new DefaultTableModel();
        JTable I_Table = new JTable(I_model);
        JScrollPane I_scrollPane = new JScrollPane(I_Table);
        I_scrollPane.setBounds(50, 120, 900, 200);
        I_Frame.add(I_scrollPane);

        //adding buttons below Interviewer Details Table
        EditButtonForInterviewer.setBounds(50, 350, 100, 50);
        DeleteButtonForInterviewer.setBounds(200, 350, 100, 50);
        UpdateButtonForInterviewer.setBounds(350, 350, 100, 50);
        CreateButtonForInterviewer.setBounds(500, 350, 100, 50);
        FeedbackButtonForInterviewer.setBounds(650, 350, 100, 50);
        I_Frame.add(EditButtonForInterviewer);
        I_Frame.add(DeleteButtonForInterviewer);
        I_Frame.add(UpdateButtonForInterviewer);
        I_Frame.add(CreateButtonForInterviewer);
        I_Frame.add(FeedbackButtonForInterviewer);
        FeedbackButtonForInterviewer.setVisible(false); // Initially hidden

        I_Frame.add(RatingLabel);
        I_Frame.add(RatingField);
        RatingLabel.setBounds(50, 450, 100, 50);
        RatingField.setBounds(120, 450, 100, 50);
        RatingField.setVisible(false); // Initially hidden
        RatingLabel.setVisible(false); // Initially hidden

            // Add column headers
            I_model.addColumn("Applicant Name");
            I_model.addColumn("Phone Number");
            I_model.addColumn("Email");
            I_model.addColumn("Address");
            I_model.addColumn("Position");  
            I_model.addColumn("Interviewer");


            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
                System.out.println("Connection Established");
                Class.forName("com.mysql.cj.jdbc.Driver"); //loading driver
                System.out.println("Driver Loaded");
            String I_query2 = """
                    SELECT Application.Name, PhoneNumber, Email, Address, Position, User.Name as Interviewer FROM Application
                    LEFT JOIN User ON User.User_ID = Application.InterviewerID;
                    """;
                    PreparedStatement I_statement2 = con.prepareStatement(I_query2);
                    ResultSet I_resultSet2 = I_statement2.executeQuery();

                    while (I_resultSet2.next()) {
                        I_model.addRow(new Object[]{
                            I_resultSet2.getString("Name"),
                            I_resultSet2.getString("PhoneNumber"),
                            I_resultSet2.getString("Email"),
                            I_resultSet2.getString("Address"),
                            I_resultSet2.getString("Position"),
                            I_resultSet2.getString("Interviewer")
                        });
                        
                    }


    }catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Exception_I 1: "+ e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception_I 2: "+ e.getMessage());
        }

           //adding Action Listener to Feedback Button
    FeedbackButtonForInterviewer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int selectedRow = I_Table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(I_Frame, "Please select a row to give feedback.");
                return;
            }


            // RatingField.setVisible(true);
            //RatingLabel.setVisible(true);
            // I_Frame.revalidate();
            // I_Frame.repaint();

            String ratingText = JOptionPane.showInputDialog(I_Frame, "Enter rating (1 to 5):");

            // Cancel pressed or input is null
            if (ratingText == null) {
                JOptionPane.showMessageDialog(I_Frame, "Rating input cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

             //ratingText = RatingField.getText().trim();
            if (ratingText.isEmpty()) {
                JOptionPane.showMessageDialog(I_Frame, "Please enter a rating before submitting.");
                return;
            } try {
                int rating = Integer.parseInt(ratingText);
                if (rating < 1 || rating > 5) {
                    JOptionPane.showMessageDialog(I_Frame, "Invalid rating. Please enter a value between 1 and 5.");
                    return;
                }
    
                // Handle feedback submission here
                System.out.println("Feedback submitted: " + rating);
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(I_Frame, "Invalid input. Please enter a valid number for rating.");
            }
        }
    });
    }
    private void load_HR_Permission_IN_Table(
        JButton EditButtonForInterviewer, JButton DeleteButtonForInterviewer, JButton UpdateButtonForInterviewer, JButton CreateButtonForInterviewer, JButton FeedbackButtonForInterviewer) {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                Class.forName("com.mysql.cj.jdbc.Driver");
        
                String query = "SELECT * FROM Permissions WHERE Role = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, "Interviewer");
        
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

 
   
    public static void main(String[] args) {
        new Interviewer();
    }
}
