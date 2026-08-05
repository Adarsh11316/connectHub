package com.adarsh.ConnectHub.repository;

import com.adarsh.ConnectHub.entity.Project;
import com.adarsh.ConnectHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findByOwner(User owner);

    public Optional<Project> findByIdAndOwner(Long id, User currentUser);
}
