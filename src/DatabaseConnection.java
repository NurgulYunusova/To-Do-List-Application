import java.sql.*;

public class DatabaseConnection {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(Database.DATABASE_URL, Database.USERNAME, Database.PASSWORD);
            System.out.println("Connection is successful");
        } catch (SQLException ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }
    }
}
