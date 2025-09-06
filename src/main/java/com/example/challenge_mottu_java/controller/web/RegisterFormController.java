package com.example.challenge_mottu_java.controller.web;

import com.example.challenge_mottu_java.model.User;
import org.springframework.ui.Model;
import com.example.challenge_mottu_java.dto.CourtDTO;
import com.example.challenge_mottu_java.service.CourtService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
public class RegisterFormController {

    private final CourtService courtService;

    public RegisterFormController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public String registerForm(Model model) {
        List<CourtDTO> courts = courtService.getAllCourts();
        model.addAttribute("courts", courts);
        model.addAttribute("user", new User());
        return "register-form";
    }
}
