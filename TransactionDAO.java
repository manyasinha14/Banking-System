import java.sql.*;
import java.util.*;

public class TransactionDAO {

    public static void recordTransaction(int userId, String type, double amount, String details) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, ?, ?, ?)");
            ps.setInt(1, userId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setString(4, details);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUserTransactions(int userId) {
        List<String> transactions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM transactions WHERE user_id=? ORDER BY created_at DESC");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(rs.getString("created_at") + " - " + rs.getString("type") +
                                 " - ₹" + rs.getDouble("amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
