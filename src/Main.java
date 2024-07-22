import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserOperations userOperations = new UserOperations();
    private static TaskOperations taskOperations = new TaskOperations();
    private static int currentUserId;

    public static void main(String[] args) {
        while (true) {
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("     Welcome to the To-Do List Application     ");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("                   Main Menu                   ");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Logout");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        getTasks();
                        break;
                    case 2:
                        addTask();
                        break;
                    case 3:
                        updateTask();
                        break;
                    case 4:
                        deleteTask();
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void register() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(0, username, password);
        userOperations.registerUser(user);
        System.out.println("User registered successfully!");
    }

    private static void login() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUserId = userOperations.loginUser(username, password);

        if (currentUserId != -1) {
            System.out.println("Login successful!");
            showMainMenu();
        } else {
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("Invalid username or password. Please try again.");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            login();
        }
    }

    private static void getTasks() throws SQLException {
        List<Task> tasks = taskOperations.getTasks(currentUserId);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found for the current user.");
        } else {
            System.out.println("-----------------------------------------------");
            System.out.println("Your tasks:");

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                System.out.println((i + 1) + ". Id: " + task.getId());
                System.out.println("   Name: " + task.getName());
                System.out.println("   Description: " + task.getDescription());
                System.out.println("   Status: " + task.getStatus());
                System.out.println("   Created At: " + task.getCreatedAt());
            }
            System.out.println("-----------------------------------------------");
        }
    }

    private static void addTask() throws SQLException {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        Task task = new Task(0, name, description, currentUserId, "Not Completed", new Timestamp(System.currentTimeMillis()));

        taskOperations.addTask(task);

        System.out.println("Task added successfully!");
    }

    private static void updateTask() throws SQLException {
        System.out.print("Enter task ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("If you don't want to change it, leave it blank.");

        System.out.print("Enter new task title: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new task description: ");
        String newDescription = scanner.nextLine();
        System.out.print("Enter new task status (Completed or Not Completed): ");
        String newStatus = scanner.nextLine();

        Task newTask = new Task(newName, newDescription, newStatus, new Timestamp(System.currentTimeMillis()));

        taskOperations.updateTask(newTask, id);

        System.out.println("Task updated successfully!");
    }

    private static void deleteTask() throws SQLException {
        System.out.print("Enter task ID to delete: ");
        int id = scanner.nextInt();

        taskOperations.deleteTask(id);
        System.out.println("Task deleted successfully!");
    }
}