package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String dni;
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellido;
    @NotEmpty
    @Email
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.MERGE , org.hibernate.annotations.CascadeType.MERGE})
    @JoinTable(name = "alumnnos_cursos", joinColumns = @JoinColumn(name = "alumnos_id"),
            inverseJoinColumns = @JoinColumn(name = "cursos_id"))

    private Set<Curso> curso;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InformeDesempenio> informeDesempenios=new HashSet<>();


    public String getNombreCompleto() {

        return this.apellido+" "+this.nombres;

    }
}
