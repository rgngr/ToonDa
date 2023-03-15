package com.example.toonda.rest.block.service;

import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.dto.BlockListResponseDto;
import com.example.toonda.rest.block.dto.BlockResponseDto;
import com.example.toonda.rest.block.entity.Block;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.user.entity.User;
import com.example.toonda.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto block(Long userId) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 상대방 존재 확인 (delete = false)
        User blockedUser = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() -> new RestApiException(Code.NO_USER));
        // 차단 여부 확인 >> 차단/취소
        Block blockExistence = blockRepository.findByUserAndBlockedUser(user, blockedUser).orElse(null);
        boolean isBlocked;
        if (blockExistence == null) {
            blockExistence = blockRepository.save(new Block(user, blockedUser));
            isBlocked = true;
        } else {
            blockRepository.delete(blockExistence);
            isBlocked = false;
        }
        return ResponseDto.of(isBlocked, Code.BLOCK_OR_CANCEL);
    }

    // 탈퇴한 회원 처리........어떻게.......?
    @Transactional
    public BlockListResponseDto blockList() {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 차단 리스트 생성
        BlockListResponseDto blockListResponseDto = new BlockListResponseDto();
        List<User> blockedUsers = userRepository.findAllBlockedUsers(user);
        for (User blockedUser : blockedUsers) {
            blockListResponseDto.addBlockedUser(new BlockResponseDto(blockedUser));
        }
        return blockListResponseDto;
    }

}
