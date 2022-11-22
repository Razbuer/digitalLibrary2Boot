package ru.rusyaevmax.digitalLibrary2Boot.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {
    @RequestMapping(value = "/error")
    public String error() {
        return "errors/404";
    }
}
