package com.ivan_rodrigues.kata.tictactoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GettingStartedController {

    @GetMapping("/getting-started")
    public String getApiDocs() {
        return "getting-started";
    }

}
