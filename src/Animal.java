public class Animal {
    private String name;
    private boolean predator;
    private int numberOfAnimals;
    private int cageNumber;

    public Animal(String name, boolean predator, int numberOfAnimals, int cageNumber) {
        this.name = name;
        this.predator = predator;
        this.numberOfAnimals = numberOfAnimals;
        this.cageNumber = cageNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isPredator() {
        return predator;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getCageNumber() {
        return cageNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s, Predator: %s, Number: %d, Cage Number: %d",
                name, predator ? "Yes" : "No", numberOfAnimals, cageNumber
        );
    }
}
