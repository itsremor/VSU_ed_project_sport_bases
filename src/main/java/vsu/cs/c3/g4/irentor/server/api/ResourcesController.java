package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vsu.cs.c3.g4.irentor.server.config.WebSecurityConfig;

@Controller
@Api(description = "resources controller")
public class ResourcesController {
    @GetMapping("/resources/about")
    public String about(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        return "resources/about";
    }

    @GetMapping("/resources/help")
    public String help(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        return "resources/help";
    }
}
