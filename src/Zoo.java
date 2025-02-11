// Zoo.java
public class Zoo {
    private int number;
    private String name;

    public Zoo(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Zoo: %s, Number: %d", name, number);
    }
}
