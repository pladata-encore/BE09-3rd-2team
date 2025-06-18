package com.gp.nut.usermanagement.auth.service;

import com.gp.nut.usermanagement.auth.model.CustomUser;
import com.gp.nut.usermanagement.command.entity.User;
import com.gp.nut.usermanagement.command.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 찾지 못함"));

        /* 수정 사항 : spring security의 UserDetails를 extends 하여 CustomUser 작성
        * 고유 id 숫자, 로그인 id(username), password, authorities 를 저장하고 반환
        * */
        return CustomUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())))
                .build();
    }
}
