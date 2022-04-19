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
import java.util.List;

import javax.validation.Valid;

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

    @GetMapping(value = {"", "/","/list"})
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
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaEmployee", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/Frm";
        } else {

            if (employee.getId() == null) {
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employee.setHireDate(new Date());
                employeesRepository.save(employee);
                return "redirect:/employee";
            } else {

                employee.setHireDate(new Date());

                employeesRepository.save(employee);
                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee";
            }
        }
    }

    @GetMapping("/edit")
    public String editarEmployee(Model model, @RequestParam("id") int id) {

        //COMPLETAR

        Optional<Employees> optEmployee = employeesRepository.findById(id);

        if (optEmployee.isPresent()) {
            Employees employee = optEmployee.get();
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

    @PostMapping("/search")
    public String buscar (Model model,@RequestParam("parametro") String parametro, @RequestParam("buscador") String buscador){

        switch (buscador){
            case "nombre":
                List<Employees> listaEmpleados1 = employeesRepository.buscarPorNombre(parametro);
                model.addAttribute("listaEmployee", listaEmpleados1);
            case "apellido":
                List<Employees> listaEmpleados2 = employeesRepository.buscarPorApellido(parametro);
                model.addAttribute("listaEmployee", listaEmpleados2);
            case "cargo":
                List<Employees> listaEmpleados3 = employeesRepository.buscarPorCargo(parametro);
                model.addAttribute("listaEmployee", listaEmpleados3);
            case "departamento":
                List<Employees> listaEmpleados4 = employeesRepository.buscarPorDepartamento(parametro);
                model.addAttribute("listaEmployee", listaEmpleados4);
            case "ciudad":
                List<Employees> listaEmpleados5 = employeesRepository.buscarPorCiudad(parametro);
                model.addAttribute("listaEmployee", listaEmpleados5);
            default:
                List<Employees> listaEmpleados6 = employeesRepository.findAll();
                model.addAttribute("listaEmployee", listaEmpleados6);
        }

        return "employee/lista";

    }

}
