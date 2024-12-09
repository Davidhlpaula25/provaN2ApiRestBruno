package com.david.n1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfirmacaoController {

    @GetMapping("/confirmacao")
    public String confirmacao() {
        return "confirmacao";
    }

}
