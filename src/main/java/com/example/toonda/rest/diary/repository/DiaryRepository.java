package com.example.toonda.rest.diary.repository;

import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByFolder(Folder folder);

    List<Diary> findByFolderAndDeletedIsFalse(Folder folder);
}