import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/zoo";
    private static final String USER = "root";
    private static final String PASSWORD = "Nurajudo007";

    // Получение всех клеток
    public static List<Cage> getAllCages() {
        List<Cage> cages = new ArrayList<>();
        String query = "SELECT * FROM cages";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                cages.add(new Cage(
                        resultSet.getString("name"),
                        resultSet.getInt("number"),
                        resultSet.getString("size"),
                        resultSet.getString("animal_size"),
                        resultSet.getInt("max_animals")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cages;
    }

    // Добавление клетки
    public static void addCage(Cage cage) {
        String query = "INSERT INTO cages (name, number, size, animal_size, max_animals) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cage.getName());
            statement.setInt(2, cage.getNumber());
            statement.setString(3, cage.getSize());
            statement.setString(4, cage.getAnimalSize());
            statement.setInt(5, cage.getMaxAnimals());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление клетки
    public static void deleteCage(int cageNumber) {
        String query = "DELETE FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, cageNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение всех животных
    public static List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM animals";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                animals.add(new Animal(
                        resultSet.getString("name"),
                        resultSet.getBoolean("predator"),
                        resultSet.getString("size"),
                        resultSet.getInt("cage_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    // Добавление животного
    public static void addAnimal(Animal animal) {
        String checkQuery = "SELECT max_animals, (SELECT COUNT(*) FROM animals WHERE cage_id = ?) AS current_count FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setInt(1, animal.getCageNumber());
            checkStatement.setInt(2, animal.getCageNumber());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int maxAnimals = resultSet.getInt("max_animals");
                int currentAnimals = resultSet.getInt("current_count");

                if (currentAnimals >= maxAnimals) {
                    System.out.println("❌ Cage is full. Cannot add more animals.");
                    return;
                }
            } else {
                System.out.println("❌ Cage not found!");
                return;
            }

            String query = "INSERT INTO animals (name, predator, size, cage_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, animal.getName());
                statement.setBoolean(2, animal.isPredator());
                statement.setString(3, animal.getSize());
                statement.setInt(4, animal.getCageNumber());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Поиск животного по имени
    public static Animal findAnimalByName(String name) {
        String query = "SELECT * FROM animals WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Animal(
                        resultSet.getString("name"),
                        resultSet.getBoolean("predator"),
                        resultSet.getString("size"),
                        resultSet.getInt("cage_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteAnimal(String name) {
        String query = "DELETE FROM animals WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("✅ Animal deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
