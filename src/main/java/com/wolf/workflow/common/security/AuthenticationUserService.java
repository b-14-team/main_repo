package com.wolf.workflow.common.security;

import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j(topic = "유저검증")
@Service
@RequiredArgsConstructor
public class AuthenticationUserService implements UserDetailsService {

    private final UserAdapter userAdapter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userAdapter.getUserByEmail(email);
        return AuthenticationUser.of(user);
    }
}