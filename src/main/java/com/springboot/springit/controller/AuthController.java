package com.springboot.springit.controller;

import com.springboot.springit.domain.User;
import com.springboot.springit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profile(){
        return "auth/profile";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }
    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if(bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("user",user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else {
            StringBuffer requestURLStringBuffer = httpServletRequest.getRequestURL();
            String requestURI = httpServletRequest.getRequestURI();
            int pos = requestURLStringBuffer.indexOf(requestURI);
            String baseURL = requestURLStringBuffer.delete(pos, pos + requestURI.length()).toString();
            User newUser = userService.register(user, baseURL);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success",true);
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{email}/{activationCode}")
    public String activate(@PathVariable String email, @PathVariable String activationCode,
                           Model model, HttpServletRequest httpServletRequest){
        Optional<User> optionalUser = userService.findByEmail(email);
        int passes=0;
        if(optionalUser.isPresent()){
            passes++;
            User user = optionalUser.get();
            if(user.getActivationCode().equals(activationCode)){
                passes++;
                user.setEnabled(true);
                user.setConfirmPassword(user.getPassword());
                userService.save(user);
                StringBuffer requestURLStringBuffer = httpServletRequest.getRequestURL();
                String requestURI = httpServletRequest.getRequestURI();
                int pos = requestURLStringBuffer.indexOf(requestURI);
                String baseURL = requestURLStringBuffer.delete(pos, pos + requestURI.length()).toString();
                userService.sendWelcomeEmail(user, baseURL);
            }
        }
        model.addAttribute("passes", passes);
        return "auth/activated";
    }
}
