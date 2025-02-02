import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/zoo";
    private static final String USER = "root"; // Замените на свой логин
    private static final String PASSWORD = "password"; // Замените на свой пароль

    // Получение всех клеток
    public static List<Cage> getAllCages() {
        List<Cage> cages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM cages")) {
            while (resultSet.next()) {
                cages.add(new Cage(
                        resultSet.getString("name"),
                        resultSet.getInt("number"),
                        resultSet.getString("size"),
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
        String query = "INSERT INTO cages (name, number, size, max_animals) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cage.getName());
            statement.setInt(2, cage.getNumber());
            statement.setString(3, cage.getSize());
            statement.setInt(4, cage.getMaxAnimals());
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
                Animal animal = new Animal(
                        resultSet.getString("name"),
                        resultSet.getBoolean("predator")
                );
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    // Добавление животного
    public static void addAnimal(Animal animal, int cageId) {
        String query = "INSERT INTO animals (name, predator, cage_id) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, animal.getName());
            statement.setBoolean(2, animal.isPredator());
            statement.setInt(3, cageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление животного
    public static void deleteAnimal(String name) {
        String query = "DELETE FROM animals WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
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
                        resultSet.getBoolean("predator")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если животное не найдено
    }
}
