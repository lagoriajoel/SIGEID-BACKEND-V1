package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query(value = "SELECT  * FROM cursos where cursos.id IN (select alumnnos_cursos.cursos_id from alumnnos_cursos inner join alumnos on alumnos.id=alumnnos_cursos.alumnos_id and alumnos_id=:id )", nativeQuery=true)
    List<Curso> findByAlumno(Long id);
}
