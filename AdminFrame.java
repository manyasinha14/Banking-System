import javax.swing.*;
import java.sql.*;

public class AdminFrame extends JFrame {
    User adminUser;
    JTextArea area;

    public AdminFrame(User adminUser) {
        this.adminUser = adminUser;
        setTitle("Admin Panel");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        area = new JTextArea();
        JScrollPane pane = new JScrollPane(area);
        pane.setBounds(20, 20, 440, 250);
        add(pane);

        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn = new JButton("Delete User");
        refreshBtn.setBounds(50, 300, 120, 30);
        deleteBtn.setBounds(200, 300, 120, 30);
        add(refreshBtn);
        add(deleteBtn);

        refreshBtn.addActionListener(e -> loadUsers());
        deleteBtn.addActionListener(e -> deleteUser());

        loadUsers();
        setVisible(true);
    }

    private void loadUsers() {
        area.setText("");
        try (Connection conn = DBConnection.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, balance, is_verified FROM users");
            while (rs.next()) {
                area.append(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                            rs.getString(3) + " | ₹" + rs.getDouble(4) +
                            " | Verified: " + rs.getBoolean(5) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        String email = JOptionPane.showInputDialog(this, "Enter email to delete:");
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE email=?");
            ps.setString(1, email);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "User deleted!");
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
