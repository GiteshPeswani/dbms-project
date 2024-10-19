import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class RAILWAYLOGIN extends JFrame {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";  // Your DB username
    private static final String PASSWORD = "gitesh90";  // Your DB password

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RAILWAYLOGIN() {
        setTitle("Railway Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = new JPanel();
        JButton signupButton = new JButton("Signup");
        JButton loginButton = new JButton("Login");

        signupButton.addActionListener(e -> showSignupScreen());
        loginButton.addActionListener(e -> showLoginScreen());

        menuPanel.add(signupButton);
        menuPanel.add(loginButton);

        // Adding panels for signup and login
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(createSignupPanel(), "Signup");
        mainPanel.add(createLoginPanel(), "Login");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
    }

    // Panel for signup screen
    private JPanel createSignupPanel() {
        JPanel signupPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField signupUsernameField = new JTextField();
        JPasswordField signupPasswordField = new JPasswordField();
        JButton submitSignupButton = new JButton("Submit");

        submitSignupButton.addActionListener(e -> signup(signupUsernameField, signupPasswordField));

        signupPanel.add(usernameLabel);
        signupPanel.add(signupUsernameField);
        signupPanel.add(passwordLabel);
        signupPanel.add(signupPasswordField);
        signupPanel.add(new JLabel());
        signupPanel.add(submitSignupButton);

        return signupPanel;
    }

    // Panel for login screen
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton submitLoginButton = new JButton("Login");

        submitLoginButton.addActionListener(e -> login());

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(submitLoginButton);

        return loginPanel;
    }

    private void showSignupScreen() {
        cardLayout.show(mainPanel, "Signup");
    }

    private void showLoginScreen() {
        cardLayout.show(mainPanel, "Login");
    }

    // Signup method with GUI
    private void signup(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String hashedPassword = hashPassword(password); // Hash the password

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Users1 (Username, PasswordHash) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Signup successful!");
                cardLayout.show(mainPanel, "Menu");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Login method with GUI
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String hashedPassword = hashPassword(password); // Hash the password

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Users1 WHERE Username = ? AND PasswordHash = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to hash passwords using SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           RAILWAYLOGIN frame = new RAILWAYLOGIN();
            frame.setVisible(true);
        });
    }
}
