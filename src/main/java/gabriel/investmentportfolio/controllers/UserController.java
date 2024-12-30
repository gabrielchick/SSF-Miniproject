package gabriel.investmentportfolio.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import gabriel.investmentportfolio.model.User;
import gabriel.investmentportfolio.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Display login page
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User()); // Ensure the User object is in the model
        return "user/login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,HttpSession session) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("invalid",true);
            return "user/login";
        }

        if (userService.login(user)) {
        	session.setAttribute("userName", user.getUserName());
            return "redirect:/portfolio/list";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "user/login";
        }
    }

    // Display registration page
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User()); // Ensure the User object is in the model
        return "user/registration";
    }

    // Handle registration form submission
    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        userService.registerUser(user);
        return "redirect:/user/login";
    }

    // Logout and invalidate session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session.getAttribute("userName") == null ? null : session.getAttribute("userName").toString());
        return "user/logout";
    }
}