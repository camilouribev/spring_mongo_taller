package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.entities.Resource;
import com.sofkau.library.repositories.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LibraryServicesTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Test
    @DisplayName("Test getAll success")
    public void getAllResources() {
        var testRes1 = new Resource();
        testRes1.setId("10");
        testRes1.setName("Tokyo Blues");
        testRes1.setGenre("Drama");
        testRes1.setType("Book");
        testRes1.setBorrowedCopies(1);
        testRes1.setTotalCopies(3);

        var testRes2 = new Resource();
        testRes2.setId("11");
        testRes2.setName("New Yorker vol. 233");
        testRes2.setGenre("News");
        testRes2.setType("Magazine");
        testRes2.setBorrowedCopies(0);
        testRes2.setTotalCopies(1);


        var resList = new ArrayList<Resource>();
        resList.add(testRes1);
        resList.add(testRes2);

        Mockito.when(resourceRepository.findAll()).thenReturn(resList);

        var resultSet = resourceService.getAllResources();
        var resultList = List.copyOf(resultSet);


        Assertions.assertEquals(2, resultList.size());

    }


    @Test
    @DisplayName("Test save Success")
    public void create() {

        var testResDTO = new ResourceDTO();
        testResDTO.setId("13");
        testResDTO.setName("The sound and the Fury");
        testResDTO.setGenre("Modernist");
        testResDTO.setType("Book");
        testResDTO.setBorrowedCopies(1);
        testResDTO.setTotalCopies(2);

        var testRes = new Resource();
        testRes.setId("13");
        testRes.setName("The sound and the Fury");
        testRes.setGenre("Modernist");
        testRes.setType("Book");
        testRes.setBorrowedCopies(1);
        testRes.setTotalCopies(2);


        Mockito.when(resourceRepository.save(Mockito.any())).thenReturn(testRes);

        var result = resourceService.createResource(testResDTO);

        Assertions.assertNotNull(result, "Saved resource can't be null");

        Assertions.assertEquals(testRes.getName(), result.getName(), "Names don't match");
        Assertions.assertEquals(testRes.getType(), result.getType(), "Resource types don't match");
    }

    @Test
    @DisplayName("Test search by ID")
    public void searchById(){

        var testRes = new Resource();
        testRes.setId("15");
        testRes.setName("European art history vol.33");
        testRes.setGenre("Art");
        testRes.setType("Magazine");
        testRes.setBorrowedCopies(0);
        testRes.setTotalCopies(1);

        var testResDTO = new ResourceDTO();
        testResDTO.setId("15");
        testResDTO.setName("European art history vol.33");
        testResDTO.setGenre("Art");
        testResDTO.setType("Magazine");
        testResDTO.setBorrowedCopies(0);
        testResDTO.setTotalCopies(1);


        Mockito.when(resourceRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(testRes));

        var result = resourceService.getResourceById(testResDTO.getId());

        Assertions.assertEquals(testRes.getId(), result.getId(), "Ids must match");
        Assertions.assertEquals("European art history vol.33", result.getName(), "Names must match");
        Assertions.assertEquals(1, result.getTotalCopies(), "Available copies must match");
        Assertions.assertNull( result.getLoanDate(), "Date should be null");
        Assertions.assertEquals(0, result.getBorrowedCopies(), "Borrowed amount must be 0");

    }

    @Test
    @DisplayName("Test update")
    void update(){

        var testResDTO = new ResourceDTO();
        testResDTO.setId("16");
        testResDTO.setName("The Little Prince");
        testResDTO.setGenre("Adventure");
        testResDTO.setType("Book");
        testResDTO.setBorrowedCopies(2);
        testResDTO.setTotalCopies(5);

        var testRes = new Resource();
        testRes.setId("16");
        testRes.setName("The Little Prince");
        testRes.setGenre("Adventure");
        testRes.setType("Book");
        testRes.setBorrowedCopies(2);
        testRes.setTotalCopies(5);


        Mockito.when(resourceRepository.save(Mockito.any())).thenReturn(testRes);
        Mockito.when(resourceRepository.findById(testResDTO.getId())).thenReturn(java.util.Optional.of(testRes));

        var result = resourceService.updateResource(testResDTO.getId(), testResDTO);

        Assertions.assertNotNull(result, "Stored data can't be null");

        Assertions.assertEquals("16", result.getId(), "Ids must match");
        Assertions.assertEquals("The Little Prince", result.getName(), "Names must match");
        Assertions.assertEquals(5, result.getTotalCopies(), "Amount of copies must match");
        Assertions.assertEquals(2, result.getBorrowedCopies(), "Borrowed copies must match");


    }




}
