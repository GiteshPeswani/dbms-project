import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TicketManagementSystem extends JFrame {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";  // Your DB username
    private static final String PASSWORD = "gitesh90";  // Your DB password

    private JTextField userIdField;
    private JTextField trainIdField;
    private JTextField seatNumberField;
    private JTextArea outputArea;

    public TicketManagementSystem() {
        setTitle("Ticket Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // User ID input
        add(new JLabel("User ID:"));
        userIdField = new JTextField(10);
        add(userIdField);

        // Train ID input
        add(new JLabel("Train ID:"));
        trainIdField = new JTextField(10);
        add(trainIdField);

        // Seat Number input
        add(new JLabel("Seat Number:"));
        seatNumberField = new JTextField(10);
        add(seatNumberField);

        // Buttons
        JButton bookButton = new JButton("Book Ticket");
        bookButton.addActionListener(new BookTicketAction());
        add(bookButton);

        JButton viewButton = new JButton("View Tickets");
        viewButton.addActionListener(new ViewTicketsAction());
        add(viewButton);

        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.addActionListener(new CancelTicketAction());
        add(cancelButton);

        // Output area for displaying results
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    // Action for booking a ticket
    private class BookTicketAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userId = userIdField.getText();
            String trainId = trainIdField.getText();
            String seatNumber = seatNumberField.getText();

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                // Check if the UserID exists
                String checkUserSql = "SELECT * FROM Users1 WHERE UserID = ?";
                PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
                checkUserStmt.setInt(1, Integer.parseInt(userId));
                ResultSet userResult = checkUserStmt.executeQuery();

                if (!userResult.next()) {
                    outputArea.setText("User ID does not exist!");
                    return; // Exit if user does not exist
                }

                // Now proceed to book the ticket
                String sql = "INSERT INTO Tickets (UserID, TrainID, SeatNumber) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(userId));
                stmt.setInt(2, Integer.parseInt(trainId));
                stmt.setInt(3, Integer.parseInt(seatNumber));
                stmt.executeUpdate();
                outputArea.setText("Ticket booked successfully!");

            } catch (SQLException ex) {
                outputArea.setText("Error booking ticket: " + ex.getMessage());
            }
        }
    }

    // Action for viewing tickets
    private class ViewTicketsAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userId = userIdField.getText();
            outputArea.setText(""); // Clear previous output

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM Tickets WHERE UserID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(userId));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    outputArea.append("Ticket ID: " + rs.getInt("TicketID") +
                            ", Train ID: " + rs.getInt("TrainID") +
                            ", Seat Number: " + rs.getInt("SeatNumber") +
                            ", Status: " + rs.getString("Status") +
                            ", Booking Date: " + rs.getTimestamp("BookingDate") +
                            "\n");
                }
            } catch (SQLException ex) {
                outputArea.setText("Error retrieving tickets: " + ex.getMessage());
            }
        }
    }

    // Action for canceling a ticket
    private class CancelTicketAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String ticketId = JOptionPane.showInputDialog("Enter Ticket ID to cancel:");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE Tickets SET Status = 'Cancelled' WHERE TicketID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(ticketId));
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    outputArea.setText("Ticket canceled successfully!");
                } else {
                    outputArea.setText("No ticket found with the given ID.");
                }
            } catch (SQLException ex) {
                outputArea.setText("Error canceling ticket: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicketManagementSystem::new);
    }
}
