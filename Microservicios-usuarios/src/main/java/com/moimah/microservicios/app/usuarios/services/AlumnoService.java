package com.moimah.microservicios.app.usuarios.services;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.services.services.CommonService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface AlumnoService extends CommonService<Alumno> {
    public List<Alumno> findByNombreOrApellido(String term);

    public Iterable<Alumno> findAllById(Iterable<Long> ids);

    public void deleteCursoAlumnoById(Long id);
}
