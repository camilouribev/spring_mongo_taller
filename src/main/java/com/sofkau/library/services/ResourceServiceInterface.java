package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;

import java.util.Set;

public interface ResourceServiceInterface {
    public ResourceDTO createResource(ResourceDTO resource);
    public Set<ResourceDTO> getAllResources();

}
