import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    Login() {
        setTitle("Login");
        setSize(300, 200);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(120, 30, 120, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 70, 120, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 120, 100, 25);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            if (userField.getText().equals("admin") &&
                String.valueOf(passField.getPassword()).equals("admin")) {

                     AuditLogger.log("Admin Logged In");

                new Dashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        });

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}