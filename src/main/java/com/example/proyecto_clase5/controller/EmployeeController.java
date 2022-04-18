package com.example.proyecto_clase5.controller;

import com.example.proyecto_clase5.entity.Employees;
import com.example.proyecto_clase5.repository.DepartmentsRepository;
import com.example.proyecto_clase5.repository.EmployeesRepository;
import com.example.proyecto_clase5.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"", "/"})
    public String listaEmployee(Model model) {
        model.addAttribute("listaEmployee", employeesRepository.findAll());
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/lista";
    }

    @GetMapping("/new")
    public String nuevoEmployeeForm(@ModelAttribute("employee") Employees employee, Model model) {
        //COMPLETAR
        model.addAttribute("listaEmployee", employeesRepository.findAll());
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/Frm";
    }

    @PostMapping("/save")
    public String guardarEmployee(@ModelAttribute("employee") @Valid Employees employee, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  @RequestParam(name = "fechaContrato", required = false) String fechaContrato, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaEmployee", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/Frm";
        } else {

            if (employee.getId() == 0) {
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employee.setHireDate(new Date());
                employeesRepository.save(employee);
                return "redirect:/employee";
            } else {

                try {
                    employee.setHireDate(new SimpleDateFormat("yyyy-MM-dd").parse(fechaContrato));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                employeesRepository.save(employee);
                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee";
            }
        }
    }

    @GetMapping("/edit")
    public String editarEmployee(Model model, @RequestParam("id") int id,
                                 @ModelAttribute("employee") Employees employee) {

        //COMPLETAR

        Optional<Employees> optEmployee = employeesRepository.findById(id);

        if (optEmployee.isPresent()) {
            employee = optEmployee.get();
            model.addAttribute("employee", employee);
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaEmployee", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/Frm";
        } else {
            return "redirect:/employee/list";
        }
    }

    @GetMapping("/delete")
    public String borrarEmpleado(Model model,
                                 @RequestParam("id") int id,
                                 RedirectAttributes attr) {

        Optional<Employees> optEmployees = employeesRepository.findById(id);

        if (optEmployees.isPresent()) {
            employeesRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Empleado borrado exitosamente");
        }
        return "redirect:/employee";

    }

//    @PostMapping("/search")
//    public String buscar (){
//
//        //COMPLETAR
//    }

}
