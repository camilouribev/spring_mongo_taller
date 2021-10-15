package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.entities.Resource;
import com.sofkau.library.mappers.ResourceMapper;
import com.sofkau.library.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
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
    public  ResourceDTO createResource(ResourceDTO resource) {
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
            return mapper.convertToDTO(resourceRepository.findById(id).get());
        } catch (Exception e) {
            throw new NoSuchElementException("There is no resource with that Id");
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
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        resourceRepository.delete(resource);
    }

    @Override
    public String checkIfResourceAvailable(String id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        int availableResources = resource.getTotalCopies() - resource.getBorrowedCopies();
        if (availableResources != 0) {

            return "The " + resource.getType() + " " + resource.getName() + " is available. There are "
                    + availableResources + " copies remaining.";
        }

        return "All copies of the " + resource.getType() + " " + resource.getName() + " are borrowed." + " The last copy was borrowed on " + resource.getLoanDate();
    }

    @Override
    public String borrowResource(String id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        ResourceDTO resDTO = mapper.convertToDTO(resource);
        int availableResources = resDTO.getTotalCopies() - resDTO.getBorrowedCopies();
        if (availableResources != 0) {
            resDTO.setLoanDate(LocalDate.now());
            resDTO.setBorrowedCopies(resDTO.getBorrowedCopies() + 1);
            updateResource(id, resDTO);
            return "You borrowed a copy of " + resDTO.getType() + " " + resDTO.getName() + " on " + resDTO.getLoanDate();
        }

        return "All copies of the " + resDTO.getType() + " " + resDTO.getName() + " are borrowed.";

    }

    @Override
    public String returnResource(String id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        ResourceDTO resDTO = mapper.convertToDTO(resource);

        if (resDTO.getBorrowedCopies() != 0) {
            resDTO.setLoanDate(LocalDate.now());
            resDTO.setBorrowedCopies(resDTO.getBorrowedCopies() - 1);
            updateResource(id, resDTO);
            return "You returned a copy of the " + resDTO.getType() + " " + resDTO.getName();
        }

        return "No copies of the " + resDTO.getType() + " " + resDTO.getName() + " are borrowed. Nothing to return";

    }

    @Override
    public Set<ResourceDTO> recommendResourceByType(String type) {
        return resourceRepository.findByType(type).stream().map(res -> mapper.convertToDTO(res))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ResourceDTO> recommendResourceByGenre(String genre) {

        return resourceRepository.findByGenre(genre).stream().map(res -> mapper.convertToDTO(res))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ResourceDTO> recommendResourceSpecific(String type, String genre) {
        Set<ResourceDTO> recommendedByGenre = recommendResourceByGenre(genre);
        Set<ResourceDTO> recommendedByType = recommendResourceByType(type);
        Set<ResourceDTO> mergedDTO = new HashSet<>();

        recommendedByGenre.stream().forEach(firstDto -> {
            recommendedByType.stream().forEach(dto -> {
                if (firstDto.getId().equals(dto.getId())) {
                    mergedDTO.add(dto);
                }
            });
        });
        return mergedDTO;
    }
}
