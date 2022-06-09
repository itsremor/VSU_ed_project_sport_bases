package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vsu.cs.c3.g4.irentor.server.config.WebSecurityConfig;
import vsu.cs.c3.g4.irentor.server.model.UsersModel;
import vsu.cs.c3.g4.irentor.server.service.UsersService;


@Controller
@Api(description = "registration controller")
public class RegistrationController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        return "registration/registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String passwordConfirm,
                          @RequestParam String email,
                          @RequestParam String name,
                          @RequestParam String surname,
                          Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = new UsersModel(login, password, passwordConfirm, email, name, surname);
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration/registration";
        }
        if (!usersService.add(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration/registration";
        }
        return "redirect:/";
    }
}
