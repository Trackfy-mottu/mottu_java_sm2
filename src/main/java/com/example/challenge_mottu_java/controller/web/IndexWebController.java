package com.example.challenge_mottu_java.controller.web;

import com.example.challenge_mottu_java.dto.BikeDTO;
import com.example.challenge_mottu_java.dto.CourtDTO;
import com.example.challenge_mottu_java.service.BikeService;
import com.example.challenge_mottu_java.service.CourtService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
public class IndexWebController {

    private final CourtService courtservice;
    private final BikeService bikeService;

    public IndexWebController(CourtService courtservice, BikeService bikeService) {
        this.courtservice = courtservice;
        this.bikeService = bikeService;
    }

    @GetMapping
    public String index(Model model) {
        CourtDTO court = courtservice.getCourtByAcessCode("COURT_JARDINS");
        List<BikeDTO> bikes = bikeService.getBikeByAcessCode("COURT_JARDINS");
        model.addAttribute("court", court);
        model.addAttribute("bikes", bikes);
        return "index";
    }


}
