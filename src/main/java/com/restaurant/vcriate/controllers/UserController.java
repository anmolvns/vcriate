package com.restaurant.vcriate.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public String get(){
        return "Get : User controller";
    }

    @PostMapping
    public String post(){
        return "Post : User";
    }

    @PutMapping
    public String update(){
        return "Update : User";
    }

    @DeleteMapping
    public String delete(){
        return "Delete : User";
    }
}
