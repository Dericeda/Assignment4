import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ZooGUI extends JFrame {
    private JTextField searchField, animalNameField, cageIdField;
    private JCheckBox predatorCheckBox;
    private JTextArea displayArea;
    private JButton searchButton, addAnimalButton, loadAnimalsButton, removeAnimalButton;

    public ZooGUI() {
        setTitle("Zoo Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Создаём вкладки
        JTabbedPane tabbedPane = new JTabbedPane();

        // Вкладка управления животными
        JPanel animalsPanel = createAnimalsPanel();
        tabbedPane.addTab("Animals", animalsPanel);

        // Вкладка поиска животных
        JPanel searchPanel = createSearchPanel();
        tabbedPane.addTab("Search Animal", searchPanel);

        // Добавляем вкладки в окно
        add(tabbedPane);

        setVisible(true);
    }

    // Вкладка управления животными
    private JPanel createAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Панель ввода данных
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Animal Name:"));
        animalNameField = new JTextField();
        inputPanel.add(animalNameField);

        inputPanel.add(new JLabel("Predator:"));
        predatorCheckBox = new JCheckBox();
        inputPanel.add(predatorCheckBox);

        inputPanel.add(new JLabel("Cage ID:"));
        cageIdField = new JTextField();
        inputPanel.add(cageIdField);

        addAnimalButton = new JButton("Add Animal");
        removeAnimalButton = new JButton("Remove Animal");
        inputPanel.add(addAnimalButton);
        inputPanel.add(removeAnimalButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Текстовая область для вывода данных
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Кнопка загрузки животных
        loadAnimalsButton = new JButton("Load Animals");
        panel.add(loadAnimalsButton, BorderLayout.SOUTH);

        // Обработчики событий
        addAnimalButton.addActionListener(e -> addAnimal());
        removeAnimalButton.addActionListener(e -> removeAnimal());
        loadAnimalsButton.addActionListener(e -> loadAnimals());

        return panel;
    }

    // Вкладка поиска животных
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Поле для поиска
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Enter Animal Name:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Текстовая область для отображения результатов
        JTextArea searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        panel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);

        // Обработчик поиска
        searchButton.addActionListener(e -> {
            String name = searchField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the name of the animal to search.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Animal foundAnimal = DatabaseHelper.findAnimalByName(name);
            if (foundAnimal != null) {
                searchResultArea.setText("Found Animal:\n" + foundAnimal.toString());
            } else {
                searchResultArea.setText("Animal not found!");
            }
        });

        return panel;
    }

    // Добавление животного
    private void addAnimal() {
        String name = animalNameField.getText().trim();
        String cageIdText = cageIdField.getText().trim();

        if (name.isEmpty() || cageIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cageId;
        try {
            cageId = Integer.parseInt(cageIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cage ID must be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean predator = predatorCheckBox.isSelected();
        Animal newAnimal = new Animal(name, predator);
        DatabaseHelper.addAnimal(newAnimal, cageId);

        JOptionPane.showMessageDialog(this, "Animal added successfully!");
    }

    // Удаление животного
    private void removeAnimal() {
        String name = animalNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the name of the animal to remove.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DatabaseHelper.deleteAnimal(name);
        JOptionPane.showMessageDialog(this, "Animal removed!");
    }

    // Загрузка всех животных
    private void loadAnimals() {
        List<Animal> animals = DatabaseHelper.getAllAnimals();
        displayArea.setText("Animals in the Zoo:\n");
        for (Animal animal : animals) {
            displayArea.append(animal + "\n");
        }
    }

    public static void main(String[] args) {
        new ZooGUI();
    }
}
