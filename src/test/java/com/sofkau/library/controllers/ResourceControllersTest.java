package com.sofkau.library.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.services.ResourceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllersTest {
    @MockBean
    private ResourceService resourceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/resources ")
    public void findAll() throws Exception {
        //setup mock service
        var testResDTO1 = new ResourceDTO();
        testResDTO1.setId("17");
        testResDTO1.setName("The Aleph");
        testResDTO1.setGenre("Short Stories");
        testResDTO1.setType("Book");
        testResDTO1.setBorrowedCopies(0);
        testResDTO1.setTotalCopies(2);


        var resList = new HashSet<ResourceDTO>();
        resList.add(testResDTO1);

        Mockito.when(resourceService.getAllResources()).thenReturn(resList);

        //execute Get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("17")))
                .andExpect(jsonPath("$[0].name", is("The Aleph")))
                .andExpect(jsonPath("$[0].type", is("Book")));

    }

    @Test
    @DisplayName("POST /api/new")
    public void create() throws Exception {
        //setup mock service
        var testPostDTO = new ResourceDTO();
        testPostDTO.setId("18");
        testPostDTO.setName("Republic");
        testPostDTO.setType("Book");
        testPostDTO.setGenre("Philosophy");
        testPostDTO.setBorrowedCopies(1);
        testPostDTO.setTotalCopies(3);


        var testReturnDTO = new ResourceDTO();
        testReturnDTO.setId("18");
        testReturnDTO.setName("Republic");
        testReturnDTO.setType("Book");
        testReturnDTO.setGenre("Philosophy");
        testReturnDTO.setBorrowedCopies(1);
        testReturnDTO.setTotalCopies(3);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonPostDTO = ow.writeValueAsString(testPostDTO);

        Mockito.when(resourceService.createResource(Mockito.any())).thenReturn(testReturnDTO);

        //execute POST  request
        mockMvc.perform(post("/api/new")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPostDTO))


                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is("18")))
                .andExpect(jsonPath("$.name", is("Republic")))
                .andExpect(jsonPath("$.genre", is("Philosophy")));


    }




}
