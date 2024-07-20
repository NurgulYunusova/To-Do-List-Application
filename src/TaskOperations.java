import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskOperations {
    public List<Task> getTasks(int userId) {
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("created_at")
                    );

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public void addTask(Task task) {
        String query = "INSERT INTO tasks (name, description, user_id, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setInt(3, task.getUserId());
            preparedStatement.setString(4, task.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
