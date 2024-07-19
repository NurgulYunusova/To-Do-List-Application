import java.sql.*;

public class DatabaseConnection {
//    private static Connection connection = null;
//
//    public DatabaseConnection() {
//        try {
//            connection = DriverManager.getConnection(Database.DATABASE_URL, Database.USERNAME, Database.PASSWORD);
//            System.out.println("Connection is successful");
//        } catch (SQLException ex) {
//            System.out.println("Connection failed");
//            ex.printStackTrace();
//        }
//    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Database.DATABASE_URL, Database.USERNAME, Database.PASSWORD);
    }
}
