public class Cage {
    private int id;               // ID клетки (в таблице cages)
    private String name;          // Название клетки
    private int number;           // Номер клетки
    private int maxAnimals;       // Максимальная вместимость
    private int currentAnimals;   // Текущее количество животных

    public Cage(int id, String name, int number, int maxAnimals) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.maxAnimals = maxAnimals;
        this.currentAnimals = 0; // Изначально клетка пустая
    }

    public Cage(String name, int number, int maxAnimals) {
        this.name = name;
        this.number = number;
        this.maxAnimals = maxAnimals;
        this.currentAnimals = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getMaxAnimals() {
        return maxAnimals;
    }

    public void setMaxAnimals(int maxAnimals) {
        this.maxAnimals = maxAnimals;
    }

    public int getFreeSpaces() {
        return maxAnimals - currentAnimals;
    }

    public void addAnimals(int count) {
        currentAnimals += count;
    }

    public void removeAnimals(int count) {
        currentAnimals -= count;
    }

    @Override
    public String toString() {
        return String.format(
                "Cage Name: %s, Number: %d, Max Animals: %d",
                name, number, maxAnimals
        );
    }
}

