package com.example.proyecto_clase5.repository;

import com.example.proyecto_clase5.entity.Departments;
import com.example.proyecto_clase5.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    List<Employees> findByDepartmentOrderBySalaryDesc(Departments departments); //en la entidad

    @Query(nativeQuery = true, value = "select * from employees where lower(first_name) like %?1%")
    List<Employees> buscarPorNombre(String nombre);

    @Query(nativeQuery = true, value = "select * from employees where lower(last_name) like %?1%")
    List<Employees> buscarPorApellido(String apellido);

    @Query(nativeQuery = true, value = "select e.* from employees e\n" +
            "inner join jobs j on (e.job_id = j.job_id)\n" +
            "where lower(j.job_title) like ?1%")
    List<Employees> buscarPorCargo(String cargo);

    @Query(nativeQuery = true, value = "select e.* from employees e\n" +
            "inner join departments d on (d.department_id = e.department_id)\n" +
            "where lower(d.department_name) like %?1%")
    List<Employees> buscarPorDepartamento(String departamento);

    @Query(nativeQuery = true, value = "select e.* from employees e\n" +
            "inner join departments d on (d.department_id = e.department_id)\n" +
            "inner join locations l on (l.location_id = d.location_id)\n" +
            "where lower(l.city) like %?1%")
    List<Employees> buscarPorCiudad(String ciudad);

}
