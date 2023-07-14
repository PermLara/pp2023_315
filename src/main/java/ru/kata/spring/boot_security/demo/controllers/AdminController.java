package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        User principalUser = userService.findByUsername(principal.getName());
        model.addAttribute("principalUser", principalUser);
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roleSelect", allRoles);
        model.addAttribute("newUser", new User());
        return "admin_fetch";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute("newUser") User newUser,
                             Model model) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("admin/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("newUser") User newUser,
                             Model model) {
        newUser.setId(id);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
}
