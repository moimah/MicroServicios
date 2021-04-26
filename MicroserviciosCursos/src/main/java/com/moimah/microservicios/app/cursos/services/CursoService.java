package com.moimah.microservicios.app.cursos.services;

import com.moimah.commons.services.services.CommonService;
import com.moimah.microservicios.app.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso> {
    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
