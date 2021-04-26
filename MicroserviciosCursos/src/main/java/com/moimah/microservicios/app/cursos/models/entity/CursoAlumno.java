package com.moimah.microservicios.app.cursos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moimah.commons.alumno.models.entity.Alumno;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "curso_alumno")
public class CursoAlumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="alumno_id", unique = true)
    private Long alumnoId;

    @JsonIgnoreProperties(value = {"cursoAlumnos"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof CursoAlumno)) return false;
        CursoAlumno ca = (CursoAlumno) o;

        if (o == null || getClass() != o.getClass()) return false;

        return this.alumnoId != null && this.alumnoId.equals(ca.getAlumnoId());
    }

  }
