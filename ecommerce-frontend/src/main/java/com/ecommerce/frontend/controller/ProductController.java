package com.ecommerce.frontend.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

        @GetMapping("/product")
        public String showProductPage() {
            return "product";
        }

    @GetMapping("/formProduct")
    public String showFormProductPage() {
        return "/formProduct";
    }
    @GetMapping("/listProduct")
    public String showListProductPage() {
        return "/listProduct";
    }

    @GetMapping("/updateproduct")
    public String showUpdateproductPage() {
        return "/formUpdateProduct";
    }


}






