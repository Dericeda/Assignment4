public class Cage {
    private int number;
    private String name;
    private int totalCapacity;
    private int currentAnimals;
    private int zooNumber;

    public Cage(int number, String name, int totalCapacity, int zooNumber) {
        this.number = number;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.zooNumber = zooNumber;
        this.currentAnimals = 0;
    }


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


    public int getFreeSpaces() {
        return totalCapacity - currentAnimals;
    }


    public void addAnimals(int count) {
        if (currentAnimals + count <= totalCapacity) {
            currentAnimals += count;
        } else {
            throw new IllegalArgumentException("Not enough space in the cage!");
        }
    }



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


