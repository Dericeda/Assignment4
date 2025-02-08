import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ZooGUI extends JFrame {
    private JTextField cageNameField, cageNumberField, maxAnimalsField;
    private JComboBox<String> cageSizeBox, animalSizeBox;
    private JTextArea displayCagesArea;

    private JTextField animalNameField, cageIdField;
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

    // Панель для управления клетками
    private JPanel createCagesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Cage Name:"));
        cageNameField = new JTextField();
        inputPanel.add(cageNameField);

        inputPanel.add(new JLabel("Cage Number:"));
        cageNumberField = new JTextField();
        inputPanel.add(cageNumberField);

        inputPanel.add(new JLabel("Cage Size:"));
        cageSizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        inputPanel.add(cageSizeBox);

        inputPanel.add(new JLabel("Allowed Animal Size:"));
        animalSizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        inputPanel.add(animalSizeBox);

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
                String size = (String) cageSizeBox.getSelectedItem();
                String animalSize = (String) animalSizeBox.getSelectedItem();
                int maxAnimals = Integer.parseInt(maxAnimalsField.getText().trim());

                Cage cage = new Cage(name, number, size, animalSize, maxAnimals);
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
                displayCagesArea.append(cage + "\n");
            }
        });

        return panel;
    }

    // Панель для управления животными
    private JPanel createAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Animal Name:"));
        animalNameField = new JTextField();
        inputPanel.add(animalNameField);

        inputPanel.add(new JLabel("Predator:"));
        predatorCheckBox = new JCheckBox();
        inputPanel.add(predatorCheckBox);

        inputPanel.add(new JLabel("Animal Size:"));
        animalSizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        inputPanel.add(animalSizeBox);

        inputPanel.add(new JLabel("Cage Number:"));
        cageIdField = new JTextField();
        inputPanel.add(cageIdField);

        JButton addAnimalButton = new JButton("Add Animal");
        JButton removeAnimalButton = new JButton("Remove Animal");
        inputPanel.add(addAnimalButton);
        inputPanel.add(removeAnimalButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayAnimalsArea = new JTextArea();
        displayAnimalsArea.setEditable(false);
        panel.add(new JScrollPane(displayAnimalsArea), BorderLayout.CENTER);

        JButton loadAnimalsButton = new JButton("Load Animals");
        panel.add(loadAnimalsButton, BorderLayout.SOUTH);

        // Обработчики кнопок
        addAnimalButton.addActionListener(e -> {
            try {
                String name = animalNameField.getText().trim();
                boolean predator = predatorCheckBox.isSelected();
                String size = (String) animalSizeBox.getSelectedItem();
                int cageNumber = Integer.parseInt(cageIdField.getText().trim());

                Animal animal = new Animal(name, predator, size, cageNumber);
                DatabaseHelper.addAnimal(animal);
                JOptionPane.showMessageDialog(this, "Animal added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Cage Number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeAnimalButton.addActionListener(e -> {
            String name = animalNameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter the name of the animal to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DatabaseHelper.deleteAnimal(name);
            JOptionPane.showMessageDialog(this, "Animal removed successfully!");
        });

        loadAnimalsButton.addActionListener(e -> {
            List<Animal> animals = DatabaseHelper.getAllAnimals();
            displayAnimalsArea.setText("Animals in the Zoo:\n");
            for (Animal animal : animals) {
                displayAnimalsArea.append(animal + "\n");
            }
        });

        return panel;
    }

    // Панель для поиска животных
    private JPanel createSearchAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());

        searchPanel.add(new JLabel("Enter Animal Name:"));
        searchAnimalField = new JTextField(15);
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
