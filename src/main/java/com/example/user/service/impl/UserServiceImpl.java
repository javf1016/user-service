package com.example.user.service.impl;

import com.example.user.model.PageResponse;
import com.example.user.model.StandardResponse;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 *
 *  CLASS USER SERVICE
 * ****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public StandardResponse<User> register(User user, HttpServletRequest request) {
        User userExist = userRepository.findByEmail(user.getEmail());
        if (userExist != null) {
            return new StandardResponse<>(HttpStatus.BAD_REQUEST.value(), "Email " + user.getEmail() + " exist.",
                    request.getRequestURI(), null, null, null);
        }
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Timestamp timestamp = Timestamp.from(instant);
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);
        User savedUser = userRepository.save(user);
        return new StandardResponse<>(HttpStatus.OK.value(), "\n" + "Successful registration",
                request.getRequestURI(), null, savedUser, null);
    }

    @Override
    public StandardResponse<PageResponse<User>> findAllUsers(int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        PageResponse<User> pageResponse = new PageResponse<>(usersPage.getContent(), usersPage.getNumber(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getTotalPages());
        return new StandardResponse<>(HttpStatus.OK.value(), "User List", request.getRequestURI(),
                null, pageResponse, null);
    }

    @Override
    public StandardResponse<User> findUserByMail(String email, HttpServletRequest request) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "The user with email " + email + " does not exist", request.getRequestURI(), null, null, null);
        }
        return new StandardResponse<>(HttpStatus.OK.value(), "User Found", request.getRequestURI(), null, user, null);
    }

    @Override
    public StandardResponse<User> findUserById(Integer id, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new StandardResponse<>(HttpStatus.OK.value(), "User Found", null, null, user, null);
        } else {
            return new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "\n" +
                    "The user does not exist with the id: " + id, request.getRequestURI(), null, null, null);
        }
    }
}
