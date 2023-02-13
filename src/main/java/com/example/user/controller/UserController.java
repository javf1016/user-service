package com.example.user.controller;

import com.example.user.model.PageResponse;
import com.example.user.model.StandardResponse;
import com.example.user.model.User;
import com.example.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 *
 * USER CONTROLLER
 * */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

    /**
     *
     * Register a user
     * **/
	@PostMapping("/register")
	public StandardResponse<User> register(@RequestBody User user, HttpServletRequest request){
		return userService.register(user, request);
	}

    /**
     *
     * Get all users (paginated)
     * **/
	@GetMapping("/list")
	public StandardResponse<PageResponse<User>> findAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
															 @RequestParam(name = "size", defaultValue = "10") int size,
															 HttpServletRequest request) {
		return userService.findAllUsers(page, size, request);
	}

	/**
	 * Find a user by email
	 */
	@GetMapping("/findemail/{email}")
	public StandardResponse<User> findUserByEmail(@PathVariable String email, HttpServletRequest request) {
		if (!isValidEmail(email)) {
			return new StandardResponse<>(HttpStatus.BAD_REQUEST.value(), "El email " + email + " esta mal escrito", null, null, null, null);
		}
		return userService.findUserByMail(email, request);
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}
}
