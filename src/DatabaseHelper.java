// DatabaseHelper.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/zoo";
    private static final String USER = "root";
    private static final String PASSWORD = "Nurajudo007";  // ❗ Замените на свой пароль

    // --------------- Операции с зоопарками ---------------
    public static void addZoo(Zoo zoo) {
        String query = "INSERT INTO zoos (number, name) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, zoo.getNumber());
            statement.setString(2, zoo.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Zoo> getAllZoos() {
        List<Zoo> zoos = new ArrayList<>();
        String query = "SELECT * FROM zoos";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Zoo zoo = new Zoo(
                        resultSet.getInt("number"),
                        resultSet.getString("name")
                );
                zoos.add(zoo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zoos;
    }

    public static Zoo findZooByNumber(int number) {
        String query = "SELECT * FROM zoos WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Zoo(
                        resultSet.getInt("number"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateZoo(Zoo zoo) {
        String query = "UPDATE zoos SET name = ? WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, zoo.getName());
            statement.setInt(2, zoo.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteZoo(int number) {
        // Перед удалением зоопарка проверяем, что в нём нет клеток
        List<Cage> cages = getCagesByZoo(number);
        if (!cages.isEmpty()) {
            System.out.println("Cannot delete zoo: it contains cages.");
            return;
        }
        String query = "DELETE FROM zoos WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --------------- Операции с клетками ---------------
    public static void addCage(Cage cage) {
        String query = "INSERT INTO cages (number, name, total_capacity, current_animals, zoo_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cage.getNumber());
            statement.setString(2, cage.getName());
            statement.setInt(3, cage.getTotalCapacity());
            statement.setInt(4, cage.getCurrentAnimals());
            statement.setInt(5, cage.getZooNumber());
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
                Cage cage = new Cage(
                        resultSet.getInt("number"),
                        resultSet.getString("name"),
                        resultSet.getInt("total_capacity"),
                        resultSet.getInt("current_animals"),
                        resultSet.getInt("zoo_number")
                );
                cages.add(cage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cages;
    }

    public static List<Cage> getCagesByZoo(int zooNumber) {
        List<Cage> cages = new ArrayList<>();
        String query = "SELECT * FROM cages WHERE zoo_number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, zooNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cage cage = new Cage(
                        resultSet.getInt("number"),
                        resultSet.getString("name"),
                        resultSet.getInt("total_capacity"),
                        resultSet.getInt("current_animals"),
                        resultSet.getInt("zoo_number")
                );
                cages.add(cage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cages;
    }

    public static Cage findCageByNumber(int number) {
        String query = "SELECT * FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Cage(
                        resultSet.getInt("number"),
                        resultSet.getString("name"),
                        resultSet.getInt("total_capacity"),
                        resultSet.getInt("current_animals"),
                        resultSet.getInt("zoo_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateCage(Cage cage) {
        String query = "UPDATE cages SET name = ?, total_capacity = ?, current_animals = ?, zoo_number = ? WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cage.getName());
            statement.setInt(2, cage.getTotalCapacity());
            statement.setInt(3, cage.getCurrentAnimals());
            statement.setInt(4, cage.getZooNumber());
            statement.setInt(5, cage.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCage(int number) {
        // Перед удалением клетки проверяем, что в ней нет животных
        List<Animal> animals = getAnimalsInCage(number);
        if (!animals.isEmpty()) {
            System.out.println("Cannot delete cage: it contains animals.");
            return;
        }
        String query = "DELETE FROM cages WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --------------- Операции с животными ---------------
    public static void addAnimal(Animal animal) {
        String query = "INSERT INTO animals (name, predator, number_of_animals, cage_number) VALUES (?, ?, ?, ?)";
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

    public static List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM animals";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Animal animal = new Animal(
                        resultSet.getString("name"),
                        resultSet.getBoolean("predator"),
                        resultSet.getInt("number_of_animals"),
                        resultSet.getInt("cage_number")
                );
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public static List<Animal> getAnimalsInCage(int cageNumber) {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM animals WHERE cage_number = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cageNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Animal animal = new Animal(
                        resultSet.getString("name"),
                        resultSet.getBoolean("predator"),
                        resultSet.getInt("number_of_animals"),
                        resultSet.getInt("cage_number")
                );
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public static void deleteAnimalByName(String name) {
        String query = "DELETE FROM animals WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                        resultSet.getInt("cage_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод обновления животного с корректировкой заполненности клетки.
    // Алгоритм:
    // 1. Найти старую запись (oldAnimal) по имени (предполагаем, что имя уникально).
    // 2. Если клетка не изменилась, то вычислить: newOccupancy = currentAnimals - oldCount + newCount.
    //    Если новая занятость превышает вместимость – ошибка.
    // 3. Если клетка изменилась – из старой клетки вычесть старую численность,
    //    а в новой добавить новое число (проверив вместимость).
    // 4. Обновить запись в таблице animals.
    public static void updateAnimal(String oldName, Animal updatedAnimal) {
        Animal oldAnimal = findAnimalByName(oldName);
        if (oldAnimal == null) {
            System.out.println("Animal not found for update.");
            return;
        }

        if (oldAnimal.getCageNumber() == updatedAnimal.getCageNumber()) {
            Cage cage = findCageByNumber(updatedAnimal.getCageNumber());
            if (cage == null) {
                System.out.println("No such cage for update.");
                return;
            }
            int newOccupancy = cage.getCurrentAnimals() - oldAnimal.getNumberOfAnimals() + updatedAnimal.getNumberOfAnimals();
            if (newOccupancy > cage.getTotalCapacity()) {
                System.out.println("Not enough space in the cage for update.");
                return;
            }
            cage.setCurrentAnimals(newOccupancy);
            updateCage(cage);
        } else {
            Cage oldCage = findCageByNumber(oldAnimal.getCageNumber());
            Cage newCage = findCageByNumber(updatedAnimal.getCageNumber());
            if (oldCage == null || newCage == null) {
                System.out.println("One of the cages does not exist for update.");
                return;
            }
            oldCage.setCurrentAnimals(oldCage.getCurrentAnimals() - oldAnimal.getNumberOfAnimals());
            if (newCage.getCurrentAnimals() + updatedAnimal.getNumberOfAnimals() > newCage.getTotalCapacity()) {
                System.out.println("Not enough space in the new cage for update.");
                return;
            }
            newCage.setCurrentAnimals(newCage.getCurrentAnimals() + updatedAnimal.getNumberOfAnimals());
            updateCage(oldCage);
            updateCage(newCage);
        }

        String query = "UPDATE animals SET name = ?, predator = ?, number_of_animals = ?, cage_number = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, updatedAnimal.getName());
            statement.setBoolean(2, updatedAnimal.isPredator());
            statement.setInt(3, updatedAnimal.getNumberOfAnimals());
            statement.setInt(4, updatedAnimal.getCageNumber());
            statement.setString(5, oldName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

