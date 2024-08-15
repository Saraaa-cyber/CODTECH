import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        new LoginForm();
    }
}

// Login Form
class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50));
        panel.setLayout(new GridBagLayout());
        
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = new Color(200, 200, 200);
        Color fieldColor = new Color(255, 255, 255);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Inventory Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(fieldFont);
        usernameField.setForeground(fieldColor);
        usernameField.setBackground(new Color(70, 70, 70));
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        passwordField.setForeground(fieldColor);
        passwordField.setBackground(new Color(70, 70, 70));
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (user.equals("Admin") && pass.equals("Admin678")) {
                new InventoryGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        });

        panel.add(loginButton, gbc);
        add(panel);

        setVisible(true);
    }
}

// Product Class
class Product {
    String id, name;
    int quantity;
    double price;

    public Product(String id, String name, int quantity, double price) {
        this.id = id; this.name = name; this.quantity = quantity; this.price = price;
    }
}

// Inventory Management and GUI
class InventoryGUI extends JFrame {
    List<Product> products = new ArrayList<>();
    DefaultTableModel tableModel;

    public InventoryGUI() {
        setTitle("Inventory Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model with columns
        String[] columnNames = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton(buttonPanel, "Add", e -> addProduct());
        addButton(buttonPanel, "Edit", e -> editProduct());
        addButton(buttonPanel, "Delete", e -> deleteProduct());
        addButton(buttonPanel, "Low Stock", e -> showLowStockReport());
        addButton(buttonPanel, "View All", e -> displayAllProducts());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addButton(JPanel panel, String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        panel.add(button);
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("ID:");
        String name = JOptionPane.showInputDialog("Name:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));
        double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
        products.add(new Product(id, name, quantity, price));
        displayAllProducts();
    }

    private void editProduct() {
        String id = JOptionPane.showInputDialog("Enter Product ID to Edit:");
        for (Product p : products) {
            if (p.id.equals(id)) {
                p.name = JOptionPane.showInputDialog("New Name:", p.name);
                p.quantity = Integer.parseInt(JOptionPane.showInputDialog("New Quantity:", p.quantity));
                p.price = Double.parseDouble(JOptionPane.showInputDialog("New Price:", p.price));
                displayAllProducts();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Product not found!");
    }

    private void deleteProduct() {
        String id = JOptionPane.showInputDialog("Enter Product ID to Delete:");
        products.removeIf(p -> p.id.equals(id));
        displayAllProducts();
    }

    private void showLowStockReport() {
        int threshold = Integer.parseInt(JOptionPane.showInputDialog("Low Stock Threshold:"));
        tableModel.setRowCount(0);
        for (Product p : products) {
            if (p.quantity < threshold) {
                tableModel.addRow(new Object[]{p.id, p.name, p.quantity, p.price});
            }
        }
    }

    private void displayAllProducts() {
        tableModel.setRowCount(0); // Clear the table
        for (Product p : products) {
            tableModel.addRow(new Object[]{p.id, p.name, p.quantity, p.price});
        }
    }
}
