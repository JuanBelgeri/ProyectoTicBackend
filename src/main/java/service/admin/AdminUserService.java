package service.admin;

import model.users.User;
import model.enums.UserRole;
import repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get users by role
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }

    // Get active users
    public List<User> getActiveUsers() {
        return userRepository.findAll().stream()
                .filter(User::getActive)
                .collect(Collectors.toList());
    }

    // Get inactive users
    public List<User> getInactiveUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getActive())
                .collect(Collectors.toList());
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update user status (activate/deactivate)
    public User updateUserStatus(String email, Boolean active) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setActive(active);
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(email);
    }

    // Get user count by role
    public long getUserCountByRole(UserRole role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .count();
    }

    // Get total user count
    public long getTotalUserCount() {
        return userRepository.count();
    }

    // Get active user count
    public long getActiveUserCount() {
        return userRepository.findAll().stream()
                .filter(User::getActive)
                .count();
    }
}