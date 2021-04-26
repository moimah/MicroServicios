package com.moimah.microservicios.app.usuarios.services;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.services.services.CommonServiceImpl;
import com.moimah.microservicios.app.usuarios.client.CursoFeignClient;
import com.moimah.microservicios.app.usuarios.models.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    @Autowired
    private CursoFeignClient clientCurso;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void deleteCursoAlumnoById(Long id) {
        clientCurso.deleteCursoAlumnoById(id);
    }

    @Override
    @Transactional
    public void deleteIdById(Long id) {
        super.deleteIdById(id);
        this.deleteCursoAlumnoById(id);
    }
}
