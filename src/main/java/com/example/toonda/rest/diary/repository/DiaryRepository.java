package com.example.toonda.rest.diary.repository;

import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByFolder(Folder folder);

    List<Diary> findByFolderAndDeletedFalse(Folder folder);

    Optional<Diary> findByIdAndDeletedFalse(Long diaryId);

    boolean existsByIdAndDeletedFalse(Long id);

    @Query("select d from Diary d join Likes l on d.id = l.diary.id where l.user = :user order by d.date")
    List<Diary> findAllForDiaryList(@Param("user")User user);
}