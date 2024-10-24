package grp1.touristguide.service;

import grp1.touristguide.model.Tag;
import grp1.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Service;
import grp1.touristguide.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    // Method to find all attractions
    public List<TouristAttraction> findAll() {
        return touristRepository.findAll();
    }

    // Method to find an attraction by name, returns null if not found
    public TouristAttraction findByName(String name) {
        return touristRepository.findByName(name);
    }

    // Method to update an existing attraction
    public void updateAttraction(TouristAttraction updatedAttraction) {
        touristRepository.update(updatedAttraction);
    }

    // Method to save a new attraction
    public void saveAttraction(TouristAttraction attraction) {
        touristRepository.save(attraction);
    }

    // Method to delete an attraction by name
    public void deleteAttraction(int id) {
        touristRepository.deleteById(id);
    }

    // Method to get all tags
    public List<Tag> findAllTags(){
        return touristRepository.findAllTags();
    }
}
