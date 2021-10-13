package com.sofkau.library.controllers;


import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ResourceController {
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(value= "api/new")
    public ResourceDTO addNewResource(@RequestBody ResourceDTO resDTO){
        return resourceService.createResource(resDTO);
    }

    @GetMapping(value = "/api/resources")
    public Set<ResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }
}
