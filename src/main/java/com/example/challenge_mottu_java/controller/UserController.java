package com.example.challenge_mottu_java.controller;

import com.example.challenge_mottu_java.dto.UserDTO;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(Model model, @PathVariable String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/form")
    public String form(User user){
        return "form";
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid User user) {
        UserDTO newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/form")
    public String save(@Valid User user, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()) return "form";
        userService.createUser(user);
        var message = messageSource.getMessage("task.create.success", null, LocaleContextHolder.getLocale());
        redirect.addFlashAttribute("message", message);
        return "redirect:/task";
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @RequestBody @Valid User newUser) {
        try {
            UserDTO user =  userService.updateUser(username, newUser);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
