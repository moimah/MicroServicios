package com.moimah.microservicios.app.usuarios.services;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.services.services.CommonServiceImpl;
import com.moimah.microservicios.app.usuarios.models.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {
    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }
}
