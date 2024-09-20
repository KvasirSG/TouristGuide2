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

import javax.management.MXBean;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TouristController.class)
class TouristControllerTest {
    private TouristAttraction touristAttraction = new TouristAttraction();

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
        testAttraction.setTags(Arrays.asList(Tag.CULTURAL, Tag.HISTORIC));
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

        mockMvc.perform(get("/attractions/{name}/edit", "Test Attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", Arrays.asList(Tag.values())));
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
                .andExpect(model().attribute("tags", Arrays.asList(Tag.CULTURAL, Tag.HISTORIC)));
    }

    @Test
    void addAttractionForm() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", Arrays.asList(Tag.values())));
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
        mockMvc.perform(post("/attractions/delete/{name}", "Test Attraction"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        Mockito.verify(touristService).deleteAttraction("Test Attraction");
    }

}