import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerManagement extends JFrame implements ActionListener {

    // Components for the Customer Management System
    JLabel labelTitle, labelCustomerName, labelCustomerID, labelCustomerAge, labelCustomerGender, labelCustomerContact;
    JTextField textCustomerName, textCustomerID, textCustomerAge, textCustomerContact;
    JComboBox<String> comboCustomerGender;
    JButton buttonAddCustomer, buttonViewCustomers, buttonDeleteCustomer, buttonExit;
    JTextArea textAreaDisplay;

    // List to store customer information
    ArrayList<String> customerList = new ArrayList<>();

    // Constructor to setup the GUI components
    public CustomerManagement() {
        // Set the frame title
        setTitle("Customer Management System");

        // Set the layout for the frame
        setLayout(new GridLayout(8, 2, 10, 10));

        // Initialize components
        labelTitle = new JLabel("Customer Management System", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelCustomerName = new JLabel("Customer Name: ");
        labelCustomerID = new JLabel("Customer ID: ");
        labelCustomerAge = new JLabel("Customer Age: ");
        labelCustomerGender = new JLabel("Customer Gender: ");
        labelCustomerContact = new JLabel("Contact Number: ");

        textCustomerName = new JTextField(20);
        textCustomerID = new JTextField(10);
        textCustomerAge = new JTextField(10);
        textCustomerContact = new JTextField(15);

        // ComboBox for gender
        String[] genders = {"Male", "Female", "Other"};
        comboCustomerGender = new JComboBox<>(genders);

        // Buttons for actions
        buttonAddCustomer = new JButton("Add Customer");
        buttonViewCustomers = new JButton("View Customers");
        buttonDeleteCustomer = new JButton("Delete Customer");
        buttonExit = new JButton("Exit");

        // Text area to display customer information
        textAreaDisplay = new JTextArea(10, 40);
        textAreaDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaDisplay);

        // Add components to the frame
        add(labelTitle);
        add(new JLabel(""));  // Empty label for spacing
        add(labelCustomerName);
        add(textCustomerName);
        add(labelCustomerID);
        add(textCustomerID);
        add(labelCustomerAge);
        add(textCustomerAge);
        add(labelCustomerGender);
        add(comboCustomerGender);
        add(labelCustomerContact);
        add(textCustomerContact);
        add(buttonAddCustomer);
        add(buttonViewCustomers);
        add(buttonDeleteCustomer);
        add(buttonExit);
        add(new JLabel("Customer Information:"));
        add(scrollPane);

        // Set size and visibility of the frame
        setSize(600, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add action listeners to buttons
        buttonAddCustomer.addActionListener(this);
        buttonViewCustomers.addActionListener(this);
        buttonDeleteCustomer.addActionListener(this);
        buttonExit.addActionListener(this);
    }

    // Handle button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAddCustomer) {
            // Get input customer details
            String customerName = textCustomerName.getText();
            String customerID = textCustomerID.getText();
            String customerAge = textCustomerAge.getText();
            String customerGender = comboCustomerGender.getSelectedItem().toString();
            String customerContact = textCustomerContact.getText();

            // Validate input
            if (customerName.isEmpty() || customerID.isEmpty() || customerAge.isEmpty() || customerContact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add customer to the list
            customerList.add("Customer ID: " + customerID + ", Name: " + customerName + ", Age: " + customerAge +
                    ", Gender: " + customerGender + ", Contact: " + customerContact);
            JOptionPane.showMessageDialog(this, "Customer added successfully!");

            // Clear input fields
            textCustomerName.setText("");
            textCustomerID.setText("");
            textCustomerAge.setText("");
            textCustomerContact.setText("");

        } else if (e.getSource() == buttonViewCustomers) {
            // Display all customers in the text area
            if (customerList.isEmpty()) {
                textAreaDisplay.setText("No customers available.");
            } else {
                StringBuilder customerInfo = new StringBuilder();
                for (String customer : customerList) {
                    customerInfo.append(customer).append("\n");
                }
                textAreaDisplay.setText(customerInfo.toString());
            }

        } else if (e.getSource() == buttonDeleteCustomer) {
            // Get customer ID to delete
            String customerIDToDelete = JOptionPane.showInputDialog("Enter Customer ID to delete:");
            boolean found = false;

            // Search and delete customer from the list
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).contains("Customer ID: " + customerIDToDelete)) {
                    customerList.remove(i);
                    found = true;
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == buttonExit) {
            // Exit the application
            System.exit(0);
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new CustomerManagement();
    }
}
