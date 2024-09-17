package grp1.touristguide.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TouristAttraction {

    // Attributes for the tourist attraction
    private String name;
    private String description;
    private String city;
    private List<Tag> tags;

    // Constructor
    public TouristAttraction(String name, String description, String city, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = Objects.requireNonNullElseGet(tags, ArrayList::new);
    }

    public TouristAttraction() {}

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

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public List<Tag> getTags() {
        return tags;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}


