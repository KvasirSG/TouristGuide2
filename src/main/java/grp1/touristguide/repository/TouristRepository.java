package grp1.touristguide.repository;

import grp1.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TouristRepository {

    // ArrayList to store TouristAttraction objects
    private final List<TouristAttraction> attractions = new ArrayList<>();

    // Constructor to initialize with some sample data
    public TouristRepository() {
        // Adding a couple of sample TouristAttraction objects to the list
        attractions.add(new TouristAttraction("Tivoli","Amusement park in the middle of Copenhagen city center."));
        attractions.add(new TouristAttraction("The Little Mermaid", "Iconic bronze statue based on the fairy tale by Hans Christian Andersen, located by the waterfront in Copenhagen."));
    }

    // Create: Add a new TouristAttraction
    public void addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
    }

    // Read: Get all TouristAttractions
    public List<TouristAttraction> getAllAttractions() {
        return new ArrayList<>(attractions);
    }

    // Read: Get a TouristAttraction by name
    public Optional<TouristAttraction> getAttractionByName(String name) {
        return attractions.stream().filter(attraction -> attraction.getName().equalsIgnoreCase(name)).findFirst();
    }

    // Update: Update an existing TouristAttraction by name
    public boolean updateAttraction(String name, TouristAttraction updatedAttraction) {
        Optional<TouristAttraction> existingAttraction = getAttractionByName(name);
        if (existingAttraction.isPresent()) {
            int index = attractions.indexOf(existingAttraction.get());
            attractions.set(index, updatedAttraction);
            return true;
        }
        return false;
    }

    // Delete: Remove a TouristAttraction by name
    public boolean deleteAttraction(String name) {
        return attractions.removeIf(attraction -> attraction.getName().equalsIgnoreCase(name));
    }
}

