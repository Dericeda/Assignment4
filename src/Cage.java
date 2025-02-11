// Cage.java
public class Cage {
    private int number;
    private String name;
    private int totalCapacity;      // Изначальная вместимость
    private int currentAnimals;     // Количество животных, уже размещённых в клетке
    private int zooNumber;          // Номер зоопарка, к которому относится клетка

    public Cage(int number, String name, int totalCapacity, int zooNumber) {
        this.number = number;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.zooNumber = zooNumber;
        this.currentAnimals = 0;
    }

    // Дополнительный конструктор для загрузки из БД (где известно количество уже размещённых животных)
    public Cage(int number, String name, int totalCapacity, int currentAnimals, int zooNumber) {
        this.number = number;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.currentAnimals = currentAnimals;
        this.zooNumber = zooNumber;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getCurrentAnimals() {
        return currentAnimals;
    }

    public int getZooNumber() {
        return zooNumber;
    }

    // Свободных мест – это разница между вместимостью и уже размещёнными животными
    public int getFreeSpaces() {
        return totalCapacity - currentAnimals;
    }

    // Добавление животных в клетку – проверка на переполнение
    public void addAnimals(int count) {
        if (currentAnimals + count <= totalCapacity) {
            currentAnimals += count;
        } else {
            throw new IllegalArgumentException("Not enough space in the cage!");
        }
    }

    // Удаление животных из клетки (при удалении животного из БД)
    public void removeAnimals(int count) {
        if (currentAnimals - count >= 0) {
            currentAnimals -= count;
        } else {
            throw new IllegalArgumentException("Not enough animals to remove!");
        }
    }

    // Позволяет установить число занятых мест (при обновлении)
    public void setCurrentAnimals(int count) {
        if(count <= totalCapacity && count >= 0) {
            currentAnimals = count;
        } else {
            throw new IllegalArgumentException("Invalid animals count!");
        }
    }

    @Override
    public String toString() {
        return String.format("Cage: %s, Number: %d, Free Spaces: %d, Total Capacity: %d, Zoo Number: %d",
                name, number, getFreeSpaces(), totalCapacity, zooNumber);
    }
}


