package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImp implements AlumnoService{
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Override
    public List<Alumno> listar() {
        return alumnoRepository.findAll();
    }

    @Override
    public Optional<Alumno> listarporId(Long id) {
        return alumnoRepository.findById(id);
    }

    @Override
    public Optional<Alumno> listarporDni(String dni) {
        return alumnoRepository.findByDni(dni);
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public void eliminar(Long id) {
        alumnoRepository.deleteById(id);

    }

    @Override
    public Optional<Alumno> porEmail(String email) {
        return alumnoRepository.findByEmail(email);
    }



    @Override
    public List<Alumno> listarPorApellido(String apellido) {
       return (List<Alumno>) alumnoRepository.findByApellido(apellido);
    }

    @Override
    public List<Alumno> listarPorCurso(Long id) {
         return (List<Alumno>)alumnoRepository.findBycurso(id);
    }

    @Override
    public List<Alumno> listarPorAnioCurso(String anio, String cicloLectivo) {
        return alumnoRepository.findByAnioCurso(anio, cicloLectivo);
    }

    public List<Alumno> guardarLista(List<Alumno> alumnos){
        return alumnoRepository.saveAll(alumnos);
    }
    @Override
    public int numAlumnosPorAnio(String anio){
        return alumnoRepository.findNumAlumnosByAnio(anio);
    }
}
