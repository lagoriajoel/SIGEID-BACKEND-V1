package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByApellido(String apellido);
    Optional<Alumno> findByDni(String dni);

    Optional<Alumno> findByEmail(String email);

    @Query(value = "SELECT  * FROM alumnos where alumnos.id IN (select alumnnos_cursos.alumnos_id from alumnnos_cursos where cursos_id=:id)", nativeQuery=true)
     List<Alumno> findBycurso(Long id);
    @Query(value = "SELECT  * FROM alumnos where alumnos.id IN (select alumnnos_cursos.alumnos_id from alumnnos_cursos inner join cursos on cursos.id=alumnnos_cursos.cursos_id and cursos.anio=:anio and cursos.ciclo_lectivo=:cicloLectivo)", nativeQuery=true)
    List<Alumno> findByAnioCurso(String anio, String cicloLectivo);

    @Query(value = "SELECT  count(*) FROM alumnos where alumnos.id IN (select alumnnos_cursos.alumnos_id from alumnnos_cursos inner join cursos on cursos.id=alumnnos_cursos.cursos_id and cursos.anio=:anio )", nativeQuery=true)
    int findNumAlumnosByAnio(String anio);

}
