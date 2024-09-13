package grp1.touristguide.service;

import grp1.touristguide.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import grp1.touristguide.repository.TouristRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    // Constructor injection of TouristRepository
    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    // Create: Add a new TouristAttraction
    public void addAttraction(TouristAttraction attraction) {
        touristRepository.addAttraction(attraction);
    }

    // Read: Get all TouristAttractions
    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAllAttractions();
    }

    // Read: Get a TouristAttraction by name
    public Optional<TouristAttraction> getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    // Update: Update an existing TouristAttraction by name
    public boolean updateAttraction(String name, TouristAttraction updatedAttraction) {
        return touristRepository.updateAttraction(name, updatedAttraction);
    }

    // Delete: Remove a TouristAttraction by name
    public boolean deleteAttraction(String name) {
        return touristRepository.deleteAttraction(name);
    }
}
