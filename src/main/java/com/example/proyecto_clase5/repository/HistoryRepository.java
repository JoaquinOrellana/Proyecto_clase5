package com.example.proyecto_clase5.repository;

import com.example.proyecto_clase5.dto.EmpleadoHistoriaDto;
import com.example.proyecto_clase5.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    @Query(value = "select e.first_name as nombre,\n" +
            "e.last_name as apellido, date(jh.start_date) as fechainicio,\n" +
            "date(jh.end_date) as fechafin, j.job_title as puesto, e.salary as salario\n" +
            "from hr.employees e\n" +
            "inner join hr.jobs j on j.job_id = e.job_id\n" +
            "left join hr.job_history jh on e.employee_id = jh.employee_id\n" +
            "order by salary desc;", nativeQuery = true)
    List<EmpleadoHistoriaDto> obtenerEmpleadosHistoria();

    @Query(value = "select e.first_name as nombre,\n" +
            "e.last_name as apellido, date(jh.start_date) as fechainicio,\n" +
            "date(jh.end_date) as fechafin, j.job_title as puesto, e.salary as salario\n" +
            "from hr.employees e\n" +
            "inner join hr.jobs j on j.job_id = e.job_id\n" +
            "left join hr.job_history jh on e.employee_id = jh.employee_id\n" +
            "where salary = ?1 \n" +
            "order by salary desc;", nativeQuery = true)
    List<EmpleadoHistoriaDto> obtenerEmpleadosHistoriaPorSalario(BigDecimal salario);

}
