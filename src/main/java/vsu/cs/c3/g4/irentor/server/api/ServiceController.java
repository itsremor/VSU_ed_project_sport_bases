package vsu.cs.c3.g4.irentor.server.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Api(description = "service controller")
public class ServiceController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/service/balance")
    public String balance(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("balance", user.getBalance());
        return "service/balance";
    }

    @PostMapping("/service/balance")
    public String addBalance(
            @RequestParam Integer card,
            @RequestParam Integer cvv,
            @RequestParam Float sum,
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        UsersModel user = usersService.findByLogin(principal.getName());
        if (WebSecurityConfig.checkCard(card, cvv, sum)) {
            System.out.println(user.getBalance() + " + " + sum + " = " + (user.getBalance() + sum));
            user.setBalance(user.getBalance() + sum);
            usersService.addExist(user);
        }
        model.addAttribute("balance", user.getBalance());
        return "service/balance";
    }

    @GetMapping("/service/orders")
    public String orderHistory(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("orders", ordersService.findAllByUser(user.getId()));
        return "service/order-history";
    }

    @GetMapping("/service/catalog")
    public String itemsList(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("categories", categoriesService.findAll());
        model.addAttribute("items", itemsService.findAll());
        return "service/catalog";
    }

    @GetMapping("/service/order/{id}")
    public String order(
            @PathVariable("id") Long id,
            Principal principal,
            Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        model.addAttribute("item", itemsService.findById(id));
        return "service/make-order";
    }

    @PostMapping("/service/order/{id}")
    public String makeOrder(
            @PathVariable("id") Long id,
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam Integer duration,
            Principal principal,
            Model model) throws ParseException {
        if (principal == null) {
            return "redirect:/login";
        }
        UsersModel user = usersService.findByLogin(principal.getName());
        model.addAttribute("user", WebSecurityConfig.isUser());
        model.addAttribute("admin", WebSecurityConfig.isAdmin());
        ItemsModel item = itemsService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("id", id);
        Date parsedStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date parsedStartTime = new SimpleDateFormat("hh:mm").parse(startTime);
        System.out.println(parsedStartDate.toString());
        System.out.println(parsedStartTime.toString());
        parsedStartDate.setTime(parsedStartDate.getTime() + parsedStartTime.getTime());
        OrdersModel order = new OrdersModel(user.getId(), item.getId(), parsedStartDate, duration, item.getPrice() * duration);
        ordersService.add(order);
        return "service/make-order";
    }
}
