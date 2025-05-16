import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

class ValidationsOnInputs {

    public static boolean isValidName(String NameField) {
        return NameField.matches("^[a-zA-Z\\s]{1,30}$");  // Only letters and space, max 30 chars
    }

    public static boolean isValidPhoneNumber(String PhoneNumberField) {
        return PhoneNumberField.matches("^[0-9]\\d{9}$");  // Starts with 6-9, exactly 10 digits
    }

    public static boolean isValidEmail(String EmailField) {
        return EmailField.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"); // Basic email format
    }

    public static boolean isValidAddress(String AddressField) {
        return AddressField.length() >= 5 && AddressField.length() <= 100; // Length check
    }
}


public class Applicant extends ValidationsOnInputs {
   
    public Applicant()
    {
        JComboBox <String> ApplyingForPosition = new JComboBox<>();
       

        ApplyingForPosition.addItem("Select Position"); //Adding default item to the combo box
        JFrame F= new JFrame();
        JLabel l1 = new JLabel("Registeration Form");
        //adding a Back button
        JButton Back = new JButton("Back");
        Back.setBounds(370, 720, 120, 50); //Back button
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                F.dispose(); //Close the current frame
                new LoginWindow(); //Open the login window
            }
        });
        F.add(Back); //Adding back button to the frame
       

        //adding a close button 
        JButton Close = new JButton("Close");
        Close.setBounds(500, 720, 80, 50); //Close button
        Close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                F.dispose(); //Close the current frame
            }
        });
        F.add(Close); //Adding close button to the frame

        F.setTitle("Application Form"); //Setting title
        F.setSize(600,1000); //Setting size
        F.setLayout(null); //Setting layout
        F.setLocationRelativeTo(null); //Setting location to center
 
       
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Setting default close operation
        F.add(l1);  
        l1.setBounds(100, 50, 200, 50);

        JLabel Name = new JLabel("Name :");
        JLabel PhoneNumber = new JLabel("Phone Number :");
        JLabel Email = new JLabel("Email :");
        JLabel Address = new JLabel("Address :");
        JLabel Position = new JLabel("Position :");

       

        JButton B1 = new JButton("Apply");
        // B1.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e)
        //     {
        //         JOptionPane.showMessageDialog(F, "Application Submitted Successfully");
        //        new LoginWindow(); 
        //        //F.dispose();//Close the current frame
        //     }
        // });

        
        Name.setBounds(100, 100, 200, 50);
        PhoneNumber.setBounds(100, 150, 200, 50);
        Email.setBounds(100, 200, 200, 50);     
        Address.setBounds(100, 250, 200, 50);
        ApplyingForPosition.setBounds(300, 300, 200, 50);
        B1.setBounds(100, 370, 100, 50); // Button
       
       JTextField NameField = new JTextField(20);
         JTextField PhoneNumberField = new JTextField(20);
        JTextField EmailField = new JTextField(20);
        JTextField AddressField = new JTextField(20);
        NameField.setBounds(300, 100, 200, 50);
        PhoneNumberField.setBounds(300, 150, 200, 50);
        EmailField.setBounds(300, 200, 200, 50);
        AddressField.setBounds(300, 250, 200, 50); 
        Position.setBounds(100, 300, 200, 50);
       



//Adding Job Details after
JLabel JobID = new JLabel("Job ID :");
JLabel JobDescription = new JLabel("Job Description :");
JLabel CreatedBy = new JLabel("Created By :");
JLabel Status = new JLabel("Status :");

//Values 
JLabel JobIDValue = new JLabel();
JTextArea JobDescriptionValue = new JTextArea();
JLabel CreatedByValue = new JLabel();   
JLabel StatusValue = new JLabel();
 


