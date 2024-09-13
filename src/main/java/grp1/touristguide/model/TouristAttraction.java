package grp1.touristguide.model;

public class TouristAttraction {

    // Attributes for the tourist attraction
    private String name;
    private String description;

    // Constructor
    public TouristAttraction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString method for easy display
    @Override
    public String toString() {
        return "TouristAttraction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


