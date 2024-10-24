package grp1.touristguide.model;

import java.util.Objects;

public class Tag {

    // Attributes
    private int id;         // Unique identifier for the tag (optional, depends on your DB schema)
    private String name;    // Name of the tag

    // Constructor
    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }
    public Tag(){}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Override equals and hashCode for proper comparison (important for lists/sets)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Override toString (optional, for easy debugging/printing)
    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
