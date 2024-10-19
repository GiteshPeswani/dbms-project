
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class Main extends JFrame implements ActionListener {
//    String jdbcUrl = "jdbc:mysql://localhost:3306/railway_management";
//    // Declare components for login
//    JLabel labelTitle, labelUsername, labelPassword;
//    JTextField textUsername;
//    JPasswordField textPassword;
//    JButton buttonLogin, buttonReset, buttonSignup;
//
//    // Constructor to setup the GUI components
//    public Main() {
//        // Set the frame title
//        setTitle("Railway Management System - Login");
//
//        // Set the layout for the frame
//        setLayout(new GridLayout(5, 2, 10, 10));
//
//        // Initialize components
//        labelTitle = new JLabel("Login to Railway Management System", JLabel.CENTER);
//        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
//        labelUsername = new JLabel("Username: ", JLabel.RIGHT);
//        labelPassword = new JLabel("Password: ", JLabel.RIGHT);
//        textUsername = new JTextField(20);
//        textPassword = new JPasswordField(20);
//        buttonLogin = new JButton("Login");
//        buttonReset = new JButton("Reset");
//        buttonSignup = new JButton("Sign Up");  // New Sign Up button
//
//        // Add components to the frame
//        add(labelTitle);
//        add(new JLabel(""));  // Empty label for spacing
//        add(labelUsername);
//        add(textUsername);
//        add(labelPassword);
//        add(textPassword);
//        add(buttonLogin);
//        add(buttonReset);
//        add(new JLabel("New User?"));
//        add(buttonSignup);  // Adding Sign Up button
//
//        // Set size and visibility of the frame
//        setSize(400, 250);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Add action listeners to buttons
//        buttonLogin.addActionListener(this);
//        buttonReset.addActionListener(this);
//        buttonSignup.addActionListener(this);  // Add listener to Sign Up button
//    }
//
//    // Handle button actions
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == buttonLogin) {
//            // Handle login logic here (you can add database connection or validation logic)
//            String username = textUsername.getText();
//            String password = new String(textPassword.getPassword());
//            boolean isLoggedIn=UserLogin.loginUser(username,password);
//
//            // For demo purposes, check if username is "admin" and password is "password"
//            if (isLoggedIn) {
//                JOptionPane.showMessageDialog(null, "Login Successful!" );
//                openDashboard();  // Open the dashboard after successful login
//            } else {
//                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else if (e.getSource() == buttonReset) {
//            // Clear the input fields
//            textUsername.setText("");
//            textPassword.setText("");
//        } else if (e.getSource() == buttonSignup) {
//            // Open the Sign Up form when the user clicks "Sign Up"
//            openSignupForm();
//        }
//    }
//
//    // Method to open Sign Up form
//    public void openSignupForm() {
//        // Create a new Sign Up frame
//        JFrame signupFrame = new JFrame("Sign Up");
//
//        // Set layout for Sign Up form
//        signupFrame.setLayout(new GridLayout(4, 2, 10, 10));
//
//        // Create components for Sign Up form
//        JLabel labelNewUsername = new JLabel("New Username: ", JLabel.RIGHT);
//        JTextField textNewUsername = new JTextField(20);
//        JLabel labelNewPassword = new JLabel("New Password: ", JLabel.RIGHT);
//        JPasswordField textNewPassword = new JPasswordField(20);
//        JButton buttonSubmit = new JButton("Submit");
//        JButton buttonCancel = new JButton("Cancel");
//
//        // Add components to the Sign Up frame
//        signupFrame.add(labelNewUsername);
//        signupFrame.add(textNewUsername);
//        signupFrame.add(labelNewPassword);
//        signupFrame.add(textNewPassword);
//        signupFrame.add(buttonSubmit);
//        signupFrame.add(buttonCancel);
//
//        // Set size and visibility of the Sign Up frame
//        signupFrame.setSize(400, 200);
//        signupFrame.setVisible(true);
//
//        // Add action listeners for buttons
//        buttonSubmit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle sign-up logic (save user data)
//                String newUsername = textNewUsername.getText();
//                String newPassword = new String(textNewPassword.getPassword());
//
//                // Simulate user creation (in real application, store in database)
//                if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
//                    JOptionPane.showMessageDialog(signupFrame, "Sign Up Successful!");
//                    signupFrame.dispose();  // Close the sign-up frame
//                } else {
//                    JOptionPane.showMessageDialog(signupFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                signupFrame.dispose();  // Close the sign-up frame
//            }
//        });
//    }
//
//    // Method to open the dashboard after successful login
//    public void openDashboard() {
//        // Create a new Dashboard frame
//        JFrame dashboardFrame = new JFrame("Railway Management System - Dashboard");
//
//        // Set layout for the dashboard (GridLayout for options)
//        dashboardFrame.setLayout(new GridLayout(5, 1, 10, 10));
//
//        // Create buttons for different management options
//        // JButton buttonRailwayMgmt = new JButton("Railway Management");
//        JButton buttonCustomerManagement = new JButton("Customer Management");
//        JButton buttonRailwayTicketBookingSystem = new JButton("Ticket Booking");
//        JButton buttonTrainManagementSystem = new JButton("Train Management");
//        JButton buttonLogout = new JButton("Logout");
//
//        // Add buttons to the dashboard frame
//        // dashboardFrame.add(buttonRailwayMgmt);
//        dashboardFrame.add(buttonCustomerManagement);
//        dashboardFrame.add(buttonRailwayTicketBookingSystem);
//        dashboardFrame.add(buttonTrainManagementSystem);
//        dashboardFrame.add(buttonLogout);
//
//        // Set size and visibility of the dashboard frame
//        dashboardFrame.setSize(400, 300);
//        dashboardFrame.setVisible(true);
//
//        // Add action listeners for the dashboard buttons
//
//
//        buttonCustomerManagement.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(dashboardFrame, "Customer Management Module");
//                // Open customer management module (to be implemented)
//                new CustomerManagement();
//            }
//        });
//
//        buttonRailwayTicketBookingSystem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(dashboardFrame, "Ticket Booking Module");
//                // Open ticket booking module (to be implemented)
//                new RailwayBookingSystem();
//            }
//        });
//
//        buttonTrainManagementSystem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(dashboardFrame, "Train Management Module");
//                // Open train management module (to be implemented)
//                new TrainManagementSystem();
//            }
//        });
//
//        buttonLogout.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dashboardFrame.dispose();  // Logout and close the dashboard
//                JOptionPane.showMessageDialog(null, "Logged Out Successfully!");
//            }
//        });
//    }
//
//    // Main method to run the program
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run(){
//                new Main().setVisible(true);;
//                DatabaseConnection databaseConnection = new DatabaseConnection();
//                UserLogin userLogin=new UserLogin();
//                Usersignup usersignup=new Usersignup();
//
//                // dbmsconn userInputHandler = new dbmsconn();
//
//
//            }
//        });
//
//
//
//
//    }
//}
