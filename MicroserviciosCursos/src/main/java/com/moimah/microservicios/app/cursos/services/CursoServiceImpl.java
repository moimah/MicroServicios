package com.moimah.microservicios.app.cursos.services;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.services.services.CommonServiceImpl;
import com.moimah.microservicios.app.cursos.clients.AlumnoFeignClient;
import com.moimah.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.moimah.microservicios.app.cursos.models.entity.Curso;
import com.moimah.microservicios.app.cursos.models.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    @Autowired
    private RespuestaFeignClient clientRespuesta;

    @Autowired
    private AlumnoFeignClient clientAlumno;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        return clientRespuesta.findExamenesIdsConRespuestasByAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> getAlumnosPorCurso(Iterable<Long> ids) {
        return clientAlumno.getAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void deleteCursoAlumnoById(Long id) {
        repository.deleteCursoAlumnoById(id);
    }
}
