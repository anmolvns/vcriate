package com.restaurant.vcriate.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

    @GetMapping
    public String get(){
        return "Get : Management controller";
    }

    @PostMapping
    public String post(){
        return "Post : Management";
    }

    @PutMapping
    public String update(){
        return "Update : Management";
    }

    @DeleteMapping
    public String delete(){
        return "Delete : Management";
    }
}
