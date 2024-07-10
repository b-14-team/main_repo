package com.wolf.workflow.user.repository;

import com.wolf.workflow.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
