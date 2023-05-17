package pl.marcinlipinski.matchbettingapp.service;

import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.model.User;
import pl.marcinlipinski.matchbettingapp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final long userId = 1;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        return userRepository.findById(userId).get();
    }

    public double getAccountValue() {
        return getUser().getAccountBalance();
    }

    public void newUser() {
        var user = getUser();
        user.setAccountBalance(20.00);
        userRepository.deleteAll();
        userRepository.save(user);
    }
}
