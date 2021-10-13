package com.sofkau.library.mappers;

import com.sofkau.library.dtos.ResourceDTO;
import com.sofkau.library.entities.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    private ModelMapper mapper;

    public ResourceMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public ResourceDTO convertToDTO(Resource resource){
        ResourceDTO resourceDTO = mapper.map(resource, ResourceDTO.class);
        return resourceDTO;
    }
    public Resource convertToEntity(ResourceDTO resourceDTO){
        Resource resource= mapper.map(resourceDTO,Resource.class);
        return resource;
    }

}

