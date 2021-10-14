package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;

import java.util.Set;

public interface ResourceServiceInterface {
    public ResourceDTO createResource(ResourceDTO resource);
    public Set<ResourceDTO> getAllResources();
    public ResourceDTO getResourceById(String id);
    public ResourceDTO updateResource(String id, ResourceDTO resDTO);
    public void deleteResource(String id);

}
