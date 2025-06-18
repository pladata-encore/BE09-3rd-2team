package com.gp.nut.usermanagement.command.service;

import com.gp.nut.usermanagement.command.dto.UserCreateRequest;
import com.gp.nut.usermanagement.command.dto.UserRoleUpdateRequest;
import com.gp.nut.usermanagement.command.entity.User;
import com.gp.nut.usermanagement.command.entity.UserRole;
import com.gp.nut.usermanagement.command.service.repository.UserRepository;
import com.gp.nut.usermanagement.common.ApiResponse;
import com.gp.nut.usermanagement.common.Errorcode;
import com.gp.nut.usermanagement.common.UserException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserCreateRequest request) {
        // ID 중복 체크 확인
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(Errorcode.DUPLICATE_USERNAME);
        }
        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(Errorcode.USER_NOT_FOUND));
    }

    @Transactional
    public void updateUser(Long userId, UserCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(Errorcode.USER_NOT_FOUND));
        // ID 중복 체크 확인
        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(Errorcode.DUPLICATE_USERNAME);
        }
        user.updateUser(passwordEncoder.encode(request.getPassword()), request.getName());
        userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(Errorcode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUserRole(Long userId, UserRoleUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(Errorcode.USER_NOT_FOUND));
        user.updateRole(request.getRole());
    }

    @Transactional
    public void registerAdmin(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(Errorcode.DUPLICATE_USERNAME);
        }
        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        user.updateRole(UserRole.ADMIN);
        userRepository.save(user);
    }


}