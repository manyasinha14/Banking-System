import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    static final String USER = "root"; // your MySQL username
    static final String PASS = "1234"; // your MySQL password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println(" Database connected successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println(" JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Database connection failed!");
            System.out.println("Check these:");
            System.out.println(" Is MySQL running?");
            System.out.println(" Is database 'banking_system' created?");
            System.out.println(" Is username/password correct?");
            System.out.println(" Is mysql-connector .jar in the same folder?");
            e.printStackTrace();
        }
        return null;
    }
}
