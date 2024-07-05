package mk.frizer.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.frizer.model.BaseUser;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.InvalidUsernameOrPasswordException;
import mk.frizer.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        BaseUser user = null;

        try {
            user = authService.login(username, password);
        } catch (InvalidUsernameOrPasswordException | InvalidArgumentsException exception) {
            model.addAttribute("bodyContent", "login");
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "master-template";
        }

        session.setAttribute("user", user);
        return "redirect:/home";
    }
}
