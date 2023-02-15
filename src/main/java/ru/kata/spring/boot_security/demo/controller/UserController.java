package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @ModelAttribute("loginRoles")
    public String userRoles() {
        String roles = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .toString();
        return roles.substring(1, roles.length() - 1).replaceAll("ROLE_", "");
    }

    @ModelAttribute("login")
    public String user() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping
    public String printUsersList(Model model, @AuthenticationPrincipal User userAuth) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("newUser",  new User());
        model.addAttribute("roles",roleService.getRoleList());
        model.addAttribute("userAuth", userAuth);

        return "index";
    }

    @PostMapping("new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/";
    }

    @PostMapping("del/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
