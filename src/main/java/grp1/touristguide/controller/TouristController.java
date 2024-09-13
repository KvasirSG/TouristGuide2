package grp1.touristguide.controller;

import grp1.touristguide.model.TouristAttraction;
import grp1.touristguide.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class TouristController {
    private TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public String tourist(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "tourist";
    }

    @GetMapping("/attractions")
    public String listAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    @GetMapping("/attractions/{name}")
    public String listAttractionsByName(Model model, @PathVariable String name) {
        Optional<TouristAttraction> attraction = touristService.getAttractionByName(name);
        model.addAttribute("attraction", attraction.get());
        return "attraction";
    }

    @GetMapping("/attractions/{name}/tags")
    public String listTagsByAttractions(Model model, @PathVariable String name) {
        Optional<TouristAttraction> attraction = touristService.getAttractionByName(name);
        model.addAttribute("attraction", attraction.get());
        return "attractionTags";
    }
}
