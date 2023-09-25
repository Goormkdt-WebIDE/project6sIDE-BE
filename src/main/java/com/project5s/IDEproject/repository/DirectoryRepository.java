package com.project5s.IDEproject.repository;

import com.project5s.IDEproject.domain.Directory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends MongoRepository<Directory, String> {
}
