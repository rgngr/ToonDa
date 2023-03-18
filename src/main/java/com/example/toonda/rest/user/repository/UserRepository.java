package com.example.toonda.rest.user.repository;

import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoIdNullAndEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndDeletedFalse(Long userId);

    @Query("select u from Users u join Block b on b.blockedUser = u where b.user = :user and u.deleted = false order by b.id")
    List<User> findAllBlockedUsers(@Param("user") User user);
}
