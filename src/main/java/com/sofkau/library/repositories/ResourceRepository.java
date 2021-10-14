package com.sofkau.library.repositories;

import com.sofkau.library.entities.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    Set<Resource> findByType( String type);
    Set<Resource> findByGenre( String genre);

}