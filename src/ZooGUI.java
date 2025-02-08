import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ZooGUI extends JFrame {
    private JTextField cageNameField, cageNumberField, maxAnimalsField;
    private JTextArea displayCagesArea;

    private JTextField animalNameField, cageNumberFieldAnimals, numberOfAnimalsField;
    private JCheckBox predatorCheckBox;
    private JTextArea displayAnimalsArea;

    private JTextField searchAnimalField;
    private JTextArea searchResultArea;

    public ZooGUI() {
        setTitle("Zoo Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Cages", createCagesPanel());
        tabbedPane.addTab("Animals", createAnimalsPanel());
        tabbedPane.addTab("Search Animals", createSearchAnimalsPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // Вкладка для управления клетками
    private JPanel createCagesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 1, 5, 5));

        inputPanel.add(new JLabel("Cage Name:"));
        cageNameField = new JTextField();
        inputPanel.add(cageNameField);

        inputPanel.add(new JLabel("Cage Number:"));
        cageNumberField = new JTextField();
        inputPanel.add(cageNumberField);

        inputPanel.add(new JLabel("Max Animals:"));
        maxAnimalsField = new JTextField();
        inputPanel.add(maxAnimalsField);

        JButton addCageButton = new JButton("Add Cage");
        JButton deleteCageButton = new JButton("Delete Cage");
        inputPanel.add(addCageButton);
        inputPanel.add(deleteCageButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayCagesArea = new JTextArea();
        displayCagesArea.setEditable(false);
        panel.add(new JScrollPane(displayCagesArea), BorderLayout.CENTER);

        JButton loadCagesButton = new JButton("Load Cages");
        panel.add(loadCagesButton, BorderLayout.SOUTH);

        // Обработчики кнопок
        addCageButton.addActionListener(e -> {
            try {
                String name = cageNameField.getText().trim();
                int number = Integer.parseInt(cageNumberField.getText().trim());
                int maxAnimals = Integer.parseInt(maxAnimalsField.getText().trim());

                Cage cage = new Cage(name, number, maxAnimals);
                DatabaseHelper.addCage(cage);
                JOptionPane.showMessageDialog(this, "Cage added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteCageButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(cageNumberField.getText().trim());
                DatabaseHelper.deleteCage(number);
                JOptionPane.showMessageDialog(this, "Cage deleted successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Cage Number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadCagesButton.addActionListener(e -> {
            List<Cage> cages = DatabaseHelper.getAllCages();
            displayCagesArea.setText("Cages in the Zoo:\n");
            for (Cage cage : cages) {
                displayCagesArea.append(
                        String.format("Cage Name: %s, Number: %d, Max Animals: %d\n",
                                cage.getName(), cage.getNumber(), cage.getMaxAnimals())
                );
            }
        });

        return panel;
    }

    // Вкладка для управления животными
    private JPanel createAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(7, 1, 5, 5));

        inputPanel.add(new JLabel("Animal Name:"));
        animalNameField = new JTextField();
        inputPanel.add(animalNameField);

        inputPanel.add(new JLabel("Predator:"));
        predatorCheckBox = new JCheckBox();
        inputPanel.add(predatorCheckBox);

        inputPanel.add(new JLabel("Number of Animals:"));
        numberOfAnimalsField = new JTextField();
        inputPanel.add(numberOfAnimalsField);

        inputPanel.add(new JLabel("Cage Number:"));
        cageNumberFieldAnimals = new JTextField();
        inputPanel.add(cageNumberFieldAnimals);

        JButton addAnimalButton = new JButton("Add Animal");
        JButton deleteAnimalButton = new JButton("Delete Animal");
        JButton loadAnimalsButton = new JButton("Load Animals");
        inputPanel.add(addAnimalButton);
        inputPanel.add(deleteAnimalButton);
        inputPanel.add(loadAnimalsButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayAnimalsArea = new JTextArea();
        displayAnimalsArea.setEditable(false);
        panel.add(new JScrollPane(displayAnimalsArea), BorderLayout.CENTER);

        // Обработчики кнопок
        addAnimalButton.addActionListener(e -> {
            try {
                String name = animalNameField.getText().trim();
                boolean predator = predatorCheckBox.isSelected();
                int numberOfAnimals = Integer.parseInt(numberOfAnimalsField.getText().trim());
                int cageNumber = Integer.parseInt(cageNumberFieldAnimals.getText().trim());

                // Проверка существования клетки
                Cage cage = DatabaseHelper.findCageByNumber(cageNumber);
                if (cage == null) {
                    JOptionPane.showMessageDialog(this, "Cage not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Проверка вместимости клетки
                if (cage.getMaxAnimals() < numberOfAnimals) {
                    JOptionPane.showMessageDialog(this, "Not enough space in the cage!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                cage.setMaxAnimals(cage.getMaxAnimals() - numberOfAnimals); // Уменьшаем Max Animals
                DatabaseHelper.updateCage(cage);  // Обновляем клетку в базе данных

                Animal animal = new Animal(name, predator, numberOfAnimals, cageNumber);
                DatabaseHelper.addAnimal(animal);
                JOptionPane.showMessageDialog(this, "Animal(s) added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteAnimalButton.addActionListener(e -> {
            try {
                String name = animalNameField.getText().trim();
                DatabaseHelper.deleteAnimal(name);
                JOptionPane.showMessageDialog(this, "Animal deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting animal!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadAnimalsButton.addActionListener(e -> {
            List<Animal> animals = DatabaseHelper.getAllAnimals();
            displayAnimalsArea.setText("Animals in the Zoo:\n");
            for (Animal animal : animals) {
                displayAnimalsArea.append(
                        String.format("Name: %s, Predator: %s, Number: %d, Cage Number: %d\n",
                                animal.getName(),
                                animal.isPredator() ? "Yes" : "No",
                                animal.getNumberOfAnimals(),
                                animal.getCageNumber())
                );
            }
        });

        return panel;
    }

    // Вкладка для поиска животных
    private JPanel createSearchAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        searchPanel.add(new JLabel("Enter Animal Name:"));
        searchAnimalField = new JTextField();
        searchPanel.add(searchAnimalField);

        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        panel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);

        // Обработчик кнопки поиска
        searchButton.addActionListener(e -> {
            String name = searchAnimalField.getText().trim();
            Animal animal = DatabaseHelper.findAnimalByName(name);
            if (animal != null) {
                searchResultArea.setText("Animal Found:\n" + animal);
            } else {
                searchResultArea.setText("Animal not found.");
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        new ZooGUI();
    }
}
