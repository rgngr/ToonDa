package com.example.toonda.rest.folder.repository;

import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<String> findAllByFolder(Folder folder);

    boolean existsByFolderAndHashtag(Folder folder, String s);

    void deleteByFolder(Folder folder);
}