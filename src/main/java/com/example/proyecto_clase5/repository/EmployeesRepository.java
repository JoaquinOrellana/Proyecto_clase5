package com.example.proyecto_clase5.repository;

import com.example.proyecto_clase5.entity.Departments;
import com.example.proyecto_clase5.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    List<Employees> findByDepartment (Departments departments); //en la entidad
}
