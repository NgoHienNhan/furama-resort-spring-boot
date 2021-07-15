package com.example.demo.controller;

import com.example.demo.bean.Employee;
import com.example.demo.bean.User;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;


@Controller
@SessionAttributes("employee")
public class LoginLogoutController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/")
    public String showPageLogin(){
        return "login";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String showHomePage(@SessionAttribute("employee") Employee employee, Model model){
        model.addAttribute("employee",employee);
        return "index";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(@ModelAttribute("employee") Employee employeeUsing, RedirectAttributes attributes, Principal principal,
                               Model model) {

        String userName = principal.getName();
        User user = userService.findById(userName);
        Employee employee = employeeService.findByUser(user);
        employeeUsing.setName(employee.getName());
        attributes.addFlashAttribute("employee",employee);

        model.addAttribute("employee",employee);

        return "index";
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public String showErrorPage(@SessionAttribute("employee") Employee employee,Model model){
        model.addAttribute("employee",employee);
        return "403Error";
    }

    @RequestMapping("/logoutSuccess")
    public String logout(){
        return "login";
    }

    @ModelAttribute("employee")
    public Employee getEmployee(){
        return new Employee();
    }
}
