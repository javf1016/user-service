package com.example.user.service;

import com.example.user.model.PageResponse;
import com.example.user.model.StandardResponse;
import com.example.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     *
     * Register a user
     *
     * **/
    StandardResponse<User> register(User user, HttpServletRequest request);

    /**
     *
     * Get all users (paginated)
     *
     * **/
    StandardResponse<PageResponse<User>>  findAllUsers(int page, int size, HttpServletRequest request);

    /**
     * Find a user by email
     */
    StandardResponse findUserByMail(String email, HttpServletRequest request);

    /**
     * Find a user by UserId
     */
    StandardResponse<User> getUser(Integer Id, HttpServletRequest request);

}
