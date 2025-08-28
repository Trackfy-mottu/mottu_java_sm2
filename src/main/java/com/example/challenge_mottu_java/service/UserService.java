package com.example.challenge_mottu_java.service;

import com.example.challenge_mottu_java.dto.UserDTO;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getRole());
    }

    public UserDTO createUser(User user) {
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getRole());
    }

    public UserDTO updateUser(String username) {
        User oldUser = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        oldUser.setUsername(oldUser.getUsername());
        oldUser.setPassword(oldUser.getPassword());
        oldUser.setName(oldUser.getName());

        userRepository.save(oldUser);
        return new UserDTO(oldUser.getId(), oldUser.getUsername(), oldUser.getName(), oldUser.getRole());
    }

    public void deleteUser(Long userId ) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        userRepository.delete(user);
    }
}
