public class Cage {
    private String name;
    private int number;
    private String size;
    private String animalSize;
    private int maxAnimals;

    public Cage(String name, int number, String size, String animalSize, int maxAnimals) {
        this.name = name;
        this.number = number;
        this.size = size;
        this.animalSize = animalSize;
        this.maxAnimals = maxAnimals;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getSize() {
        return size;
    }

    public String getAnimalSize() {
        return animalSize;
    }

    public int getMaxAnimals() {
        return maxAnimals;
    }

    @Override
    public String toString() {
        return String.format(
                "Cage Name: %s, Number: %d, Size: %s, Animal Size Allowed: %s, Max Animals: %d",
                name, number, size, animalSize, maxAnimals
        );
    }
}
