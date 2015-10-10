package com.example.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<DataEntity, Long> {
}
