package com.project5s.IDEproject.repository;

import com.project5s.IDEproject.domain.Code;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends MongoRepository<Code, String> {
}
