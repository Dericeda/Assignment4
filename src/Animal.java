public class Animal {
    private String name;
    private boolean predator;
    private String size;  // Small, Medium, Large
    private int cageNumber; // Номер клетки, в которой находится животное

    public Animal(String name, boolean predator, String size, int cageNumber) {
        this.name = name;
        this.predator = predator;
        this.size = size;
        this.cageNumber = cageNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isPredator() {
        return predator;
    }

    public String getSize() {
        return size;
    }

    public int getCageNumber() {
        return cageNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s, Predator: %s, Size: %s, Cage Number: %d",
                name, predator ? "Yes" : "No", size, cageNumber
        );
    }
}

