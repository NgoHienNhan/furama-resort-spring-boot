package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EducationDegreeService educationDegreeService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @ModelAttribute("employeeUsing")
    public Employee getUserName(@SessionAttribute(required = false,name = "employee") EmployeeUsing employee){
        return employee;
    }

    @ModelAttribute("education")
    public Iterable<EducationDegree> educationDegrees() {
        return educationDegreeService.findAll();
    }

    @ModelAttribute("position")
    public Iterable<Position> positions() {
        return positionService.findAll();
    }

    @ModelAttribute("division")
    public Iterable<Division> divisions() {
        return divisionService.findAll();
    }

    @GetMapping(value = "/show")
    public ModelAndView showPageListEmployee(@PageableDefault(value = 10) Pageable pageable) {
        Page<Employee> employees = employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("employee/showEmployee");
        if (employees.getContent().size() == 0) {
            modelAndView.addObject("msg", "Chưa có nhân viên nào.");
        }
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping(value = "/search")
    public ModelAndView showSearch(@PageableDefault(value = 10) Pageable pageable, @RequestParam("search") String name) {
        Page<Employee> employees = employeeService.findByName(pageable, "%" + name + "%");
        ModelAndView modelAndView = new ModelAndView("employee/showEmployee");
        if (employees.getContent().size() == 0) {
            modelAndView.addObject("msg", "Not found.");
        }
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping(value = "/create")
    public String showPageCreateEmployee(Model model) {
        model.addAttribute("employeeUser", new EmployeeUser());
        return "employee/createEmployee";
    }

    @PostMapping(value = "/create")
    public String saveEmployee(@Validated @ModelAttribute EmployeeUser employeeUser, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasFieldErrors()) {
            return "employee/createEmployee";
        } else {
            if (userService.checkUserName(employeeUser.getUserName())) {
                model.addAttribute("msgUserName", "Tên đăng nhập đã tồn tại.");
                return "employee/createEmployee";
            } else {
                redirectAttributes.addFlashAttribute("msg", "Create employee: " + employeeUser.getName() + " success.");

                new User();
                User user;
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String pass = bCryptPasswordEncoder.encode(employeeUser.getPassWord());


                Set<Role> roles = new HashSet<>();
                if (employeeUser.getPosition().getName().equals("Giám đốc") || employeeUser.getPosition().getName().equals("Quản lý")) {
                    roles.add(roleService.findById(1));
                } else {
                    roles.add(roleService.findById(2));
                }
                user = new User(employeeUser.getUserName(), pass, roles);


                String idEmployee = "NV-" + ((int) (Math.random() * 10000));
                Employee employee = new Employee(idEmployee, employeeUser.getName(), employeeUser.getBirthday(), employeeUser.getIdCard(),
                        employeeUser.getSalary(), employeeUser.getPhone(), employeeUser.getEmail(), employeeUser.getAddress(),
                        employeeUser.getPosition(), employeeUser.getEducationDegree(), employeeUser.getDivision(), user);

                userService.save(user);
                employeeService.save(employee);

                return "redirect:/employee/show";
            }
        }
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView showPageUpdateEmployee(@PathVariable String id) {
        Employee employee = employeeService.findById(id);
        return new ModelAndView("employee/updateEmployee", "employee", employee);
    }

    @PostMapping(value = "/update")
    public String updateEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Update employee: " + employee.getName() + " success.");
        employeeService.save(employee);
        return "redirect:/employee/show";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.findById(id);
        redirectAttributes.addFlashAttribute("msg", "Delete employee: " + employee.getName() + " success.");
        employeeService.delete(employee);
        return "redirect:/employee/show";
    }
}
