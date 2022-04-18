package com.example.proyecto_clase5.repository;

import com.example.proyecto_clase5.dto.EmpleadoHistoriaDto;
import com.example.proyecto_clase5.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    @Query(value = "select e.first_name as nombre,\n" +
            "        e.last_name as apellido, date(jh.start_date) as fechainicio,\n" +
            "        date(jh.end_date) as fechafin,  j.job_title as puesto\n" +
            "        from hr.job_history jh\n" +
            "        inner join hr.employees e\n" +
            "        on e.employee_id = jh.employee_id\n" +
            "        inner join hr.jobs j\n" +
            "        on j.job_id = e.job_id\n" +
            "        order by salary desc;", nativeQuery = true)
    List<EmpleadoHistoriaDto> obtenerEmpleadosHistoria();

}
