package com.example.proyecto_clase5.controller;


import com.example.proyecto_clase5.repository.EmployeesRepository;
import com.example.proyecto_clase5.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeesRepository;

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
                BigDecimal salary = new BigDecimal(Double.parseDouble(salario)); // verifica si es un número
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
    public String cantidadEmpleadosPorPais() {

        //COMPLETAR
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep() {
        //COMPLETAR
        return "/Search/lista3";

    }


}
