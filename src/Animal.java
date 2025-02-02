public class Animal {
    private String name;
    private boolean predator;

    // Конструктор
    public Animal(String name, boolean predator) {
        this.name = name;
        this.predator = predator;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPredator() {
        return predator;
    }

    public void setPredator(boolean predator) {
        this.predator = predator;
    }

    // Переопределение toString() для удобного вывода
    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", predator=" + predator +
                '}';
    }
}
