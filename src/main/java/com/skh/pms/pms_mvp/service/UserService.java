package com.skh.pms.pms_mvp.service;

import com.skh.pms.pms_mvp.dto.UserCreateRequest;
import com.skh.pms.pms_mvp.dto.UserResponse;
import com.skh.pms.pms_mvp.entity.User;

import java.util.Optional;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
