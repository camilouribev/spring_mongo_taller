package com.sofkau.library.repositories;

import com.sofkau.library.entities.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceRepository extends MongoRepository<Resource, String> {
}