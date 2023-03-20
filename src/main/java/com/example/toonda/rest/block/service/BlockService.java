package com.example.toonda.rest.block.service;

import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.dto.BlockListResponseDto;
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

    // 차단/취소
    @Transactional
    public ResponseDto block(Long userId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 상대방 존재 확인 (delete = false)
        User blockedUser = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() -> new RestApiException(Code.NO_USER));
        // 차단 여부 확인 >> 차단/취소
        Block blockExistence = blockRepository.findByUserAndBlockedUser(user, blockedUser).orElse(null);
        boolean blockOrNot;
        if (blockExistence == null) {
            blockExistence = blockRepository.save(new Block(user, blockedUser));
            blockOrNot= true;
        } else {
            blockRepository.delete(blockExistence);
            blockOrNot= false;
        }
        return ResponseDto.of(blockOrNot, Code.BLOCK_OR_CANCEL);
    }

    // 차단 리스트
    @Transactional(readOnly = true)
    public BlockListResponseDto blockList() {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 차단 리스트 생성 (deleted = false, order by blockId)
        BlockListResponseDto blockListResponseDto = new BlockListResponseDto();
        List<User> blockedUsers = userRepository.findAllBlockedUsers(user);
        for (User blockedUser : blockedUsers) {
            blockListResponseDto.addBlockedUser(new BlockListResponseDto.BlockResponseDto(blockedUser));
        }
        return blockListResponseDto;
    }

}
