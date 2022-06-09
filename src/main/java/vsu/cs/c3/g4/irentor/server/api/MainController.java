package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vsu.cs.c3.g4.irentor.server.config.WebSecurityConfig;

import java.security.Principal;

@Controller
@Api(description = "main controller for home page")
public class MainController {
    @GetMapping("/")
    public String home(Principal principal, Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("test", principal == null ? null : principal.getName());
        return "main/main";
    }
}
