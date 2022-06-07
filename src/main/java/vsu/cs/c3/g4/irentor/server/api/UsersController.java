package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vsu.cs.c3.g4.irentor.server.config.WebSecurityConfig;
import vsu.cs.c3.g4.irentor.server.model.ItemsModel;
import vsu.cs.c3.g4.irentor.server.model.OrdersModel;
import vsu.cs.c3.g4.irentor.server.model.UsersModel;
import vsu.cs.c3.g4.irentor.server.service.CategoriesService;
import vsu.cs.c3.g4.irentor.server.service.ItemsService;
import vsu.cs.c3.g4.irentor.server.service.OrdersService;
import vsu.cs.c3.g4.irentor.server.service.UsersService;

import java.security.Principal;

@Controller
@Api(description = "users controller")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/user/profile")
    public String userList(
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());

        return "user/profile";
    }

    @GetMapping("/user/email")
    public String userEmail(
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());
        return "user/change_email";
    }

    @PostMapping("/user/email")
    public String userNewEmail(
            @RequestParam String oldEmail,
            @RequestParam String newEmail,
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());
        if (!oldEmail.equals(newEmail) && user.getEmail().equals(oldEmail)) {
            user.setEmail(newEmail);
            usersService.addExist(user);
            return "redirect:/user/profile";
        }
        return "user/change_email";
    }

    @GetMapping("/user/password")
    public String userPassword(
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());
        return "user/change_pass";
    }

    @PostMapping("/user/password")
    public String userNewPassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("login", user.getLogin());
        model.addAttribute("email", user.getEmail());
        if (usersService.changePassword(user, oldPassword, newPassword)) {
            return "redirect:/user/profile";
        }
        return "user/change_pass";
    }
}
