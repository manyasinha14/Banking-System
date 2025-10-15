import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class RegisterFrame extends JFrame {
    JTextField nameField, emailField;
    JPasswordField passField;
    JButton registerBtn;
    int otp;

    public RegisterFrame() {
        setTitle("User Registration");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 30, 150, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(130, 70, 150, 25);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 110, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(130, 110, 150, 25);
        add(passField);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(100, 160, 120, 30);
        add(registerBtn);

        registerBtn.addActionListener(e -> registerUser());
        setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        if (UserDAO.register(name, email, pass)) {
            otp = new Random().nextInt(9000) + 1000;
            JOptionPane.showMessageDialog(this, "Your OTP is: " + otp);
            String entered = JOptionPane.showInputDialog(this, "Enter OTP:");
            if (String.valueOf(otp).equals(entered)) {
                UserDAO.verifyEmail(email);
                JOptionPane.showMessageDialog(this, "Email verified! You can now log in.");
                new LoginFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect OTP!");
            }
        }
    }
}
