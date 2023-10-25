package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserDto dto) {
        emailDuplicateValidation(dto);

        String userKey = UUID.randomUUID().toString();
        String encodedPwd = passwordEncoder.encode(dto.getPwd());

        User savedUser = insertUser(dto, encodedPwd, userKey);

        return UserResponse.of(savedUser);
    }

    public UserResponse editPwd(String email, String currentPwd, String newPwd) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("이메일을 확인해주세요.");
        }
        User user = findUser.get();

        boolean isMatch = passwordEncoder.matches(currentPwd, user.getPwd());
        if (!isMatch) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedPwd = passwordEncoder.encode(newPwd);
        user.editPwd(encodedPwd);

        return UserResponse.of(user);
    }

    public WithdrawalResponse withdrawal(String email, String pwd) {
        return null;
    }

    private User insertUser(CreateUserDto dto, String encodedPwd, String userKey) {
        User user = User.builder()
            .email(dto.getEmail())
            .pwd(encodedPwd)
            .name(dto.getName())
            .birth(dto.getBirth())
            .userKey(userKey)
            .userCodeId(dto.getUserCodeId())
            .build();

        return userRepository.save(user);
    }

    private void emailDuplicateValidation(CreateUserDto dto) {
        boolean isExistEmail = userQueryRepository.existEmail(dto.getEmail());
        if (isExistEmail) {
            throw new DuplicateException("이미 사용 중인 이메일 입니다.");
        }
    }
}
