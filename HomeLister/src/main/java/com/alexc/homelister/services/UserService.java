package com.alexc.homelister.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.alexc.homelister.models.LoginUser;
import com.alexc.homelister.models.User;
import com.alexc.homelister.repositories.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	public User findUser(Long id) {
		return userRepo.findById(id).get();
	}

	public User register(User newUser, BindingResult result) {
		String passwordEntered = newUser.getPassword();
		if (result.hasErrors()) {
			// Exit the method and go back to the controller
			// to handle the response
			return null;
		}
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());

		if (!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
		}
		if (!potentialUser.isPresent()) {
			String hashed = BCrypt.hashpw(passwordEntered, BCrypt.gensalt());
			newUser.setPassword(hashed);
			return userRepo.save(newUser);

		} else {
			return null;
		}

	}

	public User login(LoginUser newLoginObject, BindingResult result) {
		Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
		if (potentialUser.isEmpty()) {
			return null;
		}
		User user = potentialUser.get();

		if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
			result.rejectValue("password", "Matches", "Invalid Password!");
			return null;
		} else {
			return user;
		}

	}
}
