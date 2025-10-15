import javax.swing.*;

public class LoginFrame extends JFrame {
    JTextField emailField;
    JPasswordField passField;
    JButton loginBtn, registerBtn;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(130, 30, 150, 25);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(130, 70, 150, 25);
        add(passField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 120, 100, 30);
        add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(160, 120, 100, 30);
        add(registerBtn);

        loginBtn.addActionListener(e -> loginUser());
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        setVisible(true);
    }

    private void loginUser() {
        String email = emailField.getText();
        String pass = new String(passField.getPassword());
        User user = UserDAO.login(email, pass);

        if (user != null) {
            if (!user.isVerified) {
                JOptionPane.showMessageDialog(this, "Email not verified!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Welcome " + user.name + "!");
            if (user.isAdmin)
                new AdminFrame(user);
            else
                new DashboardFrame(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }
}
