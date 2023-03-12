package com.example.toonda.rest.folder.repository;

import com.example.toonda.rest.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByIdAndDeletedFalse(Long id);

    @Query("select f from Folder f where f.id= :id and f.deleted= false  and f.open=true")
    Optional<Folder> getAliveFolder(@Param("id") Long id);

}