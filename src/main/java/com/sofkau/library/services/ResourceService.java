package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.entities.Resource;
import com.sofkau.library.mappers.ResourceMapper;
import com.sofkau.library.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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

    @Override
    public ResourceDTO getResourceById(String id) {
        try {
            return  mapper.convertToDTO(resourceRepository.findById(id).get()) ;
        } catch (Exception e) {
            throw  new NoSuchElementException("There is no resource with that Id");
        }
    }

    @Override
    public ResourceDTO updateResource(String id, ResourceDTO resDTO) {
        Resource updatedResource = mapper.convertToEntity(resDTO);
        getResourceById(updatedResource.getId());
        return mapper.convertToDTO(resourceRepository.save(updatedResource));
    }

    @Override
    public void deleteResource(String id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
        resourceRepository.delete(resource);
    }


}
