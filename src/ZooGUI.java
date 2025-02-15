import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ZooGUI extends JFrame {

    private JTextField zooNameField, zooNumberField;
    private JTextArea displayZoosArea;


    private JTextField cageNameField, cageNumberField, capacityField, cageZooNumberField;
    private JTextArea displayCagesArea;


    private JTextField animalNameField, animalNumberField, animalCageNumberField;
    private JCheckBox predatorCheckBox;
    private JTextArea displayAnimalsArea;


    private JTextField searchAnimalField;
    private JTextArea searchResultArea;


    private JTextField oldAnimalNameField;
    private JTextField newAnimalNameField, newAnimalNumberField, newAnimalCageNumberField;
    private JCheckBox newPredatorCheckBox;

    public ZooGUI() {
        setTitle("Zoo Management System");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();


        tabbedPane.addTab("Zoos", createZoosPanel());
        tabbedPane.addTab("Cages", createCagesPanel());
        tabbedPane.addTab("Animals", createAnimalsPanel());
        tabbedPane.addTab("Search Animals", createSearchAnimalsPanel());
        tabbedPane.addTab("Update Animal", createUpdateAnimalPanel());

        add(tabbedPane);
        setVisible(true);
    }


    private JPanel createZoosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Zoo Name:"));
        zooNameField = new JTextField();
        inputPanel.add(zooNameField);

        inputPanel.add(new JLabel("Zoo Number:"));
        zooNumberField = new JTextField();
        inputPanel.add(zooNumberField);

        JButton addZooButton = new JButton("Add Zoo");
        inputPanel.add(addZooButton);

        JButton deleteZooButton = new JButton("Delete Zoo");
        inputPanel.add(deleteZooButton);

        JButton loadZoosButton = new JButton("Load Zoos");
        inputPanel.add(loadZoosButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayZoosArea = new JTextArea();
        displayZoosArea.setEditable(false);
        panel.add(new JScrollPane(displayZoosArea), BorderLayout.CENTER);


        addZooButton.addActionListener(e -> {
            try {
                String name = zooNameField.getText().trim();
                int number = Integer.parseInt(zooNumberField.getText().trim());
                if (DatabaseHelper.findZooByNumber(number) != null) {
                    JOptionPane.showMessageDialog(this, "Zoo with this number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Zoo zoo = new Zoo(number, name);
                DatabaseHelper.addZoo(zoo);
                JOptionPane.showMessageDialog(this, "Zoo added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Zoo Number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteZooButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(zooNumberField.getText().trim());
                DatabaseHelper.deleteZoo(number);
                JOptionPane.showMessageDialog(this, "Zoo deleted successfully (if it had no cages)!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Zoo Number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadZoosButton.addActionListener(e -> {
            List<Zoo> zoos = DatabaseHelper.getAllZoos();
            displayZoosArea.setText("Zoos in the system:\n");
            for (Zoo zoo : zoos) {
                displayZoosArea.append(zoo.toString() + "\n");
            }
        });

        return panel;
    }


    private JPanel createCagesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        inputPanel.add(new JLabel("Cage Name:"));
        cageNameField = new JTextField();
        inputPanel.add(cageNameField);

        inputPanel.add(new JLabel("Cage Number:"));
        cageNumberField = new JTextField();
        inputPanel.add(cageNumberField);

        inputPanel.add(new JLabel("Total Capacity:"));
        capacityField = new JTextField();
        inputPanel.add(capacityField);

        inputPanel.add(new JLabel("Zoo Number:"));
        cageZooNumberField = new JTextField();
        inputPanel.add(cageZooNumberField);

        JButton addCageButton = new JButton("Add Cage");
        inputPanel.add(addCageButton);

        JButton deleteCageButton = new JButton("Delete Cage");
        inputPanel.add(deleteCageButton);

        JButton loadCagesButton = new JButton("Load Cages");
        inputPanel.add(loadCagesButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayCagesArea = new JTextArea();
        displayCagesArea.setEditable(false);
        panel.add(new JScrollPane(displayCagesArea), BorderLayout.CENTER);


        addCageButton.addActionListener(e -> {
            try {
                String name = cageNameField.getText().trim();
                int number = Integer.parseInt(cageNumberField.getText().trim());
                int totalCapacity = Integer.parseInt(capacityField.getText().trim());
                int zooNumber = Integer.parseInt(cageZooNumberField.getText().trim());
                if (DatabaseHelper.findZooByNumber(zooNumber) == null) {
                    JOptionPane.showMessageDialog(this, "Zoo not found! Create a zoo first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (DatabaseHelper.findCageByNumber(number) != null) {
                    JOptionPane.showMessageDialog(this, "Cage with this number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Cage cage = new Cage(number, name, totalCapacity, zooNumber);
                DatabaseHelper.addCage(cage);
                JOptionPane.showMessageDialog(this, "Cage added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input in Cage fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteCageButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(cageNumberField.getText().trim());
                DatabaseHelper.deleteCage(number);
                JOptionPane.showMessageDialog(this, "Cage deleted successfully (if it had no animals)!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Cage Number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadCagesButton.addActionListener(e -> {
            List<Cage> cages = DatabaseHelper.getAllCages();
            displayCagesArea.setText("Cages in the system:\n");
            for (Cage cage : cages) {
                displayCagesArea.append(cage.toString() + "\n");
            }
        });

        return panel;
    }


    private JPanel createAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        inputPanel.add(new JLabel("Animal Name:"));
        animalNameField = new JTextField();
        inputPanel.add(animalNameField);

        inputPanel.add(new JLabel("Predator:"));
        predatorCheckBox = new JCheckBox();
        inputPanel.add(predatorCheckBox);

        inputPanel.add(new JLabel("Number of Animals:"));
        animalNumberField = new JTextField();
        inputPanel.add(animalNumberField);

        inputPanel.add(new JLabel("Cage Number:"));
        animalCageNumberField = new JTextField();
        inputPanel.add(animalCageNumberField);

        JButton addAnimalButton = new JButton("Add Animal");
        inputPanel.add(addAnimalButton);

        JButton deleteAnimalButton = new JButton("Delete Animal");
        inputPanel.add(deleteAnimalButton);

        JButton loadAnimalsButton = new JButton("Load Animals");
        inputPanel.add(loadAnimalsButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        displayAnimalsArea = new JTextArea();
        displayAnimalsArea.setEditable(false);
        panel.add(new JScrollPane(displayAnimalsArea), BorderLayout.CENTER);


        addAnimalButton.addActionListener(e -> {
            try {
                String name = animalNameField.getText().trim();
                boolean predator = predatorCheckBox.isSelected();
                int numberOfAnimals = Integer.parseInt(animalNumberField.getText().trim());
                int cageNumber = Integer.parseInt(animalCageNumberField.getText().trim());

                Cage cage = DatabaseHelper.findCageByNumber(cageNumber);
                if (cage == null) {
                    JOptionPane.showMessageDialog(this, "Cage not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cage.getFreeSpaces() < numberOfAnimals) {
                    JOptionPane.showMessageDialog(this, "Not enough space in the cage!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                cage.addAnimals(numberOfAnimals);
                DatabaseHelper.updateCage(cage);

                Animal animal = new Animal(name, predator, numberOfAnimals, cageNumber);
                DatabaseHelper.addAnimal(animal);
                JOptionPane.showMessageDialog(this, "Animal(s) added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input in Animal fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteAnimalButton.addActionListener(e -> {
            try {
                String name = animalNameField.getText().trim();
                DatabaseHelper.deleteAnimalByName(name);
                JOptionPane.showMessageDialog(this, "Animal(s) deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting animal!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadAnimalsButton.addActionListener(e -> {
            List<Animal> animals = DatabaseHelper.getAllAnimals();
            displayAnimalsArea.setText("Animals in the system:\n");
            for (Animal animal : animals) {
                displayAnimalsArea.append(animal.toString() + "\n");
            }
        });

        return panel;
    }


    private JPanel createSearchAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout());

        inputPanel.add(new JLabel("Animal Name to Search:"));
        searchAnimalField = new JTextField(20);
        inputPanel.add(searchAnimalField);

        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        panel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String name = searchAnimalField.getText().trim();
            Animal animal = DatabaseHelper.findAnimalByName(name);
            if (animal != null) {
                searchResultArea.setText("Animal found:\n" + animal.toString());
            } else {
                searchResultArea.setText("Animal not found.");
            }
        });

        return panel;
    }


    private JPanel createUpdateAnimalPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));

        inputPanel.add(new JLabel("Existing Animal Name:"));
        oldAnimalNameField = new JTextField();
        inputPanel.add(oldAnimalNameField);

        inputPanel.add(new JLabel("New Animal Name:"));
        newAnimalNameField = new JTextField();
        inputPanel.add(newAnimalNameField);

        inputPanel.add(new JLabel("New Predator Status:"));
        newPredatorCheckBox = new JCheckBox();
        inputPanel.add(newPredatorCheckBox);

        inputPanel.add(new JLabel("New Number of Animals:"));
        newAnimalNumberField = new JTextField();
        inputPanel.add(newAnimalNumberField);

        inputPanel.add(new JLabel("New Cage Number:"));
        newAnimalCageNumberField = new JTextField();
        inputPanel.add(newAnimalCageNumberField);

        JButton updateAnimalButton = new JButton("Update Animal");
        inputPanel.add(updateAnimalButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTextArea updateResultArea = new JTextArea();
        updateResultArea.setEditable(false);
        panel.add(new JScrollPane(updateResultArea), BorderLayout.CENTER);

        updateAnimalButton.addActionListener(e -> {
            try {
                String oldName = oldAnimalNameField.getText().trim();
                String newName = newAnimalNameField.getText().trim();
                boolean newPredator = newPredatorCheckBox.isSelected();
                int newNumber = Integer.parseInt(newAnimalNumberField.getText().trim());
                int newCageNumber = Integer.parseInt(newAnimalCageNumberField.getText().trim());


                Cage newCage = DatabaseHelper.findCageByNumber(newCageNumber);
                if (newCage == null) {
                    updateResultArea.setText("No such cage exists for update.");
                    return;
                }

                Animal updatedAnimal = new Animal(newName, newPredator, newNumber, newCageNumber);
                DatabaseHelper.updateAnimal(oldName, updatedAnimal);
                updateResultArea.setText("Animal updated successfully (if there was enough space)!");
            } catch (NumberFormatException ex) {
                updateResultArea.setText("Invalid input in update fields.");
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ZooGUI());
    }
}
