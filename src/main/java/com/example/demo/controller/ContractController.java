package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/contract")
public class ContractController{
    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractDetailService contractDetailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ServiceService serviceService;

    @Autowired AttachServiceService attachServiceService;

    @ModelAttribute("customers")
    public Iterable<Customer> customers(){
        return customerService.findAll();
    }

    @ModelAttribute("employees")
    public Iterable<Employee> employees(){
        return employeeService.findAllNotPage();
    }

    @ModelAttribute("services")
    public Iterable<Service> services(){
        return serviceService.findAllNotPage();
    }

    @ModelAttribute("attachServices")
    public Iterable<AttachService> attachServices(){
        return attachServiceService.findAll();
    }

    @ModelAttribute("employeeUsing")
    public Employee getUserName(@SessionAttribute(required = false,name = "employee") EmployeeUsing employee){
        return employee;
    }

    @GetMapping(value = "/show")
    public ModelAndView show(@PageableDefault(value = 5) Pageable pageable){
        Page<Contract> contracts = contractService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("contract/listUseService");
        if (contracts.getContent().size() == 0){
            modelAndView.addObject("msg","Chưa có khách hàng nào sử dụng dịch vụ.");
        }else {
            modelAndView.addObject("contracts",contracts);
        }
        return modelAndView;
    }

    @GetMapping(value = "/createContract")
    public ModelAndView showPageCreateContract(){
        return new ModelAndView("contract/createContract","contract",new Contract());
    }

    @PostMapping(value = "/createContract")
    public String saveContract(@Validated @ModelAttribute Contract contract, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasFieldErrors()){
            return "contract/createContract";
        }else {
            redirectAttributes.addFlashAttribute("msg", "Create contract of customer: " + contract.getCustomer().getCustomerName() + " success.");
            contractService.save(contract);
            return "redirect:/contract/show";
        }
    }

    @GetMapping(value = "/createContractDetail")
    public String showPageCreateContractDetail(Model model){
        model.addAttribute("contract",contractService.findAllNotPage());
        model.addAttribute("contractDetail",new ContractDetail());
        return "contract/createContractDetail";
    }

    @PostMapping(value = "/createContractDetail")
    public String saveContractDetail(@ModelAttribute ContractDetail contractDetail, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("msg","Create contract detail of customer: "+contractDetail.getContract().getCustomer().getCustomerName()+" success.");
        contractDetailService.save(contractDetail);
        return "redirect:/contract/show";
    }
}