//Setting bounds for job details
        JobID.setBounds(100, 450, 200, 50);
        JobIDValue.setBounds(300, 450, 200, 50);

        JobDescription.setBounds(100, 500, 200, 50);
        //JobDescription.setBounds(100, 500, 200, 50);
        JobDescriptionValue.setBounds(300, 500, 200, 50);
        JobDescriptionValue.setLineWrap(true);
        JobDescriptionValue.setWrapStyleWord(true);
        JobDescriptionValue.setEditable(false);


        CreatedBy.setBounds(100, 550, 200, 50);
        CreatedByValue.setBounds(300, 550, 200, 50);
        Status.setBounds(100, 600, 200, 50);
        StatusValue.setBounds(300, 600, 200, 50);

        ApplyingForPosition.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPosition = (String) ApplyingForPosition.getSelectedItem();
                if (selectedPosition != null && !selectedPosition.equals("Select Position")) {
                    // Update the job details based on the selected position
                    try {
                      
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                        String query2 = "Select ID, Job_Description, Created_By, Status from Job_applications where Job_Title = ?";
                        PreparedStatement preparedStatement = con.prepareStatement(query2);
                        preparedStatement.setString(1, selectedPosition);
                        ResultSet resultSet2 = preparedStatement.executeQuery();
                        if (resultSet2.next()) {
                            JobIDValue.setText(String.valueOf(resultSet2.getInt("ID")));
                            JobDescriptionValue.setText(resultSet2.getString("Job_Description"));
                            CreatedByValue.setText(resultSet2.getString("Created_By"));
                            StatusValue.setText(resultSet2.getString("Status"));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        F.add(Name);
        F.add(NameField);
        F.add(PhoneNumber); 
        F.add(PhoneNumberField);
        F.add(Email);
        F.add(EmailField);
        F.add(Address);
        F.add(AddressField);
        F.add(Position);
        F.add(ApplyingForPosition);
  
        F.add(JobID);
        F.add(JobIDValue);
        F.add(JobDescription);
        F.add(JobDescriptionValue);
        F.add(CreatedBy);
        F.add(CreatedByValue);
        F.add(Status);
        F.add(StatusValue);

        F.add(B1);
        F.setVisible(true);
        F.setResizable(true); //Setting resizable


        //Inserting data into Database when Button is clickecd
        B1.addActionListener(new ActionListener() {
            @Override
            //Action performed when button is clicked
                public void actionPerformed(ActionEvent e) {
            // Get the values from the text fields
            String JobID = JobIDValue.getText();
            String name = NameField.getText();
            String phoneNumber = PhoneNumberField.getText();
            String email = EmailField.getText();
            String address = AddressField.getText();
            String position = (String) ApplyingForPosition.getSelectedItem();
if(NameField.getText().trim().isEmpty() || PhoneNumberField.getText().trim().isEmpty() || EmailField.getText().trim().isEmpty() || AddressField.getText().trim().isEmpty() || ApplyingForPosition.getSelectedItem().equals("Select Position"))
{JOptionPane.showMessageDialog(F, "Please fill all the fields"); }

//validation
// ✅ VALIDATION
if (!ValidationsOnInputs.isValidName(name)) {
    JOptionPane.showMessageDialog(F, "Invalid Name. Only letters and spaces allowed (max 30 characters).");
    return;
}

if (!ValidationsOnInputs.isValidPhoneNumber(phoneNumber)) {
    JOptionPane.showMessageDialog(F, "Invalid Phone Number. It must be 10 digits starting with 0-9.");
    return;
}

if (!ValidationsOnInputs.isValidEmail(email)) {
    JOptionPane.showMessageDialog(F, "Invalid Email Format.");
    return;
}

if (!ValidationsOnInputs.isValidAddress(address)) {
    JOptionPane.showMessageDialog(F, "Invalid Address. Must be 5 to 100 characters.");
    return;
}

if (position.equals("Select Position")) {
    JOptionPane.showMessageDialog(F, "Please select a valid Position.");
    return;
}



       try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem", "root", "");
                String insertQuery = "INSERT INTO Application (Job_ID, Name, PhoneNumber, Email, Address, Position) VALUES (?, ?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
                preparedStatement.setString(1, JobID);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, address);
                preparedStatement.setString(6, position);
                preparedStatement.executeUpdate();
                
                JOptionPane.showMessageDialog(F, "Application Submitted Successfully");
                new LoginWindow(); //Open the login window
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        });

        Connection con=null;
        try{ 
            Class.forName("com.mysql.cj.jdbc.Driver"); // loading driver
            System.out.println("Driver Loaded");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplicationSystem","root",""); //Establishing connection
                System.out.println("Connection Established");

            //Query abut table
            String query = "Select Job_Title from Job_applications";
            Statement statement = con.createStatement();
            ResultSet resultSet = (statement).executeQuery(query);
        
            int rowCount = 0;
            while(resultSet.next())
            {
                rowCount++;
                
                ApplyingForPosition.addItem(resultSet.getString("Job_Title")); //Adding items to the combo box
            }
            System.out.println("Row Count: "+ rowCount);

            //Query about getting Job_ID, Job_Description, Created_By, Status when we select a job title
            // String query2 = "Select Job_ID, Job_Description, Created_By, Status from Job_applications where Job_Title = ?";
            // Statement statement2 = con.createStatement();
            // ResultSet resultSet2 = (statement2).executeQuery(query2);
            // if (resultSet2.next()) {
            //     JobIDValue.setText(String.valueOf(resultSet2.getInt("Job_ID")));
            //     JobDescriptionValue.setText(resultSet2.getString("Job_Description"));
            //     CreatedByValue.setText(resultSet2.getString("Created_By"));
            //     StatusValue.setText(resultSet2.getString("Status"));
            // }


            // while(resultSet2.next())
            // {
            //     F2.addItem(resultSet2.getString("Job_ID"));
            // }

            
        } catch(ClassNotFoundException | SQLException e) {

            System.out.println("Exception 2: "+ e.getMessage());

        } catch(Exception e) {
            System.out.println("Exception 1: "+ e.getMessage());
        }

        }
    public static void main(String[] args) {
        new Applicant();

       

    }
}
