package grp1.touristguide.controller;

import grp1.touristguide.model.Tag;
import grp1.touristguide.model.TouristAttraction;
import grp1.touristguide.service.TouristService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = grp1.touristguide.controller.TouristController.class)
class TouristControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    private TouristAttraction testAttraction;

    @BeforeEach
    void setUp() {
        // Initialize the testAttraction object
        testAttraction = new TouristAttraction();
        testAttraction.setName("Test Attraction");
        testAttraction.setDescription("A beautiful place to visit");
        testAttraction.setCity("Test City");
        testAttraction.setTags(Arrays.asList(new Tag("Cultural"), new Tag("Historic")));
    }

    @Test
    void getAllAttractions() throws Exception {
        when(touristService.findAll()).thenReturn(Collections.singletonList(testAttraction));

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"))
                .andExpect(model().attributeExists("attractions"));
    }

    @Test
    void editAttraction() throws Exception {
        when(touristService.findByName(anyString())).thenReturn(testAttraction);
        when(touristService.findAllTags()).thenReturn(Arrays.asList(new Tag("Cultural"), new Tag("Historic")));

        mockMvc.perform(get("/attractions/{name}/edit", "Test Attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", Arrays.asList(new Tag("Cultural"), new Tag("Historic"))));
    }

    @Test
    void updateAttraction() throws Exception {
        mockMvc.perform(post("/attractions/update")
                        .flashAttr("attraction", testAttraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        Mockito.verify(touristService).updateAttraction(any(TouristAttraction.class));
    }

    @Test
    void viewTags() throws Exception {
        when(touristService.findByName(anyString())).thenReturn(testAttraction);

        mockMvc.perform(get("/attractions/{name}/tags", "Test Attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", Arrays.asList(new Tag("Cultural"), new Tag("Historic"))));
    }

    @Test
    void addAttractionForm() throws Exception {
        when(touristService.findAllTags()).thenReturn(Arrays.asList(new Tag("Cultural"), new Tag("Historic")));

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", Arrays.asList(new Tag("Cultural"), new Tag("Historic"))));
    }

    @Test
    void saveAttraction() throws Exception {
        mockMvc.perform(post("/attractions/save")
                        .flashAttr("attraction", testAttraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        Mockito.verify(touristService).saveAttraction(any(TouristAttraction.class));
    }

    @Test
    void deleteAttraction() throws Exception {
        mockMvc.perform(post("/attractions/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        Mockito.verify(touristService).deleteAttraction(anyInt());
    }

}
