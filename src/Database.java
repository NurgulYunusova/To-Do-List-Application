public class Database {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private final static String DATABASE_NAME = "todo_list_app";
    private final static String HOST = "localhost";
    private final static int PORT = 3306;

    public static final String DATABASE_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;
}
