package gabriel.investmentportfolio.repository;

import gabriel.investmentportfolio.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {

    private static final String USER_PREFIX = "user:";
    private static final String USERNAME_TO_ID_PREFIX = "usernameToId:";
    private static final String SESSION_PREFIX = "loggedInUser:";

    @Autowired
    private RedisTemplate redisTemplate;

    // Save user to Redis (used for registration)
    public void saveUser(User user) {
        // Save user data
        redisTemplate.opsForValue().set(USER_PREFIX + user.getUserId(), user);
        // Save username-to-userId mapping
        redisTemplate.opsForValue().set(USERNAME_TO_ID_PREFIX + user.getUserName(), user.getUserId());
    }

    // Retrieve user from Redis by userId
    public User getUserById(UUID userId) {
        return (User) redisTemplate.opsForValue().get(USER_PREFIX + userId);
    }

    // Retrieve user from Redis by username
    public User getUserByUserName(String userName) {
        // Get userId from username
        UUID userId = (UUID) redisTemplate.opsForValue().get(USERNAME_TO_ID_PREFIX + userName);
        if (userId != null) {
            return getUserById(userId);
        }
        return null;
    }

    // Save user session (used for login)
    public void saveUserSession(User user) {
        redisTemplate.opsForValue().set(SESSION_PREFIX + user.getUserId(), user);
    }

    // Remove user session (used for logout)
    public void removeUserSession(UUID userId) {
        redisTemplate.delete(SESSION_PREFIX + userId);
    }

    // Check if the user session exists
    public boolean hasUserSession(UUID userId) {
        return redisTemplate.hasKey(SESSION_PREFIX + userId);
    }
}
