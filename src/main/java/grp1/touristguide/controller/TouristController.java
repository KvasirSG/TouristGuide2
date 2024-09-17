package grp1.touristguide.controller;

import grp1.touristguide.model.Tag;
import grp1.touristguide.model.TouristAttraction;
import grp1.touristguide.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    @Autowired
    private TouristService attractionService;

    // GET endpoint to show the list of all attractions
    @GetMapping
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = attractionService.findAll();
        model.addAttribute("attractions", attractions);
        return "attractionList"; // The name of the Thymeleaf template to render
    }

    // GET endpoint to edit a specific tourist attraction
    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = attractionService.findByName(name);
        model.addAttribute("attraction", attraction);
        return "editAttraction"; // HTML page for editing the attraction
    }

    // POST endpoint to update an existing tourist attraction
    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        attractionService.updateAttraction(attraction);
        return "redirect:/attractions"; // Redirect back to the list after saving changes
    }

    // GET endpoint to display tags for a specific attraction
    @GetMapping("/{name}/tags")
    public String viewTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = attractionService.findByName(name);
        model.addAttribute("attraction", attraction);
        return "tags"; // HTML page to display the tags of the attraction
    }

    // GET endpoint to add a new tourist attraction
    @GetMapping("/add")
    public String addAttractionForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction()); // Provide a new, empty model for the form
        model.addAttribute("tags", Arrays.asList(Tag.values())); // Add tags to the model
        return "addAttraction"; // HTML form page for adding a new attraction
    }

    // POST endpoint to save a new tourist attraction
    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        attractionService.saveAttraction(attraction);
        return "redirect:/attractions"; // Redirect to the list after saving the new attraction
    }

    // POST endpoint to delete a tourist attraction
    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        attractionService.deleteAttraction(name);
        return "redirect:/attractions"; // Redirect to the list after deletion
    }
}

