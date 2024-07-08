package mk.frizer.web.controller;

import mk.frizer.model.BaseUser;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.BaseUserUpdateDTO;
import mk.frizer.service.BaseUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final BaseUserService baseUserService;

    public ProfileController(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @GetMapping
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String editProfile(@RequestBody BaseUserUpdateDTO baseUserUpdateDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BaseUser user = (BaseUser) userDetails;

        baseUserService.updateBaseUser(user.getId(), baseUserUpdateDTO);
        return "redirect:/profile";
    }
}

