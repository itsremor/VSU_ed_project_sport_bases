package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vsu.cs.c3.g4.irentor.server.config.WebSecurityConfig;
import vsu.cs.c3.g4.irentor.server.model.*;
import vsu.cs.c3.g4.irentor.server.service.CategoriesService;
import vsu.cs.c3.g4.irentor.server.service.ItemsService;
import vsu.cs.c3.g4.irentor.server.service.OrdersService;
import vsu.cs.c3.g4.irentor.server.service.UsersService;

@Controller
@Api(description = "admins controller")
public class AdminsController {
    @Autowired
    private UsersService userService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("users", userService.findAllByRole(1L));
        return "admin/users";
    }

    @PostMapping("/admin/users")
    public String deleteUser(
            @RequestParam Long id,
            @RequestParam String action,
            Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        if (action.equals("delete")) {
            userService.removeById(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/add-item")
    public String addProduct(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("categories", categoriesService.findAll());
        return "admin/add-item";
    }

    @PostMapping("/admin/add-item")
    public String addProduct(
            @RequestParam Long category,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Float price,
            Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("categories", categoriesService.findAll());
        ItemsModel item = new ItemsModel(category, name, description, price);
        itemsService.add(item);
        return "admin/add-item";
    }

    @GetMapping("/admin/orders")
    public String ordersList(Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("orders", ordersService.findAllByStatus(2L));
        return "admin/orders-confirmation";
    }

    @PostMapping("/admin/orders")
    public String changeOrder(
            @RequestParam Long id,
            @RequestParam String action,
            Model model) {
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        if (action.equals("accept")) {
            OrdersModel order = ordersService.findById(id);
            UsersModel user = userService.findById(order.getUser());
            if (order.getPrice() > user.getBalance()) {
                model.addAttribute("error", "У пользователя недостаточно средств.");
                model.addAttribute("orders", ordersService.findAllByStatus(2L));
                return "admin/orders-confirmation";
            }
            user.setBalance(user.getBalance()- order.getPrice());
            userService.addExist(user);
            order.setStatus(3L);
            ordersService.add(order);
        }
        if (action.equals("decline")) {
            ordersService.remove(ordersService.findById(id));
        }
        return "redirect:/admin/orders";
    }
}
