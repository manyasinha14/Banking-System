public class User {
    int id;
    String name, email, password;
    double balance;
    boolean isVerified;
    boolean isAdmin;

    public User(int id, String name, String email, String password, double balance, boolean isVerified, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.isVerified = isVerified;
        this.isAdmin = isAdmin;
    }
}
