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

    public void updateTask(Task task, int id) {
        StringBuilder query = new StringBuilder("UPDATE tasks SET ");
        boolean first = true;

        if (task.getName() != null && !task.getName().isEmpty()) {
            query.append("name = ?");
            first = false;
        }

        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            if (!first) query.append(", ");
            query.append("description = ?");
            first = false;
        }

        if (task.getStatus() != null && !task.getStatus().isEmpty()) {
            if (!first) query.append(", ");
            query.append("status = ?");
        }


        if (!first) query.append(", ");
        query.append("created_at = ?");

        query.append(" WHERE id = ?");

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (task.getName() != null && !task.getName().isEmpty()) {
                preparedStatement.setString(paramIndex++, task.getName());
            }

            if (task.getDescription() != null && !task.getDescription().isEmpty()) {
                preparedStatement.setString(paramIndex++, task.getDescription());
            }

            if (task.getStatus() != null && !task.getStatus().isEmpty()) {
                preparedStatement.setString(paramIndex++, task.getStatus());
            }

            preparedStatement.setTimestamp(paramIndex++, task.getCreatedAt());

            preparedStatement.setInt(paramIndex, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
