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

     // CRUD actions
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
  // Business actions
    @GetMapping(value = "/api/resources/{resId}/isavailable")
    public String checkIsResourceAvaliable(@PathVariable("resId") String id){
        return resourceService.checkIfResourceAvailable(id);
    }

    @PutMapping(value = "/api/resources/{resId}/borrow")
    public String borrowResource(@PathVariable("resId") String id){
        return resourceService.borrowResource(id);
    }
    @PutMapping(value = "/api/resources/{resId}/return")
    public String returnResource(@PathVariable("resId") String id){
        return resourceService.returnResource(id);
    }
    @GetMapping(value = "/api/resources/recommendbygenre/{genre}")
    public Set<ResourceDTO> recommendResourceByGenre(@PathVariable("genre") String genre){
        return resourceService.recommendResourceByGenre(genre);
    }

    @GetMapping(value = "/api/resources/recommendbytype/{type}")
    public Set<ResourceDTO> recommendResourceByType(@PathVariable("type") String type){
        return resourceService.recommendResourceByType(type);
    }

    @GetMapping(value = "/api/resources/recommendspecific/{type}/{genre}")
    public Set<ResourceDTO> recommendResourceByType(@PathVariable("type") String type,@PathVariable("genre") String genre){
        return resourceService.recommendResourceSpecific(type,genre);
    }






}
