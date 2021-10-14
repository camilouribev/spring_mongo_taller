package com.sofkau.library.controllers;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ResourceController {
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(value = "/api/new")
    public ResourceDTO addNewResource(@RequestBody ResourceDTO resDTO) {
        return resourceService.createResource(resDTO);
    }

    @GetMapping(value = "/api/resources")
    public Set<ResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }

    @GetMapping(value = "/api/resources/{resId}")
    public ResourceDTO getResourceById(@PathVariable("resId") String id) {
        return resourceService.getResourceById(id);
    }

    @PutMapping(value = "/api/resources/{resId}")
    public ResourceDTO updateResource(@PathVariable("resId") String resId, @RequestBody ResourceDTO resDTO) {
        if (resDTO.getId() != null) {
            return resourceService.updateResource(resId, resDTO);
        }
        throw new RuntimeException();
    }

    @DeleteMapping(value = "/api/resources/{resId}")
    public void deleteResource(@PathVariable("resId") String id) {
        resourceService.deleteResource(id);
    }
}
