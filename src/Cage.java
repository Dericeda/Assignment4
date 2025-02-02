public class Cage {
    private String name;
    private int number;
    private String size;
    private int maxAnimals;

    // Конструктор
    public Cage(String name, int number, String size, int maxAnimals) {
        this.name = name;
        this.number = number;
        this.size = size;
        this.maxAnimals = maxAnimals;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getMaxAnimals() {
        return maxAnimals;
    }

    public void setMaxAnimals(int maxAnimals) {
        this.maxAnimals = maxAnimals;
    }

    // Переопределение toString() для удобного вывода
    @Override
    public String toString() {
        return "Cage{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", size='" + size + '\'' +
                ", maxAnimals=" + maxAnimals +
                '}';
    }
}
