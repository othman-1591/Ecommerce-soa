package com.ecommerce.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanierController {

    @GetMapping("/panier")
    public String showPanierPage() {
        return "panier";
    }
}
