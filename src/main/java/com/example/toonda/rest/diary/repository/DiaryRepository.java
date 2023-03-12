package com.example.toonda.rest.diary.repository;

import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByFolder(Folder folder);

    List<Diary> findByFolderAndDeletedFalse(Folder folder);

    Optional<Diary> findByIdAndDeletedFalse(Long diaryId);

    boolean existsByIdAndDeletedFalse(Long id);
}