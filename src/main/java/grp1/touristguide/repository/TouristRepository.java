package grp1.touristguide.repository;

import grp1.touristguide.model.Tag;
import grp1.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TouristRepository {

    // ArrayList to store TouristAttraction objects
    private final List<TouristAttraction> attractions = new ArrayList<>();

    // Constructor to initialize with some sample data
    public TouristRepository() {
        // Adding a couple of sample TouristAttraction objects to the list
        attractions.add(new TouristAttraction(
                "Tivoli",
                "Amusement park in the middle of Copenhagen city center.",
                "Copenhagen",
                Arrays.asList(Tag.FAMILY_FRIENDLY, Tag.HISTORIC, Tag.CULTURAL, Tag.NATURE, Tag.ADVENTURE, Tag.RELAXING)
        ));

        // Adding The Little Mermaid with appropriate tags
        attractions.add(new TouristAttraction(
                "The Little Mermaid",
                "Iconic bronze statue based on the fairy tale by Hans Christian Andersen, located by the waterfront in Copenhagen.",
                "Copenhagen",
                Arrays.asList(Tag.HISTORIC, Tag.CULTURAL, Tag.NATURE, Tag.RELAXING)
        ));
    }

    // Read: Get all TouristAttractions
    public List<TouristAttraction> findAll() {
        return new ArrayList<>(attractions);
    }

    // Read: Get a TouristAttraction by name
    public TouristAttraction findByName(String name) {
        return attractions.stream().filter(attraction -> attraction.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    // Method to save a new attraction
    public void save(TouristAttraction attraction) {
        attractions.add(attraction);
    }

    // Method to update an existing attraction
    public void update(TouristAttraction updatedAttraction) {
        TouristAttraction existingAttraction = findByName(updatedAttraction.getName());
        if (existingAttraction != null) {
            existingAttraction.setDescription(updatedAttraction.getDescription());
            existingAttraction.setCity(updatedAttraction.getCity());
            existingAttraction.setTags(updatedAttraction.getTags());
        }
    }

    // Method to delete an existing attraction by name
    public void deleteByName(String name) {
        TouristAttraction attraction = findByName(name);
        if (attraction != null) {
            attractions.remove(attraction);
        }
    }
}

