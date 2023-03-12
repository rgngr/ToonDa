package com.example.toonda.rest.block.repository;

import com.example.toonda.rest.block.entity.Block;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    boolean existsByUserAndBlockedUser(User user, User blockedUser);
}