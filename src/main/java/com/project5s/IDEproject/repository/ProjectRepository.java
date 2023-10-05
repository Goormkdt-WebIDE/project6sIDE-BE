package com.project5s.IDEproject.repository;

import com.project5s.IDEproject.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findProjectByEmailAndName(String email, String name);
    Optional<Project> findProjectByEmailAndId(String email, String id);
    boolean existsProjectByEmailAndId(String email, String id);
    boolean existsProjectByEmailAndName(String email, String name);
    List<Project> findProjectsByEmail(String email);
}
