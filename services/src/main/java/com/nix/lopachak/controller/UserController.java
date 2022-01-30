package com.nix.lopachak.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.nix.lopachak.util.SecurityUtils.getCurrentUserDetails;
import static java.util.Objects.requireNonNull;

/**
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        UserDetails userDetails = requireNonNull(getCurrentUserDetails());
        model.addAttribute("name", userDetails.getUsername());
        return "hello";
    }
}
