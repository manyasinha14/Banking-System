import javax.swing.*;
import java.util.List;
import java.sql.PreparedStatement;

public class DashboardFrame extends JFrame {
    User user;
    JLabel balanceLabel;

    public DashboardFrame(User user) {
        this.user = user;
        setTitle("User Dashboard");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Welcome, " + user.name);
        nameLabel.setBounds(30, 20, 300, 25);
        add(nameLabel);

        balanceLabel = new JLabel("Balance: ₹" + user.balance);
        balanceLabel.setBounds(30, 60, 200, 25);
        add(balanceLabel);

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton historyBtn = new JButton("Transaction History");

        depositBtn.setBounds(30, 100, 120, 30);
        withdrawBtn.setBounds(170, 100, 120, 30);
        transferBtn.setBounds(30, 150, 120, 30);
        historyBtn.setBounds(170, 150, 180, 30);

        add(depositBtn); add(withdrawBtn); add(transferBtn); add(historyBtn);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        transferBtn.addActionListener(e -> transfer());
        historyBtn.addActionListener(e -> viewHistory());

        setVisible(true);
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog("Enter amount to deposit:");
        double amount = Double.parseDouble(input);
        user.balance += amount;
        UserDAO.updateBalance(user.id, user.balance);
        TransactionDAO.recordTransaction(user.id, "DEPOSIT", amount, "Deposit");
        balanceLabel.setText("Balance: ₹" + user.balance);
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog("Enter amount to withdraw:");
        double amount = Double.parseDouble(input);
        if (amount > user.balance) {
            JOptionPane.showMessageDialog(this, "Insufficient funds!");
            return;
        }
        user.balance -= amount;
        UserDAO.updateBalance(user.id, user.balance);
        TransactionDAO.recordTransaction(user.id, "WITHDRAW", amount, "Withdraw");
        balanceLabel.setText("Balance: ₹" + user.balance);
    }

    private void transfer() {
        String email = JOptionPane.showInputDialog("Enter recipient email:");
        String amountStr = JOptionPane.showInputDialog("Enter amount:");
        double amount = Double.parseDouble(amountStr);

        try (var conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, email);
            var rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "User not found!");
                return;
            }
            int receiverId = rs.getInt("id");
            double receiverBalance = rs.getDouble("balance");

            if (amount > user.balance) {
                JOptionPane.showMessageDialog(this, "Insufficient funds!");
                return;
            }

            user.balance -= amount;
            receiverBalance += amount;

            UserDAO.updateBalance(user.id, user.balance);
            UserDAO.updateBalance(receiverId, receiverBalance);

            TransactionDAO.recordTransaction(user.id, "TRANSFER", amount, "Sent to " + email);
            TransactionDAO.recordTransaction(receiverId, "TRANSFER", amount, "Received from " + user.email);

            balanceLabel.setText("Balance: ₹" + user.balance);
            JOptionPane.showMessageDialog(this, "Transfer successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewHistory() {
        List<String> txns = TransactionDAO.getUserTransactions(user.id);
        JOptionPane.showMessageDialog(this, String.join("\n", txns));
    }
}
