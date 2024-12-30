package gabriel.investmentportfolio.service;

import gabriel.investmentportfolio.model.User;
import gabriel.investmentportfolio.repository.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        // Check if username already exists
        if (userRepository.getUserByUserName(user.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Generate a unique userId
        user.setUserId(UUID.randomUUID());
        // Save user
        userRepository.saveUser(user);
    }


    public boolean login(User user) {
        // Retrieve the user from the repository by username
        User storedUser = userRepository.getUserByUserName(user.getUserName());
        
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            // Save session
            userRepository.saveUserSession(storedUser);
            return true;
        }
        return false;
    }

    // Check if the user is logged in
    public boolean isUserLoggedIn(UUID userId) {
        return userRepository.hasUserSession(userId); // Check if the session exists
    }
     
    public void logout(String userName) {
        User user = userRepository.getUserByUserName(userName);
        if (user != null) {
            userRepository.removeUserSession(user.getUserId()); // Remove the user session
        }
    }
    // Get logged-in user details
    public User getLoggedInUser(UUID userId) {
        if (isUserLoggedIn(userId)) {
            return userRepository.getUserById(userId); // Return the user from the repository if logged in
        }
        return null;
    }
}
