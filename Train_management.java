import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Train_management extends JFrame {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";  // Your DB username
    private static final String PASSWORD = "gitesh90";  // Your DB password

    private JTextField trainNameField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField totalSeatsField;
    private JTextField availableSeatsField;
    private JTextArea displayArea;

    public Train_management() {
        setTitle("Train Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Train Name:"));
        trainNameField = new JTextField();
        inputPanel.add(trainNameField);

        inputPanel.add(new JLabel("Source:"));
        sourceField = new JTextField();
        inputPanel.add(sourceField);

        inputPanel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        inputPanel.add(destinationField);

        inputPanel.add(new JLabel("Total Seats:"));
        totalSeatsField = new JTextField();
        inputPanel.add(totalSeatsField);

        inputPanel.add(new JLabel("Available Seats:"));
        availableSeatsField = new JTextField();
        inputPanel.add(availableSeatsField);

        JButton addButton = new JButton("Add Train");
        addButton.addActionListener(new AddTrainAction());
        inputPanel.add(addButton);

        JButton viewButton = new JButton("View Trains");
        viewButton.addActionListener(new ViewTrainsAction());
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure JDBC driver is loaded
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found. Include the driver in your project.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SQLException(e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Inner class to handle adding a train
    private class AddTrainAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String trainName = trainNameField.getText();
            String source = sourceField.getText();
            String destination = destinationField.getText();
            int totalSeats = Integer.parseInt(totalSeatsField.getText());
            int availableSeats = Integer.parseInt(availableSeatsField.getText());

            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO Trains (TrainName, Source, Destination, TotalSeats, AvailableSeats) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, trainName);
                stmt.setString(2, source);
                stmt.setString(3, destination);
                stmt.setInt(4, totalSeats);
                stmt.setInt(5, availableSeats);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Train added successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add train.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Inner class to handle viewing trains
    private class ViewTrainsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayArea.setText(""); // Clear the display area
            try (Connection conn = getConnection()) {
                String sql = "SELECT * FROM Trains";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                StringBuilder sb = new StringBuilder();
                sb.append("TrainID | TrainName | Source | Destination | TotalSeats | AvailableSeats\n");
                sb.append("----------------------------------------------------------\n");
                while (rs.next()) {
                    int trainID = rs.getInt("TrainID");
                    String trainName = rs.getString("TrainName");
                    String source = rs.getString("Source");
                    String destination = rs.getString("Destination");
                    int totalSeats = rs.getInt("TotalSeats");
                    int availableSeats = rs.getInt("AvailableSeats");

                    sb.append(String.format("%d | %s | %s | %s | %d | %d\n", trainID, trainName, source, destination, totalSeats, availableSeats));
                }
                displayArea.setText(sb.toString());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Train_management app = new Train_management();
            app.setVisible(true);
        });
    }
}
