package com.example.proyecto_clase5.controller;


import com.example.proyecto_clase5.entity.Departments;
import com.example.proyecto_clase5.repository.DepartmentsRepository;
import com.example.proyecto_clase5.repository.EmployeesRepository;
import com.example.proyecto_clase5.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Autowired
    HistoryRepository historyRepository;

    @GetMapping(value = {"", "/"})
    public String indice() {
        return "Search/indice";
    }

    @GetMapping(value = {"/Salario"})
    public String listaEmpleadosMayorSalario(Model model) {
        model.addAttribute("listaEmpleados", historyRepository.obtenerEmpleadosHistoria());
        return "Search/lista2";
    }

    @PostMapping("/busqueda")
    public String buscar(Model model,
                         @RequestParam("salario") String salario,
                         RedirectAttributes attr) {
        try {
            if (salario.equals("")) { // verifica que no esté vacío
                attr.addFlashAttribute("msg", "La búsqueda debe ser un número y no debe estar vacía.");
                return "redirect:/Search/Salario";
            } else {
                BigDecimal salary = new BigDecimal(salario); // verifica si es un número
                model.addAttribute("listaEmpleados", historyRepository.obtenerEmpleadosHistoriaPorSalario(salary));
                model.addAttribute("salarioFiltro", salary);
                return "Search/lista2";
            }
        } catch (Exception e) {
            attr.addFlashAttribute("msg", "La búsqueda debe ser un número y no debe estar vacía.");
            return "redirect:/Search/Salario";
        }
    }

    @GetMapping(value = "/Filtro2")
    public String cantidadEmpleadosPorPais(Model model) {
        model.addAttribute("listaSalarioPromedioPorDepartamento", departmentsRepository.listaSalarioPromedioPorDepartamento());
        return "Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep(Model model, @RequestParam("id") Integer id) {
        //COMPLETAR
        Optional<Departments> optional = departmentsRepository.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("listaEmpleadosPorDepartamento", employeesRepository.findByDepartmentOrderBySalaryDesc(optional.get()));
        } else {
            return "redirect:/Search";
        }
        return "Search/lista3";
    }


}
