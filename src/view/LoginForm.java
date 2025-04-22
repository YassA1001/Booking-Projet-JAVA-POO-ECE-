// LoginForm.java
package view;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginForm() {
        setTitle("Connexion - Booking App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email :");
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(emailLabel, gbc);

        emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Se connecter");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        gbc.gridy = 3;
        panel.add(messageLabel, gbc);

        add(panel);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            LoginController.handleLogin(email, password, messageLabel, this);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
