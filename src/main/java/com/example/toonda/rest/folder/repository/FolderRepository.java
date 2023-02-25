package com.example.toonda.rest.folder.repository;

import com.example.toonda.rest.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}