package ru.rusyaevmax.digitalLibrary2Boot.controllers;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String main() {
        return "main";
    }
}
