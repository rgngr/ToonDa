package com.example.toonda.rest.block.repository;

import com.example.toonda.rest.block.entity.Block;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {

    // 차단 여부 확인
    boolean existsByUserAndBlockedUser(User user, User blockedUser);

    // 차단된 유저 찾기
    Optional<Block> findByUserAndBlockedUser(User user, User blockedUser);
}