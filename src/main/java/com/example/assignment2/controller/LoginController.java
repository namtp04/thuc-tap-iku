package com.example.assignment2.controller;

import com.example.assignment2.repository.NhanVienRepository;
import com.example.assignment2.request.LoginRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private NhanVienRepository nhanVienRepository;

    @GetMapping("login")
    public String loginView() {
        return "/login/login";
    }

    @PostMapping("/login-action")
    public String loginAction(@Valid @ModelAttribute("loginReq") LoginRequest loginReq, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", getErrorMessages(bindingResult));
            return "/login/login";
        }
        Boolean checkLogin = nhanVienRepository.existsByMaAndAndMatKhau(loginReq.getUsername(), loginReq.getPassword());
        if (checkLogin) {
            session.setAttribute("username", loginReq.getUsername());
            if(nhanVienRepository.getNhanVienAdmin(loginReq.getUsername())!=null){
                session.setAttribute("role","ADMIN");
            }else{
                session.setAttribute("role","NHÂN VIÊN");
            }
            return "redirect:/ban-hang/hien-thi";
        }
        model.addAttribute("error", "Login false check username and password");
        return "/login/login";
    }

    public Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/product/list";
    }
}
