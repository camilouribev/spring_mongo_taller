package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.entities.Resource;
import com.sofkau.library.mappers.ResourceMapper;
import com.sofkau.library.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResourceService implements ResourceServiceInterface {
    private ResourceRepository resourceRepository;
    private ResourceMapper mapper;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    @Override
    public ResourceDTO createResource(ResourceDTO resource) {
        Resource newResource = mapper.convertToEntity(resource);
        return mapper.convertToDTO(resourceRepository.save(newResource));

    }

    @Override
    public Set<ResourceDTO> getAllResources() {
        return StreamSupport
                .stream(resourceRepository.findAll().spliterator(), false)
                .map(res -> mapper.convertToDTO(res)).collect(Collectors.toSet());
    }


}
