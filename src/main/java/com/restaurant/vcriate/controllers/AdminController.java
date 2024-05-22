package com.restaurant.vcriate.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public String get(){
        return "Get : Admin controller";
    }

    @PostMapping
    public String post(){
        return "Post : Admin";
    }

    @PutMapping
    public String update(){
        return "Update : Admin";
    }

    @DeleteMapping
    public String delete(){
        return "Delete : Admin";
    }

}
