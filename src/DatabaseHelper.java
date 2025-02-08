// DatabaseHelper.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/zoo";
    private static final String USER = "root";
    private static final String PASSWORD = "Nurajudo007";

    public static void addCage(Cage cage) {
        String query = "INSERT INTO cages (name, number, max_animals) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cage.getName());
            statement.setInt(2, cage.getNumber());
            statement.setInt(3, cage.getMaxAnimals());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCage(int number) {
        String query = "DELETE FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Cage> getAllCages() {
        List<Cage> cages = new ArrayList<>();
        String query = "SELECT * FROM cages";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                cages.add(new Cage(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("number"),
                        resultSet.getInt("max_animals")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cages;
    }

    public static void updateCage(Cage cage) {
        String query = "UPDATE cages SET max_animals = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cage.getMaxAnimals());
            statement.setInt(2, cage.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAnimal(Animal animal) {
        String query = "INSERT INTO animals (name, predator, number_of_animals, cage_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, animal.getName());
            statement.setBoolean(2, animal.isPredator());
            statement.setInt(3, animal.getNumberOfAnimals());
            statement.setInt(4, animal.getCageNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                        resultSet.getInt("number_of_animals"),
                        resultSet.getInt("cage_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public static Cage findCageByNumber(int number) {
        String query = "SELECT * FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Cage(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("number"),
                        resultSet.getInt("max_animals")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                        resultSet.getInt("number_of_animals"),
                        resultSet.getInt("cage_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

