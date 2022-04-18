package com.example.proyecto_clase5.repository;

import com.example.proyecto_clase5.dto.SalarioPromedioPorDepartamentoDto;
import com.example.proyecto_clase5.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {

    @Query(nativeQuery = true,value ="Select d.department_id as departmentid, d.department_name as departmentname, cast(AVG(salary) as decimal) as promedio\n" +
            "from employees e\n" +
            "inner join departments d on e.department_id = d.department_id\n" +
            "group by e.department_id;" )
    List<SalarioPromedioPorDepartamentoDto> listaSalarioPromedioPorDepartamento();

}
