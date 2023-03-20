package com.example.toonda.rest.folder.repository;

import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByIdAndDeletedFalse(Long id);

    @Query("select f from Folder f where f.id= :id and f.deleted= false  and f.open=true")
    Optional<Folder> getAliveFolder(@Param("id") Long id);

    @Query("select f from Folder f where f.user = :user and f.deleted= false order by f.modifiedAt desc ")
    List<Folder> findAllUsersFolders(@Param("user")User user);

    @Query("select f from Folder f where f.user = :oppUser and f.deleted= false and f.open=true order by f.modifiedAt desc")
    List<Folder> findAllForMypageFolderList(@Param("oppUser")User oppUser);

}