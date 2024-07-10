package com.wolf.workflow.user.adapter;

import com.wolf.workflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdapter {
    private final UserRepository userRepository;
}
