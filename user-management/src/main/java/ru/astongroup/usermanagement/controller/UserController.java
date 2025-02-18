package ru.astongroup.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.astongroup.usermanagement.models.UserModel;
import ru.astongroup.usermanagement.models.Dtos.UserDto;
import ru.astongroup.usermanagement.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello from User Controller!";
    }


    @PostMapping("/create")
    public Boolean register(@RequestBody UserModel user) {

        return userService.create(user);
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers() {

        return userService.getAll();
    }

    @GetMapping("/mail/{email}")
    public UserModel getUserByEmail(@PathVariable String email) {

        return userService.getByEmail(email);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {

        return userService.getById(id);
    }
    @PutMapping("/update/{id}")
    public boolean updateUserById(
            @PathVariable long id,
            @RequestBody UserModel user) {

        return userService.updateById(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUserById(@PathVariable long id) {

        return userService.deleteById(id);
    }


}