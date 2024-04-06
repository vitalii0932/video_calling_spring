package com.example.video_call.user.services;

import com.example.video_call.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserService {
    private static final List<User> USERS_LIST = new ArrayList<>();

    public void register(User user) {
        if(USERS_LIST.stream().anyMatch(someUser -> someUser.getEmail().equals(user.getEmail()))) {
            throw new RuntimeException("The user with this email is registered in the system");
        }
        user.setStatus("online");
        USERS_LIST.add(user);
    }

    public User login(User user) {
        var userId = IntStream.range(0, USERS_LIST.size())
                .filter(i -> USERS_LIST.get(i).getEmail().equals(user.getEmail()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
        var connectedUser = USERS_LIST.get(userId);
        if (!connectedUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }
        user.setStatus("online");
        return connectedUser;
    }

    public void logout(String email) {
        var userId = IntStream.range(0, USERS_LIST.size())
                .filter(i -> USERS_LIST.get(i).getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
        USERS_LIST.remove(USERS_LIST.get(userId));
    }

    public List<User> findAll() {
        return USERS_LIST;
    }
}
