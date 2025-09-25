package com.example.challenge_mottu_java.controller.web;

import com.example.challenge_mottu_java.model.Bike;
import com.example.challenge_mottu_java.model.User;
import com.example.challenge_mottu_java.service.BikeService;
import com.example.challenge_mottu_java.service.PendingService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/bike")
public class BikeWebController {

    private final BikeService bikeService;
    private final MessageSource messageSource;
    private final PendingService pendingService;

    public BikeWebController(BikeService bikeService, MessageSource messageSource, PendingService pendingService) {
        this.bikeService = bikeService;
        this.messageSource = messageSource;
        this.pendingService = pendingService;
    }

    @GetMapping("/form")
    public String form(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Bike bike = new Bike();
        bike.setCourt(user.getCourt());

        model.addAttribute("bike", bike);
        model.addAttribute("court", user.getCourt());
        return "bike-form";
    }

    @PostMapping("/form")
    public String save(@Valid Bike bike, BindingResult result, RedirectAttributes redirect, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("court", user.getCourt());
            return "bike-form";
        }

        try {
            bike.setCourt(user.getCourt());
            bikeService.createBike(bike);

            var message = messageSource.getMessage("bike.create.success", null, LocaleContextHolder.getLocale());
            redirect.addFlashAttribute("message", message != null ? message : "Moto cadastrada com sucesso!");
            return "redirect:/web";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/bike/form";
        }
    }

    @GetMapping("/{placa}")
    public String detalhes(@PathVariable("placa") String placa, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Bike bike = bikeService.findByPlacaAndCourt(placa, user.getCourt());
        if (bike == null) {
            model.addAttribute("error", "Moto não encontrada neste pátio.");
            return "redirect:/web/dashboard"; // ou alguma página de erro
        }

        model.addAttribute("user",user);
        model.addAttribute("bike", bike);
        model.addAttribute("court", user.getCourt());
        return "bike-details"; // nome do template Thymeleaf
    }

    @PostMapping("/delete/{id}")
    public String deleteBike(@PathVariable String id) {
        bikeService.deleteBike(id);
        return "redirect:/web";
    }

    @GetMapping("/edit/{placa}")
    public String editForm(@PathVariable("placa") String placa, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Bike bike = bikeService.findByPlacaAndCourt(placa, user.getCourt());
        if (bike == null) {
            model.addAttribute("error", "Moto não encontrada neste pátio.");
            return "redirect:/web";
        }

        model.addAttribute("bike", bike);
        model.addAttribute("court", user.getCourt());
        return "bike-edit";
    }

    @PostMapping("/edit/{placa}")
    public String updateBike(@PathVariable("placa") String placa, @Valid Bike bike,
                             BindingResult result, RedirectAttributes redirect, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            Bike existingBike = bikeService.findByPlacaAndCourt(placa, user.getCourt());
            model.addAttribute("bike", existingBike);
            model.addAttribute("court", user.getCourt());
            return "bike-edit";
        }

        try {
            bike.setPlaca(placa);
            bike.setCourt(user.getCourt());
            bikeService.updateBike(placa, bike);

            var message = messageSource.getMessage("bike.update.success", null, LocaleContextHolder.getLocale());
            redirect.addFlashAttribute("message", message != null ? message : "Moto atualizada com sucesso!");
            return "redirect:/web/bike/" + placa;
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/bike/edit/" + placa;
        }
    }

    @PostMapping("/pending/update/{pendingId}")
    public String updatePending(@PathVariable("pendingId") Long pendingId,
                                @RequestParam("number") Long number,
                                @RequestParam("description") String description,
                                @RequestParam("status") String status,
                                @RequestParam("bikePlaca") String bikePlaca,
                                RedirectAttributes redirect) {
        try {
            pendingService.updatePendingById(pendingId, number, description, status);

            redirect.addFlashAttribute("message", "Pendência atualizada com sucesso!");
            return "redirect:/web/bike/edit/" + bikePlaca;
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/bike/edit/" + bikePlaca;
        }
    }

    @PostMapping("/pending/delete/{pendingId}")
    public String deletePending(@PathVariable("pendingId") Long pendingId,
                                @RequestParam("bikePlaca") String bikePlaca,
                                RedirectAttributes redirect) {
        try {
            pendingService.deletePending(pendingId);
            redirect.addFlashAttribute("message", "Pendência removida com sucesso!");
            return "redirect:/web/bike/edit/" + bikePlaca;
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/bike/edit/" + bikePlaca;
        }
    }
}