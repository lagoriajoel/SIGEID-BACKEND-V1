package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Curso;
import com.informes.informesbackend.Services.AsignaturaService;
import com.informes.informesbackend.Services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class cursoController {

    @Autowired
    private CursoService service;
    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping("/list")
    @CrossOrigin("*")
    public ResponseEntity<List<Curso>> listarCursos(){
    return ResponseEntity.ok(service.listar());
}

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.porId(id);
        if (optionalCurso.isPresent()){
            return ResponseEntity.ok(optionalCurso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listCursosAlumno/{id}")
    public ResponseEntity<?> cursosDeAlumno(@PathVariable Long id){

       List<Curso>cursos= service.cursosPorAlumno(id);
       if (cursos.isEmpty()){
           return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El alumno no se encuentra asignado a ningun curso"));
       }
        return ResponseEntity.ok(cursos);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PostMapping("/save")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){


        if(result.hasErrors()){
            return validar(result);
        }
        if(curso.getAnio().equals("1") || curso.getAnio().equals("2") ){
            curso.setTecnicatura("-");
        }

        Curso cursoDB = service.guardar(curso);
        crearAsignaturas(cursoDB);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }


    private void crearAsignaturas(Curso curso ) {
       Set<String> asignaturas1año =new HashSet<>();
        asignaturas1año.add("Biología");
        asignaturas1año.add("Física");
        asignaturas1año.add("Química");
        asignaturas1año.add("Educación Física");
        asignaturas1año.add("Tecnología");
        asignaturas1año.add("Dibujo Técnico");
        asignaturas1año.add("Lengua");
        asignaturas1año.add("Matematica");
        asignaturas1año.add("Geografía");
        asignaturas1año.add("Lengua Extranjera");
        asignaturas1año.add("Taller");
        asignaturas1año.add("Informatica");

        Set<String> asignaturas3año =new HashSet<>();
        asignaturas3año.add("Seguridad e Higiene");
        asignaturas3año.add("Física");
        asignaturas3año.add("Química");
        asignaturas3año.add("Educación Física");
        asignaturas3año.add("Dibujo Técnico");
        asignaturas3año.add("Lengua y Literatura");
        asignaturas3año.add("Matematica");
        asignaturas3año.add("Geografía");
        asignaturas3año.add("Lengua Extranjera");
        asignaturas3año.add("Taller");
        asignaturas3año.add("Informatica");

        Set<String> asignaturas4año =new HashSet<>();
        asignaturas4año.add("Representacion Grafica");
        asignaturas4año.add("OCCQPI");
        asignaturas4año.add("Fundamentos de Procesos Productivos");
        asignaturas4año.add("Técnologia de los materiales");
        asignaturas4año.add("EESES");
        asignaturas4año.add("Lengua y Literatura");
        asignaturas4año.add("Matematica");
        asignaturas4año.add("OCEPP");
        asignaturas4año.add("Lengua Extranjera");
        asignaturas4año.add("Taller");
        asignaturas4año.add("Informatica");

        Set<String> asignaturas5año =new HashSet<>();
        asignaturas5año.add("Analisis y ensayo");
        asignaturas5año.add("OCCIPP");
        asignaturas5año.add("AETAC");
        asignaturas5año.add("Economía");
        asignaturas5año.add("Diseño y organizacion de Proc Industriales");
        asignaturas5año.add("Practicas Profecionalizantes");
        asignaturas5año.add("Matematica");
        asignaturas5año.add("OCEPP");
        asignaturas5año.add("Ingles Técnico");
        asignaturas5año.add("Taller");

        Set<String> asignaturas6año =new HashSet<>();
        asignaturas6año.add("Gestión de Procesos Productivos");
        asignaturas6año.add("Marco Jurídico");
        asignaturas6año.add("Prcticas Profecionalizante II");
        asignaturas6año.add("Ingles Técnico");
        asignaturas6año.add("Diseño y organizacion de Proc Industriales");
        asignaturas6año.add("Practicas Profecionalizantes");
        asignaturas6año.add("Matematica");
        asignaturas6año.add("OCCQM");
        asignaturas6año.add("Analisis y Ensayos");
        asignaturas6año.add("Taller");

        Set<Asignatura> asignaturasCreadas=new HashSet<>();
        Set<String> asignaturasGuardar=new HashSet<>();
        switch(curso.getAnio()) {
            case "1":
                asignaturasGuardar=asignaturas1año;
                break;
            case "2":
                asignaturasGuardar=asignaturas1año;
                break;
            case "3":
                asignaturasGuardar=asignaturas3año;
                break;
            case "4":
                asignaturasGuardar=asignaturas4año;
                break;
            case "5":
                asignaturasGuardar=asignaturas5año;
                break;
            case "6":
                asignaturasGuardar=asignaturas6año;
                break;
            default:
                // code block
        }
        asignaturasGuardar.forEach( asignatura ->{
            Asignatura asignaturaNueva=new Asignatura();
            asignaturaNueva.setNombre(asignatura);
            asignaturaNueva.setCurso(curso);
            asignaturaNueva.setAnioCurso(curso.getAnio());
            asignaturaNueva.setCicloLectivo(curso.getCicloLectivo());
            asignaturasCreadas.add(asignaturaNueva);
        });

        asignaturaService.GuardarAsignaturas(asignaturasCreadas);


    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarCurso(@RequestBody Curso curso, @PathVariable Long id){

        Optional<Curso> optionalCurso = service.porId(id);
        if(!optionalCurso.isPresent()){


            return ResponseEntity.unprocessableEntity().build();
        }

        curso.setId(optionalCurso.get().getId());
        service.guardar(curso);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @CrossOrigin("*")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.porId(id);
        if (optionalCurso.get().getAlumnos().size()>0){

            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El curso tiene asignados alumnos. No se puede ELIMINAR"));
        }
            service.eliminar(optionalCurso.get().getId());
            return ResponseEntity.ok().build();



         }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
